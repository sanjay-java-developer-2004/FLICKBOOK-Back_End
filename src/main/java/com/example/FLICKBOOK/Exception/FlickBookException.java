package com.example.FLICKBOOK.Exception;

import org.springframework.http.HttpStatus;

public class FlickBookException extends RuntimeException {

    private final HttpStatus status;

    public FlickBookException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
