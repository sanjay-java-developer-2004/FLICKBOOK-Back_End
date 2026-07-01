package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class AuthException extends FlickBookException {
    // AuthException — UNAUTHORIZED (401)
    public AuthException(String msg) {

        super(msg, HttpStatus.UNAUTHORIZED);
    }

}
