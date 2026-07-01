package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.SeatException;
import com.example.FLICKBOOK.Model.Seat;

@Service
public interface SeatService {

      public List<Seat> GetSeats(Integer showid) throws SeatException ;

       public String bookSeats(Map<String, Object> data) throws SeatException;
        public String releaseSeats(Map<String, Object>seatids) throws SeatException ;
}
