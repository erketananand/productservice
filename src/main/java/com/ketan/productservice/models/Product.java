package com.ketan.productservice.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Product extends BaseModel {
    private String title;
    private String description;
    private String image;
    private Category category;
    private double price;
}
