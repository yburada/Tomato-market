package com.market.tomato.repository.billing;

import com.market.tomato.model.FarmerBillingDetails;
import com.market.tomato.model.allotment.Allotment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface FarmerBillingRepository extends JpaRepository<FarmerBillingDetails, Integer> {

    @Query("SELECT a FROM FarmerBillingDetails a WHERE FUNCTION('DATE', a.localDateTime) = :date AND a.allotment = :allotment")
    FarmerBillingDetails findByAllotment(@Param("allotment") Allotment allotment,@Param("date") LocalDate date);
}
