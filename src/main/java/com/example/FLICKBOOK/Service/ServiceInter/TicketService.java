package com.example.FLICKBOOK.Service.ServiceInter;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Model.Ticket;

@Service
public interface TicketService {

   public Object StoreTickets(Map<String,Object> datas) ;

   //Tickets Get BY Id
     public Object getTicketsById(Integer userid) ;

     //get ticket by ticketid
      public Map<String,Object> GetTicketByTId(Integer ticketid) ;

}
