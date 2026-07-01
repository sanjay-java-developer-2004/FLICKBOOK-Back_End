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
import com.example.FLICKBOOK.Exception.SeatException;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Repository.SeatRepository;
import com.example.FLICKBOOK.Service.ServiceInter.SeatService;

@Service
public class SeatServiceImplement implements SeatService {

  @Autowired
  private SeatRepository seatrepository;

  // get seats
  @Override
  public List<Seat> GetSeats(Integer showid) throws SeatException {

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
      throw new SeatException("Show Not Founded");
    }
  }

  // Hold seats

  public String bookSeats(Map<String, Object> data) throws SeatException {

    Integer showId = Integer.valueOf(data.get("showId").toString());

    List<Integer> seatIds = (List<Integer>) data.get("seatIds");

    for (Integer seatId : seatIds) {

      Seat seat = seatrepository
          .findByShow_ShowidAndSeatid(showId, seatId)
          .orElseThrow(() -> new SeatException("Seat not found"));

      if (seat.getSeatstatus() == SeatStatus.Booked) {
        throw new SeatException("Seat already booked");
      }

      if (seat.getSeatstatus() == SeatStatus.Hold) {
        throw new SeatException(
            seat.getSeatnumber() + " is Already hold pls Choos Other Seat`");
      }

      seat.setSeatstatus(SeatStatus.Hold);
      seat.setHoldtime(LocalDateTime.now());
      seatrepository.save(seat);
    }

    return "Seats Holded Successfully";
  }

  // UnHold Automatically

  @Scheduled(fixedRate = 60000)
  public void UnHold() {

    List<Seat> temp = seatrepository.findBySeatstatus(SeatStatus.Hold);

    if (!temp.isEmpty()) {
      for (Seat seat : temp) {
        Long expiry = ChronoUnit.MINUTES.between(seat.getHoldtime(), LocalDateTime.now());

        if (expiry >= 5) {
          seat.setSeatstatus(SeatStatus.Available);
          seat.setHoldtime(null);
          seatrepository.save(seat);

        }

      }
    }
  }

  // unhold mannuly

  @Override
  public String releaseSeats(Map<String, Object> seatids) throws SeatException {

    List<?> seats = (List<?>) seatids.get("seatIds");

    if (seats.isEmpty()) {

      throw new SeatException("Seat Id Not Founded");

    } else {

      for (Object id : seats) {

        Integer seatId = ((Number) id).intValue();

        Seat seat = seatrepository.findById(seatId)
            .orElseThrow(() -> new SeatException("Seat not found"));

        if (seat.getSeatstatus() == SeatStatus.Booked) {
          throw new SeatException("Seat already booked");

        }
        if (seat.getSeatstatus() == SeatStatus.Available) {
          throw new SeatException("Seat is Already Available");
        }

        if (seat.getSeatstatus() == SeatStatus.Hold) {
          seat.setSeatstatus(SeatStatus.Available);
          seatrepository.save(seat);
          return "Seat UnHold SuccessFully";
        }

      }
      throw new SeatException("Something Wrong ...");

    }

  }

}
