package com.market.tomato.controller.persistance;

import com.market.tomato.model.save.Vehicle;
import com.market.tomato.repository.save.VehicleRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle")
@Validated
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping("/findAll")
    public ResponseEntity<List<Vehicle>> fetchAllVehicleDetails(){
        return new ResponseEntity<>(vehicleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchByVehicleNumber(@NotNull @PathVariable(name = "id") String vNumber){
        Optional<Vehicle> vehicle = vehicleRepository.findByVehicleNumber(vNumber);
        if(vehicle.isPresent()){
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        }
        return new ResponseEntity<>("Vehicle not found with number : "+vNumber, HttpStatus.NOT_FOUND);
    }
}
