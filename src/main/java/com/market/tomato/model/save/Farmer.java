package com.market.tomato.model.save;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

@Entity
@Table(name = "farmer")
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer farmerId;
    @Column(name = "farmer_name", nullable = false)
    @NotNull
    private String name;
    @Column(name = "farmer_contact_number", nullable = false)
    @NotNull
    private Long contactNumber;
    @Column(name = "farmer_address", nullable = false)
    @NotNull
    private String address;

    public Farmer(){}

    public Integer getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
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

    public Farmer(String name, Long contactNumber, String address) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.address = address;
    }
}
