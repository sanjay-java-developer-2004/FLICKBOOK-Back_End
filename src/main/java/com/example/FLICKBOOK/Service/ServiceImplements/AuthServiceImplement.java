package com.example.FLICKBOOK.Service.ServiceImplements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Enum.MovieStatus;
import com.example.FLICKBOOK.Enum.SeatStatus;
import com.example.FLICKBOOK.Exception.AuthException;
import com.example.FLICKBOOK.Model.Movie;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Model.Show;
import com.example.FLICKBOOK.Repository.MovieRepository;
import com.example.FLICKBOOK.Repository.SeatRepository;
import com.example.FLICKBOOK.Repository.ShowRepository;
import com.example.FLICKBOOK.Service.ServiceInter.AuthService;

@Service
public class AuthServiceImplement implements AuthService {

  @Autowired
  private SeatRepository seatrepository;

  @Autowired
  private ShowRepository showrepository;

  @Autowired
  private  MovieRepository movierepository;

 @Override
public Object getDashBoard(Integer theatreid) throws AuthException{

    List<Show> shows = showrepository.findByTheatre_Theaterid(theatreid);

    ArrayList<Map<String, Object>> finalresponse = new ArrayList<>();  

    if (shows.isEmpty()) {
        throw new  AuthException("Shows Not Available For This Theatre");
    }

    for (Show temp : shows) {

        LocalDateTime showDateTime = LocalDateTime.of(temp.getShowdate(), temp.getShowtime());

        if (LocalDateTime.now().isAfter(showDateTime.plusMinutes(30))) {

            List<Seat> totalSeats     = seatrepository.findByShow_Showid(temp.getShowid());
            List<Seat> bookedSeats    = seatrepository.findByShow_ShowidAndSeatstatus(temp.getShowid(), SeatStatus.Booked);
            List<Seat> availableSeats = seatrepository.findByShow_ShowidAndSeatstatus(temp.getShowid(), SeatStatus.Available);
            double price = totalSeats.get(0).getPrice();

            Map<String, Object> response = new LinkedHashMap<>();

            response.put("Show",          showDateTime);
            response.put("Total",         totalSeats.size());     
            response.put("BookedSeats",   bookedSeats.size());     
            response.put("Available",     availableSeats.size());  
            response.put("Price",         price);

            
            finalresponse.add(response);  
        }
    }

    return finalresponse;
}



//get movies by ststus 

 @Override
 public Map<String, Object> getMoviesByStatus() {

    Map<String,Object> response = new LinkedHashMap<>();

    List<Movie> NewRelease = movierepository.findByMoviestatus(MovieStatus.new_release);
    List<Movie> CommingSoon =  movierepository.findByMoviestatus(MovieStatus.upcoming);
    List<Movie> Running = movierepository.findByMoviestatus(MovieStatus.running);

    if(NewRelease.isEmpty()){
        response.put("NEW_RELEASE","No New Released movies available at this time. Please check back later!");
    }else{
        response.put("NEW_RELEASE", NewRelease);
    }

    if(CommingSoon.isEmpty()){
         response.put("COMMING_SOON","No upcoming movies available at this time. Please check back later!");
    }else{
        response.put("COMMING_SOON",CommingSoon);

    }

    if(Running.isEmpty()){
        response.put("RUNNING","No movies available at this time. Please check back later!");
    }else{
        response.put("RUNNING",Running);
    }

   return response;
 }



}
