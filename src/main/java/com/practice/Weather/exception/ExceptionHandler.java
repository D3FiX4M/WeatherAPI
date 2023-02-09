package com.practice.Weather.exception;

import com.practice.Weather.payload.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<MessageResponse> existException(ExistException ex){
        return ResponseEntity.status(HttpStatus.IM_USED)
                .body(new MessageResponse(ex.getMessage()));
    }

    public ResponseEntity<MessageResponse> notFoundException(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(ex.getMessage()));
    }

}
