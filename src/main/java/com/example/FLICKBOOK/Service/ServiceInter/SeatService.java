package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Model.Seat;

@Service
public interface SeatService {

      public List<Seat> GetSeats(Integer showid) throws Exception ;

       public String bookSeats(Map<String, Object> data) ;
}
