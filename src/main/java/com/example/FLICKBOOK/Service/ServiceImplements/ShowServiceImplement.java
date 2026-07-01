package com.example.FLICKBOOK.Service.ServiceImplements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FLICKBOOK.Enum.SeatStatus;
import com.example.FLICKBOOK.Enum.ShowStatus;
import com.example.FLICKBOOK.Exception.ShowException;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Model.Show;
import com.example.FLICKBOOK.Model.Theatre;
import com.example.FLICKBOOK.Repository.MovieRepository;
import com.example.FLICKBOOK.Repository.SeatRepository;
import com.example.FLICKBOOK.Repository.ShowRepository;
import com.example.FLICKBOOK.Repository.TheatreRepository;
import com.example.FLICKBOOK.Service.ServiceInter.ShowService;

@Service
public class ShowServiceImplement implements ShowService {

    @Autowired
    private ShowRepository showrepository;

    @Autowired
    private MovieRepository movierepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private SeatRepository seatrepository;

    // AddShows
    @Transactional
    @Override
    public String AddShows(Show show, String tname, String mname, Integer tid) throws ShowException {


        if (show.getShowtime() != null && mname != null && tname != null && show.getShowdate() != null) {

            Optional<Movie> movie1 = movierepository.findByMovienameIgnoreCase(mname.trim());


            Optional<Theatre> theatre0 = theatreRepository.findByTheateridAndTheaternameIgnoreCase(tid, tname.trim());

            if (movie1.isEmpty() || theatre0.isEmpty()) {
                throw new ShowException ("Movie or Theatre Not Available At This Name");
            }

            if (movie1.get().getMovieid() == null || theatre0.get().getTheaterid() == null) {
                throw new ShowException ("ID NOT FOUNDED");
            }

            Movie movie2 = movie1.get();
            Theatre theatre2 = theatre0.get();

            Show temp = new Show();

            temp.setMovie(movie2);
            temp.setTheatre(theatre2);
            temp.setShowStatus(ShowStatus.Active);
            temp.setShowtime(show.getShowtime());
            temp.setShowdate(show.getShowdate());
            Show savedShow = showrepository.save(temp); // first save the show

            // create seats

            int seatsPerRow = 25;
            int totalSeats = theatre0.get().getTotalseats();


            int rows = (int) Math.ceil((double) totalSeats / seatsPerRow);

            char row = 'A';

            List<Seat> seatList = new ArrayList<>();

            for (int i = 0; i < rows; i++) {

                for (int j = 1; j <= seatsPerRow; j++) {

                    Seat seat = new Seat();

                    seat.setSeatnumber(row + String.valueOf(j));
                    seat.setSeatstatus(SeatStatus.Available);
                    seat.setPrice(150.00);
                    seat.setShow(savedShow);
                    seatList.add(seat);

                }
                seatrepository.saveAll(seatList);
                seatrepository.flush();
                row++;
            }
            ;
            return "Show And Seat Added Successfully";

        } else {
            throw new ShowException ("ShoW Details Is Null");
        }
    }






    
    // getshows

    @Override
    public List<Map<String, Object>> getTheatresAndShows(Integer movieid, LocalDate date) throws ShowException  {

        List<Show> shows = showrepository.findByMovie_MovieidAndShowdate(movieid, date);

        if (shows.isEmpty()) {
            throw new ShowException ("No shows available for this date  ");
        }

        Map<Integer, Map<String, Object>> theatreMap = new LinkedHashMap<>();

        for (Show show : shows) {

            LocalDateTime showDateTime = LocalDateTime.of(show.getShowdate(), show.getShowtime());

            // Upcoming show or started within 30 mins
            if (LocalDateTime.now().isBefore(showDateTime.plusMinutes(30))) {

                Theatre theatre = show.getTheatre();

                if (!theatreMap.containsKey(theatre.getTheaterid())) {

                    Map<String, Object> theatreData = new HashMap<>();

                    theatreData.put("TheatreId", theatre.getTheaterid());
                    theatreData.put("TheatreName", theatre.getTheatername());
                    theatreData.put("Location", theatre.getTheaterlocation());
                    theatreData.put("Shows", new ArrayList<Map<String, Object>>());

                    theatreMap.put(theatre.getTheaterid(), theatreData);
                }

                List<Map<String, Object>> showList = (List<Map<String, Object>>) theatreMap
                        .get(theatre.getTheaterid())
                        .get("Shows");

                Map<String, Object> showData = new HashMap<>();
                showData.put("ShowID", show.getShowid());
                showData.put("ShowTimings", show.getShowtime());

                showList.add(showData);
            } else {
                System.out.println("Show : " + show.getShowStatus());
                show.setShowStatus(ShowStatus.InActive);
                showrepository.save(show);
                System.out.println("ShowAfter : " + show.getShowStatus());
            }

        }

        // All shows expired
        if (theatreMap.isEmpty()) {
            throw new ShowException ("Show not founded shows Are Expired pls Try Later");
        }

        return new ArrayList<>(theatreMap.values());
    }
}
