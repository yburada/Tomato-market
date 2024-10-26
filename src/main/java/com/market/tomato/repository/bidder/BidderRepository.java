package com.market.tomato.repository.bidder;

import com.market.tomato.model.bidder.Bidder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BidderRepository extends JpaRepository<Bidder, Integer> {

    @Query("SELECT a FROM Bidder a WHERE FUNCTION('DATE', a.localDateTime) = :date AND a.lotNumber = :lotNumber")
    Optional<Bidder> findByLotNumber(@Param("date") LocalDate date,@Param("lotNumber") Integer lotNumber);
}
