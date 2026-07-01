package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class MovieException extends FlickBookException {
    // MovieException — NOT_FOUND (404)
    public MovieException(String msg) {

        super(msg, HttpStatus.NOT_FOUND);
    }

}
