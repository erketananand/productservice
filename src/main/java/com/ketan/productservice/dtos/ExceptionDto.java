package com.ketan.productservice.dtos;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Getter
public class ExceptionDto {
    private HttpStatus status;
    private String message;
}
