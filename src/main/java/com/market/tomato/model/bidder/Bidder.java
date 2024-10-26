package com.market.tomato.model.bidder;

import com.market.tomato.model.save.Buyer;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bidder_table")
public class Bidder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer biddingId;

    @Column(name = "lot_number", nullable = false)
    private Integer lotNumber;

    @ManyToOne
    @JoinColumn(name = "buyer_Id_ref", referencedColumnName = "buyer_Id", nullable = false)
    private Buyer buyer;

    @Column(name = "price_per_box", nullable = false)
    private Integer price;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime localDateTime;

    public Bidder() {}

    public Bidder(Integer lotNumber, Buyer buyer, Integer price, LocalDateTime localDateTime) {
        this.lotNumber = lotNumber;
        this.buyer = buyer;
        this.price = price;
        this.localDateTime = localDateTime;
    }

    public Integer getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(Integer biddingId) {
        this.biddingId = biddingId;
    }

    public Integer getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(Integer lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
