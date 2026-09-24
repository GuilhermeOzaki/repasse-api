package com.portfolio.repasse.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class TratadorDeErros {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tratarArgumentoInvalido(IllegalArgumentException illegalArgumentException){
        return illegalArgumentException.getMessage();
    }

}
