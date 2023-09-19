package com.ketan.productservice.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price extends BaseModel {
    String currency;
    double price;
}
