package com.portfolio.stockportfolio.repository;

import com.portfolio.stockportfolio.entity.Holding;
import com.portfolio.stockportfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding,Long> {
    Optional<Holding> findByUserAndSymbol(User user,String symbol);
    List<Holding> findByUser(User user);
}
