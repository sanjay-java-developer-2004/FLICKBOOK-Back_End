package com.example.FLICKBOOK.Service.ServiceImplements;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.FLICKBOOK.Service.ServiceInter.QRCodeService;
import com.example.FLICKBOOK.Service.ServiceInter.WhatsAppService;

@Service
public class WhatsAppServiceImplement implements WhatsAppService {

    @Autowired
    private QRCodeService qrservice;

    private static final String ACCESS_TOKEN = "EAF9pDkLtFe8BR77BURKhzpwHWruAE3TYf5jbRwq8Qn0obVeZBdxX24ZCJuQYj2w7q1rlNewBQLiMgxIihXdmnhyu0EzdfQEAcWqZCfi6oPUp7keU2GzUlSfVAEwRO0JF0ZAi4tBQoOh0v9ZBi5RRnb8VbeIL1w6nCiHxeO3zsKoeD6YqINzqsxcdatceS59lHrfZBywD4blzSiDGkhlBcdjhXcfvy5EzTgtkDZBkZCkn2S3B9s3SzG4o07k798mdZCgJUEaYCcwm9ZCg0biSY9KZBlX";
    private static final String PHONE_NUMBER_ID = "1074610039080092";

    @Override
    public Map<String, Object> SendToWhatsApp(Map<String, Object> datas) throws Exception {

        Map<String, Object> res = qrservice.GenerateQRCode(datas);

        // String mobilenumber = res.get("Mobile").toString();
        String mobilenumber = "+91 9751620646";
        String baseimg = res.get("QRCode").toString();

        byte[] qrcode = Base64.getDecoder().decode(baseimg);

        System.out.println("Succcessfully Img Decoded");

        // create file to tempstorage
        File file = File.createTempFile("QRCODE", ".PNG");
        Files.write(file.toPath(), qrcode);

        String message = String.format("""

                           🎬 *FLICKBOOK - Booking Confirmed!*
                ━━━━━━━━━━━━━━━━━

                ✅ *Your ticket is confirmed!*

                🎟️ *Ticket No:*  `%s`
                🎥 *Movie:*  %s
                🏛️ *Theatre:*  %s
                🕐 *Show Time:*  %s
                💺 *Seats:*  %s

                ━━━━━━━━━━━━━━━━━
                📌 *Important:*
                • Please arrive *15 minutes* before showtime
                • Tickets are *non-refundable*
                • Carry a valid ID proof

                🙏 *Thank you for booking with FLICKBOOK!*
                
                _Enjoy your movie experience_ 🍿
                       
            """,

                res.get("TicketNumber").toString(),
                res.get("Tittle").toString(),
                res.get("TheatreName").toString(),
                res.get("ShowTime").toString(),
                res.get("Seats").toString()

        );

        System.out.println("Successfully Strings Formated");
        // upload to http header

        HttpHeaders uploadheader = new HttpHeaders();

        uploadheader.setBearerAuth(ACCESS_TOKEN);
        uploadheader.setContentType(MediaType.MULTIPART_FORM_DATA);

        System.out.println("Success fully created  uploader header");

        // multimapping

        MultiValueMap<String, Object> mediabody = new LinkedMultiValueMap<>();

        mediabody.add("messaging_product", "whatsapp");
        mediabody.add("file", new FileSystemResource(file));

        System.out.println("Media body successfull");

        // creating resttempleate

        RestTemplate resttemplate = new RestTemplate();

        // media response

        ResponseEntity<Map> mediaresponse = resttemplate.postForEntity(
                "https://graph.facebook.com/v25.0/" + PHONE_NUMBER_ID + "/media",
                new HttpEntity<>(mediabody, uploadheader),
                Map.class);

        System.out.println("Success fully mediareponse uploaded ");

        // geting id in mediaresponse

        String mediaId = mediaresponse.getBody().get("id").toString();

        System.out.println("successfully get media id");

        // creating message httpheader for send to what'sapp this header and payload
        // carry datas

        HttpHeaders msgHeader = new HttpHeaders();
        msgHeader.setBearerAuth(ACCESS_TOKEN);
        msgHeader.setContentType(MediaType.APPLICATION_JSON);

        System.out.println("successfully created final header");

        // create payload

        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "to", mobilenumber,
                "type", "document",
                "document", Map.of(
                        "id", mediaId,
                        "filename", "TicketQR",
                        "caption", message));

        System.out.println("successfully payload");

        // finnaly send to whatsapp

        ResponseEntity<String> response = resttemplate.postForEntity(
                "https://graph.facebook.com/v25.0/" + PHONE_NUMBER_ID + "/messages",
                new HttpEntity<>(payload, msgHeader),
                String.class);

        System.out.println("succesfully sended");

        // deleting stored temp file

        file.delete();

        // return previous response

        return res;

    }

}
