package com.market.tomato.controller.inbook;

import com.market.tomato.model.inbook.InBook;
import com.market.tomato.repository.inbook.InBookRepository;
import com.market.tomato.repository.save.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/in-book")
public class InBookController {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private InBookRepository inBookRepository;

    @PostMapping("/book/{vehicleNumber}/{boxes}")
    public ResponseEntity<String> saveInBookDetails(@PathVariable("vehicleNumber") String vehicleNumber, @PathVariable("boxes") Integer boxes) {

        return vehicleRepository.findByVehicleNumber(vehicleNumber)
                .map(vehicle -> {
                    InBook inBook = new InBook(vehicle, LocalDateTime.now(), boxes);
                    return inBookRepository.save(inBook);
                })
                .map(inBook1 -> new ResponseEntity<>("In Book Details saved with ID : "+inBook1.getInBookId(), HttpStatus.OK))
                .orElse(new ResponseEntity<>("Vehicle Details are not present. Please register the Vehicle", HttpStatus.NOT_FOUND));
    }
}
