package com.ketan.productservice.dtos;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
public class GenericCategoryDto {
    private Long id;
    private String name;
}
