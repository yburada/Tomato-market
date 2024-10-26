package com.market.tomato.model.save;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Column(name = "transport_name", nullable = false)
    @NotNull
    private String transportName;
    @Column(name = "contact_number", nullable = false)
    @NotNull
    private  Long contactNumber;
    @Column(name = "transport_address", nullable = false)
    @NotNull
    private String address;

    public Vehicle(){}
    public Vehicle(String vehicleNumber, String transportName, Long contactNumber, String address) {
        this.vehicleNumber = vehicleNumber;
        this.transportName = transportName;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
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
