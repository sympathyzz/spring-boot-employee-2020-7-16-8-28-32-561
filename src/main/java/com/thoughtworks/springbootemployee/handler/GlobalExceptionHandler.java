package com.thoughtworks.springbootemployee.handler;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String NOT_FOUND_DATA = "Oh not found data!";

    @ExceptionHandler(NoSuchDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String noSuchDataExceptionHandler(){
        return NOT_FOUND_DATA;
    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String illegalOptionExceptionHandler(){
        return "Illegal option";
    }

}
