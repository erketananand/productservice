package com.ketan.productservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel {
    @ManyToMany
    @JoinTable(name = "order_product",
              joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
}
