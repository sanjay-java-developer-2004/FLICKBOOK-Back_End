package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class UserException extends FlickBookException {
    // UserException — BAD_REQUEST (400)
    public UserException(String msg) {

        super(msg, HttpStatus.BAD_REQUEST);

    }

}
