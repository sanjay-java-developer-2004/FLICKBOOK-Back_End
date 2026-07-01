package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class BookingException extends FlickBookException {
    // BookingException — NOT_FOUND (404)
    public BookingException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
