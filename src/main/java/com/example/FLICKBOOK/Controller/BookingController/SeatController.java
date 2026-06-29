package com.example.FLICKBOOK.Controller.BookingController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Service.ServiceInter.SeatService;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatservice;


    @GetMapping("/seat/{showid}")
    public List<Seat> GetSeats(@PathVariable Integer showid) throws Exception {
        return seatservice.GetSeats(showid);
    }

    @PatchMapping("/book")
    public String bookSeats(@RequestBody Map<String, Object> data) {

    return seatservice.bookSeats(data);
}
}
