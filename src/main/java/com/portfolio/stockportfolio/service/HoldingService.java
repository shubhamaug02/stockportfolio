package com.portfolio.stockportfolio.service;

import com.portfolio.stockportfolio.entity.Holding;
import com.portfolio.stockportfolio.entity.User;
import com.portfolio.stockportfolio.repository.HoldingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;
    public HoldingService(HoldingRepository holdingRepository) {
         this.holdingRepository = holdingRepository;
    }

    public List<Holding> getAllHoldings(User user){
      return holdingRepository.findByUser(user);
    }

    public void buyStock(String symbol, Integer quantity, Double price, User user){
       Optional<Holding> existedHolding = holdingRepository.findByUserAndSymbol(user,symbol);
       Holding holding;
       if(existedHolding.isEmpty()){
           holding = new Holding();
           holding.setQuantity(quantity);
           holding.setAvgPrice((price));
           holding.setSymbol(symbol);
           holding.setUser(user);
         holdingRepository.save(holding);
       }
       else {
           holding = existedHolding.get();
           Integer currQuantity = holding.getQuantity();
           Double avgPrice = (holding.getAvgPrice()*currQuantity + price*quantity)/(currQuantity+quantity);
           holding.setAvgPrice(avgPrice);
           holding.setQuantity(quantity+currQuantity);
           holdingRepository.save(holding);
       }
    }

    public void sellStocks(String symbol, Integer quantity, User user) {
        Optional<Holding> existedHolding = holdingRepository.findByUserAndSymbol(user,symbol);
        if(existedHolding.isEmpty()){
            throw new RuntimeException("Holding not found for symbol: " + symbol);
        }
        Holding holding =  existedHolding.get();
        Integer currQuantity = holding.getQuantity();
        if(quantity<currQuantity) {
            holding.setQuantity(currQuantity-quantity);
            holdingRepository.save(holding);
        }
        else if(quantity.equals(currQuantity)){
            holdingRepository.delete(holding);
        }
        else {
           throw new RuntimeException("Cannot sell more than owned. Owned: " + currQuantity + ", Requested: " + quantity);
        }

    }

}
