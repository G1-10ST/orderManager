package com.mythik.orderManager.advice;

import com.mythik.orderManager.exception.ResourceNotFoundException;
import com.mythik.orderManager.model.response.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static com.mythik.orderManager.constants.CommonConstants.*;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {
        List<String> fieldErrors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getCode() + ": " + error.getDefaultMessage())
                .toList();


        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .fieldErrors(fieldErrors)
                        .code(OM_ARGUMENT_NOT_VALID_CODE)
                        .message(VALIDATION_FAILURE_MESSAGE)
                        .build(),
                ex.getStatusCode()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException ex) {


        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .code(OM_RESOURCE_NOT_FOUND_CODE)
                        .message(ex.getMessage())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }
}
