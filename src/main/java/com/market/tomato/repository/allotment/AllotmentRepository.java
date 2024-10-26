package com.market.tomato.repository.allotment;

import com.market.tomato.model.allotment.Allotment;
import com.market.tomato.model.save.Farmer;
import com.market.tomato.model.save.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AllotmentRepository extends JpaRepository<Allotment, Integer> {

    @Query("SELECT SUM(a.numberOfBoxes) FROM Allotment a WHERE FUNCTION('DATE', a.localDateTime) = :date AND a.vehicle = :vehicleNumber")
    Optional<Integer> getSumOfBoxesByVehicleNumberOnToday(@Param("date") LocalDate date, @Param("vehicleNumber") Vehicle vehicleNumber);

    @Query("SELECT a FROM Allotment a WHERE FUNCTION('DATE', a.localDateTime) = :date AND a.vehicle = :vehicleNumber")
    Optional<List<Allotment>> findByVehicle(@Param("date") LocalDate date, @Param("vehicleNumber") Vehicle vehicleNumber);

    @Query("SELECT a FROM Allotment a WHERE FUNCTION('DATE', a.localDateTime) = :date AND a.farmer = :farmerId")
    Optional<Allotment> findByFarmerAndDate(@Param("date") LocalDate date, @Param("farmerId")Farmer farmerId);
}
