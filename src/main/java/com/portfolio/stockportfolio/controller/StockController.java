package com.portfolio.stockportfolio.controller;

import com.portfolio.stockportfolio.service.StockService;
import com.portfolio.stockportfolio.util.StockQuoteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @GetMapping("/test-retry")
    public String testRetry(){
        stockService.fetchQuote("AAPL");
        return "done";
    }

    @GetMapping("/{symbol}/quote")
    public ResponseEntity<StockQuoteResponse> getQuote(@PathVariable String symbol){
        return ResponseEntity.ok(stockService.fetchQuote(symbol));
    }
}
