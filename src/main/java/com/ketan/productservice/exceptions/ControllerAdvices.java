package com.ketan.productservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ketan.productservice.dtos.ExceptionDto;

@ControllerAdvice
public class ControllerAdvices {
    /*
     * @ExceptionHandler(NotFoundException.class)
     * private ResponseEntity<ExceptionDto>
     * handleNotFoundException(NotFoundException ex) {
     * return new ResponseEntity<>(
     * new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage()),
     * HttpStatus.NOT_FOUND);
     * }
     */
}
