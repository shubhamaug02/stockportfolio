package com.portfolio.stockportfolio.controller;

import com.portfolio.stockportfolio.dto.BuyRequest;
import com.portfolio.stockportfolio.dto.HoldingResponse;
import com.portfolio.stockportfolio.dto.SellRequest;
import com.portfolio.stockportfolio.entity.User;
import com.portfolio.stockportfolio.service.HoldingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService){
        this.holdingService = holdingService;
    }

    private User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public ResponseEntity<List<HoldingResponse>> getAllHoldings() {
        return ResponseEntity.ok(holdingService.getAllHoldings(getCurrentUser()).stream().map(h -> new HoldingResponse(h.getSymbol(),h.getQuantity(), h.getAvgPrice())).toList());
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buyStocks(@Valid @RequestBody BuyRequest buyRequest) {
         holdingService.buyStock(buyRequest.symbol(), buyRequest.quantity(), buyRequest.avgPrice(), getCurrentUser());
         return ResponseEntity.ok().build();
    }

    @PostMapping("/sell")
    public ResponseEntity<Void> sellStocks(@Valid @RequestBody SellRequest sellRequest) {
         holdingService.sellStocks(sellRequest.symbol(), sellRequest.quantity(), getCurrentUser());
         return ResponseEntity.ok().build();
    }
}
