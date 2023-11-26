package com.ale.broadcast.handler.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebsocketExceptionHandler {
    @ExceptionHandler({ Exception.class })
    public void handleException(Exception e) {
        e.printStackTrace();
    }
}
