package com.example.FLICKBOOK.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public String HandelException(UserException exe){ 
        return exe.getMessage();
    }

    @ExceptionHandler(MovieException.class)
    public String HandelException(MovieException exe){
        return exe.getMessage();
    }

    @ExceptionHandler(HomeException.class)
    public String HandelException(HomeException exe){
        return exe.getMessage();
    }


    @ExceptionHandler(TheatreException.class)
    public String HandelException(TheatreException exe){
        return exe.getMessage();
    }


    
}