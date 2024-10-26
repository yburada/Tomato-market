package com.market.tomato.controller.persistance;

import com.market.tomato.model.save.Buyer;
import com.market.tomato.model.save.Farmer;
import com.market.tomato.model.save.Vehicle;
import com.market.tomato.repository.save.BuyerRepository;
import com.market.tomato.repository.save.FarmerRepository;
import com.market.tomato.repository.save.VehicleRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/save")
@Validated
public class DetailsController {

    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @PostMapping("/farmer")
    public ResponseEntity<String> saveFarmer(@NotNull @RequestBody Farmer farmer){
        Optional<Farmer> save = Optional.of(farmerRepository.save(farmer));
        return save.map(farmer1 -> new ResponseEntity<>("Farmer is saved with ID : "+farmer1.getFarmerId(), HttpStatus.OK))
                .orElse(new ResponseEntity<>("Farmer is not saved with ID : "+farmer.getFarmerId(), HttpStatus.BAD_REQUEST));

    }

    @PostMapping("/buyer")
    public ResponseEntity<String> saveBuyer(@NotNull @RequestBody Buyer buyer){
        Optional<Buyer> save = Optional.of(buyerRepository.save(buyer));
        return save.map(buyer1 -> new ResponseEntity<>("Buyer is saved with ID : "+buyer1.getBuyerId(), HttpStatus.OK))
                .orElse(new ResponseEntity<>("Buyer is not saved with ID : "+buyer.getBuyerId(), HttpStatus.BAD_REQUEST));

    }

    @PostMapping("/vehicle")
    public ResponseEntity<String> saveVehicle(@NotNull @RequestBody Vehicle vehicle){
        Optional<Vehicle> save = Optional.of(vehicleRepository.save(vehicle));
        return save.map(vehicle1 -> new ResponseEntity<>("Vehicle is saved with Number : "+vehicle1.getVehicleNumber(), HttpStatus.OK))
                .orElse(new ResponseEntity<>("Vehicle is not saved with Number : "+vehicle.getVehicleNumber(), HttpStatus.BAD_REQUEST));

    }
}
