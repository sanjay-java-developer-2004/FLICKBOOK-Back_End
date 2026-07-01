package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class SeatException extends FlickBookException {
    // SeatException — CONFLICT (409)
    public SeatException(String msg) {

        super(msg, HttpStatus.CONFLICT);
    }

}
