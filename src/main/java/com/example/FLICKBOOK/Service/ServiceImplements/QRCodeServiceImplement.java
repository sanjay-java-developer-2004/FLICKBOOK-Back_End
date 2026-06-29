package com.example.FLICKBOOK.Service.ServiceImplements;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FLICKBOOK.Enum.TicketStatus;
import com.example.FLICKBOOK.Model.Seat;
import com.example.FLICKBOOK.Model.Ticket;
import com.example.FLICKBOOK.Repository.TicketRepository;
import com.example.FLICKBOOK.Service.ServiceInter.QRCodeService;
import com.example.FLICKBOOK.Service.ServiceInter.TicketService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class QRCodeServiceImplement implements QRCodeService {

    @Autowired
    private TicketService ticketservice;

    @Autowired
    private TicketRepository ticketrepository;

    @Override
    public Map<String, Object> GenerateQRCode(Map<String, Object> response) throws Exception {

        Ticket res = (Ticket) ticketservice.StoreTickets(response);

        System.out.println("Response geted  from ticket");

        String mname = res.getBooking().getShow().getMovie().getMoviename();
        String tname = res.getBooking().getShow().getTheatre().getTheatername();
        LocalTime stime = res.getBooking().getShow().getShowtime();
        List<Seat> seat = res.getBooking().getSeats();
        String tikno = res.getTicketnumber();
        TicketStatus status = res.getStatus();

        String formattedTime = stime.format(DateTimeFormatter.ofPattern("hh:mm a"));

        System.out.println("response stored to temp map");

        String seatNumbers = seat.stream()
                .map(Seat::getSeatnumber)
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        System.out.println("seats conver to string");

        Map<String, Object> qrdatas = new HashMap<>();

        qrdatas.put("Tittle", mname);
        qrdatas.put("TheatreName", tname);
        qrdatas.put("ShowTime", formattedTime);
        qrdatas.put("Seats", seatNumbers);
        qrdatas.put("TicketNumber", tikno);
        qrdatas.put("TicketStatus", status);

        System.out.println("response convert to map successfully");
        // System.out.println("QR DATAS : "+qrdatas );

        QRCodeWriter qrwriter = new QRCodeWriter();

        System.out.println(qrdatas.toString());
        System.out.println(qrdatas.toString().length());

        BitMatrix bitmatrix = qrwriter.encode(qrdatas.toString(), BarcodeFormat.QR_CODE, 800, 800);

        ByteArrayOutputStream outputstreem = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitmatrix, "PNG", outputstreem);

        byte[] qrimg = outputstreem.toByteArray();

        System.out.println("qr converted to byte");

        Optional<Ticket> temp = ticketrepository.findById(res.getTicketid());

        if (temp.isPresent()) {

            Ticket updateticket = temp.get();

            updateticket.setQrcode(qrimg);

            ticketrepository.save(updateticket);
            System.out.println("Qr updated");

        }

        String qrtype = "PNG";
        String base64code = Base64.getEncoder().encodeToString(qrimg);

        qrdatas.put("QRCode", base64code);
        qrdatas.put("Mobile", res.getBooking().getUser().getPhonenumber());

        System.out.println("base64 workred");

        Map<String,Object> finalresponse = new HashMap<>();

        finalresponse.put("Tittle", mname);
        finalresponse.put("TheatreName", tname);
        finalresponse.put("TheatreLocation",res.getBooking().getShow().getTheatre().getTheaterlocation());
        finalresponse.put("ShowTime", formattedTime);
        finalresponse.put("Showdate", res.getBooking().getShow().getShowdate());
        finalresponse.put("Seats", seatNumbers);
        finalresponse.put("TicketNumber", tikno);
        finalresponse.put("TicketStatus", status);
        finalresponse.put("Poster",res.getBooking().getShow().getMovie().getImgdata());
        finalresponse.put("Postertype", res.getBooking().getShow().getMovie().getImgtype());
        finalresponse.put("Censor", res.getBooking().getShow().getMovie().getCensorcertificate());
        finalresponse.put("Genere", res.getBooking().getShow().getMovie().getMoviegenre());
        finalresponse.put("Duration", res.getBooking().getShow().getMovie().getMovieduration());
        finalresponse.put("QRCode", base64code);
        finalresponse.put("QRType", qrtype);

        return finalresponse;

    }

}

