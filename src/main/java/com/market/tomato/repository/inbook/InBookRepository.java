package com.market.tomato.repository.inbook;

import com.market.tomato.model.inbook.InBook;
import com.market.tomato.model.save.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InBookRepository extends JpaRepository<InBook, Integer> {

    Optional<InBook> findByVehicle(Vehicle vehicle);
    void deleteByVehicle(Vehicle vehicle);
}
