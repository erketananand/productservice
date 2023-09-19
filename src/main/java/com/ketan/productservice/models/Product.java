package com.ketan.productservice.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseModel {
    private String title;
    private String description;
    private String image;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Price price;

}
