package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class HomeException extends FlickBookException {
    // HomeException — INTERNAL_SERVER_ERROR (500)
    public HomeException(String msg) {

        super(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
