package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class WhatsAppException extends FlickBookException {
    // WhatsAppException — SERVICE_UNAVAILABLE (503)
    public WhatsAppException(String msg) {

        super(msg, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
