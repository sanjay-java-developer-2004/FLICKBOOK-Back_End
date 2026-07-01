package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class QRCodeException extends FlickBookException {
    // QRCodeException — BAD_REQUEST (400)
    public QRCodeException(String msg) {

        super(msg, HttpStatus.BAD_REQUEST);
    }

}
