package com.example.FLICKBOOK.Service.ServiceImplements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.FLICKBOOK.Enum.PaymentStatus;
import com.example.FLICKBOOK.Enum.SeatStatus;
import com.example.FLICKBOOK.Exception.BookingException;
import com.example.FLICKBOOK.Model.Booking;
import com.example.FLICKBOOK.Model.Payment;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Model.Show;
import com.example.FLICKBOOK.Model.User;
import com.example.FLICKBOOK.Repository.BookingRepository;
import com.example.FLICKBOOK.Repository.PaymentRepository;
import com.example.FLICKBOOK.Repository.SeatRepository;
import com.example.FLICKBOOK.Repository.ShowRepository;
import com.example.FLICKBOOK.Repository.UserRepository;
import com.example.FLICKBOOK.Service.ServiceInter.SeatBookingService;

@Service
public class SeatBookingServiceImplement implements SeatBookingService {

    @Autowired
    private BookingRepository bookingrepository;

    @Autowired
    private SeatRepository seatrepository;

    @Autowired
    private ShowRepository showrepository;

    @Autowired
    private PaymentRepository paymentrepository;

    @Autowired
    private UserRepository userrepository;

    @Transactional
    @Override
    public Object PayNow(Map<String, Object> datas) throws BookingException {

        Integer showid = Integer.valueOf(datas.get("showId").toString());
        List<Integer> seatids = (List<Integer>) datas.get("seatIds");
        double fronttotal = Double.valueOf(datas.get("Total").toString());
        Optional<User> userid = userrepository.findByUsername(datas.get("UserName").toString());

        List<Seat> bookedseats = new ArrayList<>();
        double totalamount = 0;

        for (Integer seatid : seatids) {

            Seat seat = seatrepository.findByShow_ShowidAndSeatid(showid, seatid)
                    .orElseThrow(() -> new BookingException("Seats Not Founded For this SeatNumber"));

            if (seat.getSeatstatus() == SeatStatus.Booked) {
                throw new BookingException("Sorry This Seat " + seat.getSeatnumber() + " Is Already Booked");
            }


            if (seat.getSeatstatus() == SeatStatus.Available) {
                throw new BookingException(
                        "The Seat " + seat.getSeatnumber() + " Is Not Hold Pls Hold Your Seat First");
            }

            if (seat.getSeatstatus() == SeatStatus.Hold) {
                seat.setSeatstatus(SeatStatus.Booked);
                seat.setHoldtime(null);

                bookedseats.add(seat);
                totalamount += seat.getPrice();

            }

        }
        
        if (totalamount == fronttotal) {
            seatrepository.saveAll(bookedseats);
       
        } else {
            throw new BookingException(
                    "Sorry, we're experiencing some payment issues. We'll fix them soon. Please try again after a few minutes."
                            + "front " + fronttotal + " " + "back " + totalamount);
        }

        Optional<Show> show1 = showrepository.findById(showid);

        if (show1.isEmpty()) {
            throw new BookingException("Show Details Not Founded For This Id");
        }
        Show show = show1.get();

        if (datas.get("PaymentMethod") == null) {
            throw new BookingException("Payment Not Founded");
        }
        
        if(userid.isEmpty()){
            throw new BookingException("Your Account Is Not Logined PLs Login First! ....");
        }

        

        User user = userid.get();
        
        Booking bookingdetails = new Booking();

        bookingdetails.setBookingtime(LocalDateTime.now());
        bookingdetails.setUser(user);
        bookingdetails.setTotalamount(totalamount);
        bookingdetails.setSeats(bookedseats);
        bookingdetails.setShow(show);
        bookingrepository.save(bookingdetails);

        // payment entry

        Payment entry = new Payment();

        entry.setBooking(bookingdetails);
        entry.setPaymentmethod(datas.get("PaymentMethod").toString());
        entry.setAmount(totalamount);
        entry.setPaymentstatus(PaymentStatus.Success);
        paymentrepository.save(entry);

        //tickets entry 

        Map<String,Object> response = new HashMap<>();

        response.put("booking",bookingdetails);
        response.put("payment",entry);
        
        return response;


    }

}
