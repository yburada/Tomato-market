package com.market.tomato.model.inbook;

import com.market.tomato.model.save.Vehicle;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "In_Book")
public class InBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer InBookId;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_number")
    private Vehicle vehicle;
    @Column(name = "Date_Time",nullable = false)
    private LocalDateTime localDateTime;
    @Column(name = "Number_Of_Boxes", nullable = false)
    private Integer numberOfBoxes;

    public InBook() {}

    public InBook(Vehicle vehicle, LocalDateTime localDateTime, Integer numberOfBoxes) {
        this.vehicle = vehicle;
        this.localDateTime = localDateTime;
        this.numberOfBoxes = numberOfBoxes;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getInBookId() {
        return InBookId;
    }

    public void setInBookId(Integer inBookId) {
        InBookId = inBookId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Integer getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(Integer numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }
}
