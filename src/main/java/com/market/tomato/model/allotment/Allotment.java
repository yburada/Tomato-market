package com.market.tomato.model.allotment;

import com.market.tomato.model.save.Farmer;
import com.market.tomato.model.save.Vehicle;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "allotment_table")
public class Allotment {

    @Id
    @Column(name = "allotment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_number")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "farmer_id", referencedColumnName = "farmerId")
    private Farmer farmer;
    @Column(name = "number_of_boxes", nullable = false)
    private Integer numberOfBoxes;
    @Column(name = "Date_Time",nullable = false)
    private LocalDateTime localDateTime;
    @Column(name = "lot_number", nullable = false)
    private Integer lotNumber;

    public Allotment() {}

    public Allotment(Vehicle vehicle, Farmer farmer, Integer numberOfBoxes, Integer lotNumber, LocalDateTime localDateTime) {
        this.vehicle = vehicle;
        this.farmer = farmer;
        this.numberOfBoxes = numberOfBoxes;
        this.lotNumber = lotNumber;
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public Integer getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(Integer numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public Integer getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(Integer lotNumber) {
        this.lotNumber = lotNumber;
    }
}
