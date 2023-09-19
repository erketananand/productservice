package com.ketan.productservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
public class GenericPriceDto {
    private Long id;
    private String currency;
    private double price;
}
