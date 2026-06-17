package com.portfolio.stockportfolio.dto;

public record HoldingResponse(String symbol, Integer quantity, Double avgPrice) {
}
