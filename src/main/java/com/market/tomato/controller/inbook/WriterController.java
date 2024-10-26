package com.market.tomato.controller.inbook;

import com.market.tomato.model.allotment.Allotment;
import com.market.tomato.model.save.Farmer;
import com.market.tomato.model.inbook.InBook;
import com.market.tomato.model.save.Vehicle;
import com.market.tomato.repository.allotment.AllotmentRepository;
import com.market.tomato.repository.save.FarmerRepository;
import com.market.tomato.repository.inbook.InBookRepository;
import com.market.tomato.repository.save.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/writer")
public class WriterController {

    @Autowired
    private AllotmentRepository allotmentRepository;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private InBookRepository inBookRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @PostMapping("/allotment/{vehicleNumber}/{farmerId}/{boxes}/{lot}")
    public ResponseEntity<String> saveAllotmentDetails(@PathVariable("vehicleNumber") String vehicleNumber, @PathVariable("farmerId") Integer farmerId, @PathVariable("boxes") Integer boxes, @PathVariable("lot") Integer lot) {
        Optional<Vehicle> byVehicleNumber = vehicleRepository.findByVehicleNumber(vehicleNumber);
        return  byVehicleNumber.map(vehicle -> checkInInBook(vehicle, farmerId, boxes,lot)).orElse(new ResponseEntity<>("Vehicle is not present. Please Enter vehicle details", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> checkInInBook(Vehicle vehicle, Integer farmerId, Integer boxes, Integer lot) {
        Optional<InBook> byVehicle = inBookRepository.findByVehicle(vehicle);
        return byVehicle.map(inBook -> checkFarmerDetailsAndNumberOfBoxes(inBook, farmerId, boxes, vehicle, lot)).orElse(new ResponseEntity<>("Vehicle does not  present in InBook", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> checkFarmerDetailsAndNumberOfBoxes(InBook inBook, Integer farmerId, Integer boxes, Vehicle vehicle, Integer lot) {
        Optional<Farmer> byFarmerId = farmerRepository.findByFarmerId(farmerId);
        return byFarmerId.map(farmer -> validateBoxes(inBook.getNumberOfBoxes(), boxes, farmer, vehicle, lot)).orElse(new ResponseEntity<>("Farmer Details not found with ID : "+farmerId, HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> validateBoxes(Integer vehicleBoxes, Integer actualBoxes, Farmer farmer, Vehicle vehicle, Integer lot){
        System.out.println(LocalDate.now()+"    "+vehicle.getVehicleNumber());
        Integer existingBoxes = allotmentRepository.getSumOfBoxesByVehicleNumberOnToday(LocalDate.now(), vehicle).orElse(0);
        if(existingBoxes+actualBoxes <= vehicleBoxes) {
            Allotment save = allotmentRepository.save(new Allotment(vehicle, farmer, actualBoxes, lot, LocalDateTime.now()));
            return new ResponseEntity<>("Allotment saved with ID : "+save.getId()+" with allotment number :"+save.getLotNumber(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Vehicle Contains boxes : "+vehicleBoxes+" already contains "+existingBoxes+"boxes trying to insert more "+actualBoxes+" boxes", HttpStatus.BAD_REQUEST);
    }
}
