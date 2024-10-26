package com.market.tomato.repository.save;

import com.market.tomato.model.save.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer> {
    Optional<Farmer> findByFarmerId(Integer id);
}
