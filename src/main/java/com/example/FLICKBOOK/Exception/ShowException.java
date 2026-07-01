package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class ShowException extends FlickBookException {
    // ShowException — NOT_FOUND (404)
    public ShowException(String msg) {

        super(msg, HttpStatus.NOT_FOUND);
    }
}
