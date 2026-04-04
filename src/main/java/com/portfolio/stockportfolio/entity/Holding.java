package com.portfolio.stockportfolio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Holding {

    @Id
    @GeneratedValue
    private Long id;
    private String symbol;
    private Integer quantity;
    private Double avgPrice;
}

