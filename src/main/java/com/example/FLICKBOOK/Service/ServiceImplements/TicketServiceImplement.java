package com.example.FLICKBOOK.Service.ServiceImplements;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Enum.TicketStatus;
import com.example.FLICKBOOK.Model.Booking;
import com.example.FLICKBOOK.Model.Payment;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Model.Ticket;
import com.example.FLICKBOOK.Repository.TicketRepository;
import com.example.FLICKBOOK.Service.ServiceInter.SeatBookingService;
import com.example.FLICKBOOK.Service.ServiceInter.TicketService;

@Service
public class TicketServiceImplement implements TicketService {

    @Autowired
    private TicketRepository ticketrepository;

    @Autowired
    private SeatBookingService seatbookingservice;

    @Override
    public Object StoreTickets(Map<String, Object> datas) {

        Map<String, Object> temp = (Map<String, Object>) seatbookingservice.PayNow(datas);

        System.out.println("Response geted to booking successfully ");

        Booking book = (Booking) temp.get("booking");
        Payment payments = (Payment) temp.get("payment");

        Ticket tickets = new Ticket();

        tickets.setBooking(book);
        tickets.setPaymenttype(payments.getPaymentmethod());
        tickets.setTicketnumber("TIK" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) +
                (int) (Math.random() * 100));

        tickets.setStatus(TicketStatus.NOT_VERIFIED);

        Ticket ticketresponse = ticketrepository.save(tickets);

        System.out.println("Ticket saved successfully");

        return ticketresponse;

    }

    // get by user id

    @Override
    public Object getTicketsById(Integer userid) {

        List<Ticket> temp = ticketrepository.findByBookingUserUserid(userid);

        List<Map<String, Object>> response = new ArrayList<>();

        if (temp.isEmpty()) {
            throw new RuntimeException("Tickets Not Founded For This User");
        } else {

            for (Ticket ticket : temp) {

                LocalTime stime = ticket.getBooking().getShow().getShowtime();
                String formattedTime = stime.format(DateTimeFormatter.ofPattern("hh:mm a"));
                String base64img = Base64.getEncoder().encodeToString(ticket.getBooking().getShow().getMovie().getImgdata());
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("Tittle", ticket.getBooking().getShow().getMovie().getMoviename());
                map.put("Theatre", ticket.getBooking().getShow().getTheatre().getTheatername());
                map.put("Poster", base64img);
                map.put("ImgType", ticket.getBooking().getShow().getMovie().getImgtype());
                map.put("TicketStatus", ticket.getStatus());
                map.put("ShowDate", ticket.getBooking().getShow().getShowdate());
                map.put("ShowTime", formattedTime);
                map.put("TicketId", ticket.getTicketid());

                response.add(map);
                System.out.println("Response Added");
            }

            System.out.println("Response sended ");
            return response;
        }

    }


    

    @Override
    public Map<String, Object> GetTicketByTId(Integer ticketid) {

        Optional<Ticket> tickets = ticketrepository.findById(ticketid);

        if (tickets.isEmpty()) {
            throw new RuntimeException("Ticket NOt Founded");

        } else {

            Ticket ticket = tickets.get();

            // 2. Ticket → get Booking
            Booking booking = ticket.getBooking();

            // 3. Booking → get Seats (only seats booked in THIS booking)
            List<Seat> seats = booking.getSeats();

            // 4. Seats → get seat numbers only
            String seatNumbers = seats.stream()
                    .map(Seat::getSeatnumber)
                    .collect(Collectors.joining(", ")); // "A1, A2, B3"

            Map<String, Object> response = new HashMap<>();

            LocalTime stime = ticket.getBooking().getShow().getShowtime();
            String formattedTime = stime.format(DateTimeFormatter.ofPattern("hh:mm a"));

            String base64code = Base64.getEncoder().encodeToString(ticket.getQrcode());

            String base64Poster = Base64.getEncoder().encodeToString(
                    ticket.getBooking().getShow().getMovie().getImgdata() 
            );

            response.put("Tittle", ticket.getBooking().getShow().getMovie().getMoviename());
            response.put("Theatre", ticket.getBooking().getShow().getTheatre().getTheatername());
            response.put("Poster", base64Poster);
            response.put("ImgType", ticket.getBooking().getShow().getMovie().getImgtype());
            response.put("TicketStatus", ticket.getStatus());
            response.put("ShowDate", ticket.getBooking().getShow().getShowdate());
            response.put("ShowTime", formattedTime);
            response.put("Seats", seatNumbers);
            response.put("TheatreLocation", ticket.getBooking().getShow().getTheatre().getTheaterlocation());
            response.put("Censor", ticket.getBooking().getShow().getMovie().getCensorcertificate());
            response.put("Genere", ticket.getBooking().getShow().getMovie().getMoviegenre());
            response.put("Duration", ticket.getBooking().getShow().getMovie().getMovieduration());
            response.put("TicketNumber", ticket.getTicketnumber());
            response.put("QRCode", base64code); 
            response.put("QRType", "PNG");

            return response;
        }

    }
    
}
