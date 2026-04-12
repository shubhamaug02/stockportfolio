package com.portfolio.stockportfolio.entity;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}

