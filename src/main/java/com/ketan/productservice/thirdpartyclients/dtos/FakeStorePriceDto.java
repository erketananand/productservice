package com.ketan.productservice.thirdpartyclients.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FakeStorePriceDto {
    private Long id;
    private double price;
    private String currency;
}
