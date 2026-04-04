package com.portfolio.stockportfolio.controller;

import com.portfolio.stockportfolio.entity.Holding;
import com.portfolio.stockportfolio.service.HoldingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService){
        this.holdingService = holdingService;
    }

    @GetMapping
    public ResponseEntity<List<Holding>> getAllHoldings() {
        return ResponseEntity.ok(holdingService.getAllHoldings());
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buyStocks(@RequestBody Holding holding) {
         holdingService.buyStock(holding.getSymbol(), holding.getQuantity(), holding.getAvgPrice());
         return ResponseEntity.ok().build();
    }

    @PostMapping("/sell")
    public ResponseEntity<Void> sellStocks(@RequestBody Holding holding) {
         holdingService.sellStocks(holding.getSymbol(), holding.getQuantity());
         return ResponseEntity.ok().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
