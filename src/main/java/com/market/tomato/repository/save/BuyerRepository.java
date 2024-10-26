package com.market.tomato.repository.save;

import com.market.tomato.model.save.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer> {

    Optional<Buyer> findByBuyerId(Integer id);
}
