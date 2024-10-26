package com.market.tomato.model;

import com.market.tomato.model.allotment.Allotment;
import com.market.tomato.model.inbook.InBook;
import com.market.tomato.model.save.Farmer;
import com.market.tomato.model.save.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "farmer_billing_details")
public class FarmerBillingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer billingId;

    @ManyToOne
    @JoinColumn(name = "allotment_id", referencedColumnName = "allotment_id")
    private Allotment allotment;

    @Column(name = "localDateTime")
    private LocalDateTime localDateTime;

    @Column(name = "less", nullable = false)
    @NotNull
    private Integer less;

    FarmerBillingDetails(){}

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Integer getBillingId() {
        return billingId;
    }

    public void setBillingId(Integer billingId) {
        this.billingId = billingId;
    }

    public Allotment getAllotment() {
        return allotment;
    }

    public void setAllotment(Allotment allotment) {
        this.allotment = allotment;
    }

    public Integer getLess() {
        return less;
    }

    public void setLess(Integer less) {
        this.less = less;
    }

    public FarmerBillingDetails(Allotment allotment, LocalDateTime localDateTime, Integer less) {
        this.allotment = allotment;
        this.localDateTime = localDateTime;
        this.less = less;
    }
}
