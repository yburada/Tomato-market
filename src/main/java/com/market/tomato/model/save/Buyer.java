package com.market.tomato.model.save;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "buyer")
public class Buyer {

    @Id
    @Column(name = "buyer_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buyerId;
    @Column(name = "buyer_name", nullable = false)
    @NotNull
    private String name;
    @Column(name = "buyer_contact_number", nullable = false)
    @NotNull
    private Long contactNumber;
    @Column(name = "buyer_address", nullable = false)
    @NotNull
    private String address;

    public Buyer(){}

    public Buyer(String buyerName, Long buyerContactNumber, String address) {
        this.name = buyerName;
        this.contactNumber = buyerContactNumber;
        this.address = address;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
