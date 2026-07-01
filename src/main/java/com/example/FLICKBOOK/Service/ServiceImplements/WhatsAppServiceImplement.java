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

        private static final String ACCESS_TOKEN = "EAF9pDkLtFe8BR9e2UuMZCze6LYkiufa5JZC0jrjDwKhIHPLzqWqRpvTo9s2iPdAZAVuoryff4ZAtZAlYOili692ZCaDYPslUiLSRAsdP19ATcZBVeJF0kwctQUZAkYrxnFKlGlbmHdRDrbhznDYCAXWT68w33pqf5SZBJf54CWWwqPQiMKzr0VSfOIXMzZBen1F97ESuQJvMSG3Gy1B2shMcEq2VE09ILe6wyolxWcnvmvRMjzCgMNvjdeJh8GTBqKeZAT02EvrKt1zGIdjrv2OmHJY";
        private static final String PHONE_NUMBER_ID = "1074610039080092";

        @Override
        public Map<String, Object> SendToWhatsApp(Map<String, Object> datas) throws Exception {

                Map<String, Object> res = qrservice.GenerateQRCode(datas);

                // String mobilenumber = res.get("Mobile").toString();

                String mobilenumber = "+91 9751620646";
                String baseimg = res.get("QRCode").toString();

                byte[] qrcode = Base64.getDecoder().decode(baseimg);

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

                // upload to http header

                HttpHeaders uploadheader = new HttpHeaders();

                uploadheader.setBearerAuth(ACCESS_TOKEN);
                uploadheader.setContentType(MediaType.MULTIPART_FORM_DATA);

                // multimapping

                MultiValueMap<String, Object> mediabody = new LinkedMultiValueMap<>();

                mediabody.add("messaging_product", "whatsapp");
                mediabody.add("file", new FileSystemResource(file));

                // creating resttempleate

                RestTemplate resttemplate = new RestTemplate();

                try {

                        // media response

                        ResponseEntity<Map> mediaresponse = resttemplate.postForEntity(
                                        "https://graph.facebook.com/v25.0/" + PHONE_NUMBER_ID + "/media",
                                        new HttpEntity<>(mediabody, uploadheader),
                                        Map.class);

                        if (mediaresponse.getBody() == null || mediaresponse.getBody().get("id") == null) {
                                throw new Exception("No media id returned from WhatsApp media upload");
                        }

                        // geting id in mediaresponse

                        String mediaId = mediaresponse.getBody().get("id").toString();

                        // creating message httpheader for send to what'sapp this header and payload
                        // carry datas

                        HttpHeaders msgHeader = new HttpHeaders();
                        msgHeader.setBearerAuth(ACCESS_TOKEN);
                        msgHeader.setContentType(MediaType.APPLICATION_JSON);

                        // create payload

                        Map<String, Object> payload = Map.of(
                                        "messaging_product", "whatsapp",
                                        "to", mobilenumber,
                                        "type", "document",
                                        "document", Map.of(
                                                        "id", mediaId,
                                                        "filename", "TicketQR",
                                                        "caption", message));

                        // finnaly send to whatsapp

                        ResponseEntity<String> response = resttemplate.postForEntity(
                                        "https://graph.facebook.com/v25.0/" + PHONE_NUMBER_ID + "/messages",
                                        new HttpEntity<>(payload, msgHeader),
                                        String.class);

                } catch (Exception e) {
                        throw new Exception(
                                        "Tickets Booked But We Are Faceing Issuess On Delevire Tickets Pls Collect Your Tickets in My Tickets",
                                        e);
                } finally {
                        // deleting stored temp file

                        file.delete();
                }

                // return previous response

                return res;

        }

}
