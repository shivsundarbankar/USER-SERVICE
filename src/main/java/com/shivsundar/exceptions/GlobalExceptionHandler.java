package com.shivsundar.exceptions;

import com.shivsundar.models.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResultNotFoundException.class)
    ResponseEntity<ApiResponse> handlerResultNotFoundException(ResultNotFoundException exception){
        ApiResponse apiResponse = ApiResponse.builder()
                .message(exception.getMessage())
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
}
