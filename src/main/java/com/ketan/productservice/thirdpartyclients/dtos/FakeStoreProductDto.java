package com.ketan.productservice.thirdpartyclients.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private String description;
    private String image;
    private FakeStoreCategoryDto category;
    private FakeStorePriceDto price;
}
