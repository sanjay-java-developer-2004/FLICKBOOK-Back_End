package com.example.FLICKBOOK.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Enum.SeatStatus;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Model.Show;

import jakarta.transaction.Transactional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    List<Seat> findByShow_Showid(Integer showid);

    Optional<Seat> findByShow_ShowidAndSeatid(Integer showId, Integer seatId);

    List<Seat> findBySeatstatus(SeatStatus hold);

     @Modifying
     @Transactional
    void deleteByShow(Show show);

     List<Seat> findByShow_ShowidAndSeatstatus(Integer showid, SeatStatus booked);


}
