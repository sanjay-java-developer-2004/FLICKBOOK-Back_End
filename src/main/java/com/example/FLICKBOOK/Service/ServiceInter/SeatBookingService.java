package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Exception.BookingException;


@Service
public interface SeatBookingService {

     public Object PayNow(Map<String,Object> datas) throws BookingException ;
}
