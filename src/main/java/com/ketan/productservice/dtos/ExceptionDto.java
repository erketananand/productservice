package com.ketan.productservice.dtos;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExceptionDto {
    private HttpStatus status;
    private String message;
}
