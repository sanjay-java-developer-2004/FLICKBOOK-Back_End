package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class TicketException extends FlickBookException {

    // TicketException — NOT_FOUND (404)
    public TicketException(String msg) {

        super(msg, HttpStatus.NOT_FOUND);
    }

}
