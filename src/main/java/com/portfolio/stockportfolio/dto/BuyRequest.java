package com.portfolio.stockportfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BuyRequest(
        @NotBlank(message = "Symbol is required")
        String symbol,

        @NotNull(message = "Quantity is required")
        @Positive(message="Quantity must be greater than zero")
        Integer quantity,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        Double avgPrice
) {
}
