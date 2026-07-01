package com.example.FLICKBOOK.Controller.BookingController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FLICKBOOK.Exception.TicketException;
import com.example.FLICKBOOK.Service.ServiceInter.TicketService;

@RestController
@RequestMapping("/Tickets")
@CrossOrigin(origins = "*")
public class TicketsController {

    @Autowired
    private TicketService ticketservice;

    // get tickets by userid

    @GetMapping("/getTickets/{userid}")
    public Object getTicketsById(@PathVariable Integer userid) throws TicketException{
        return ticketservice.getTicketsById(userid);
    }

    // get tickets by id
    @GetMapping("/getticket/{ticketid}")
    public ResponseEntity<Object> getTicket(@PathVariable Integer ticketid) throws TicketException{
        try {
            return ResponseEntity.ok(ticketservice.GetTicketByTId(ticketid));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
