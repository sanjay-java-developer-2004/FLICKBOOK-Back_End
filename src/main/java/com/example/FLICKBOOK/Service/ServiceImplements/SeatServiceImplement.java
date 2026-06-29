package com.example.FLICKBOOK.Service.ServiceImplements;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Enum.SeatStatus;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Repository.SeatRepository;
import com.example.FLICKBOOK.Service.ServiceInter.SeatService;

@Service
public class SeatServiceImplement implements SeatService {

  @Autowired
  private SeatRepository seatrepository;

  // get seats
  @Override
  public List<Seat> GetSeats(Integer showid) throws Exception {

    List<Seat> temp = seatrepository.findByShow_Showid(showid);

    List<Seat> response = new ArrayList<>();

    if (!temp.isEmpty()) {

      for (Seat seats : temp) {

        Seat seatdetails = new Seat();

        seatdetails.setSeatid(seats.getSeatid());
        seatdetails.setSeatnumber(seats.getSeatnumber());
        seatdetails.setSeatstatus(seats.getSeatstatus());
        seatdetails.setPrice(seats.getPrice());
        seatdetails.setShowTime(seats.getShow().getShowtime());
        seatdetails.setMovieName(seats.getShow().getMovie().getMoviename());
        seatdetails.setTheatreName(seats.getShow().getTheatre().getTheatername());
        seatdetails.setMovieId(seats.getShow().getMovie().getMovieid());

        response.add(seatdetails);
      }

      return response;
    } else {
      throw new Exception("Show Not Founded");
    }
  }

  
  // Hold seats

  public String bookSeats(Map<String, Object> data) {

    Integer showId = Integer.valueOf(data.get("showId").toString());

    List<Integer> seatIds = (List<Integer>) data.get("seatIds");

    for (Integer seatId : seatIds) {

      Seat seat = seatrepository
          .findByShow_ShowidAndSeatid(showId, seatId)
          .orElseThrow(() -> new RuntimeException("Seat not found"));

      if (seat.getSeatstatus() == SeatStatus.Booked) {
        throw new RuntimeException("Seat already booked");
      }

      if (seat.getSeatstatus() == SeatStatus.Hold) {
        throw new RuntimeException(
            seat.getSeatnumber() + " is hold");
      }
      
      seat.setSeatstatus(SeatStatus.Hold);
      seat.setHoldtime(LocalDateTime.now());
      seatrepository.save(seat);
    }


    return "Seats Holded Successfully";
  }

  // UnHold Automatically

@Scheduled(fixedRate=60000)
public void UnHold(){

List<Seat> temp = seatrepository.findBySeatstatus(SeatStatus.Hold);

 if(!temp.isEmpty()){
  for(Seat seat:temp){
   Long expiry = ChronoUnit.MINUTES.between(seat.getHoldtime(),LocalDateTime.now());

   if(expiry>=5){
    seat.setSeatstatus(SeatStatus.Available);
    seat.setHoldtime(null);
    seatrepository.save(seat);
  
   }

  }
}
}

}
