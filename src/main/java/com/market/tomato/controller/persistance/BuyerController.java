package com.market.tomato.controller.persistance;

import com.market.tomato.model.save.Buyer;
import com.market.tomato.repository.save.BuyerRepository;
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
@RequestMapping("/buyer")
@Validated
public class BuyerController {
    @Autowired
    private BuyerRepository buyerRepository;

    @GetMapping("/findAll")
    public ResponseEntity<List<Buyer>> fetchAllBuyerDetails(){
        return new ResponseEntity<>(buyerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchByBuyerId(@NotNull @PathVariable(name = "id") Integer id){
        Optional<Buyer> byBuyerId = buyerRepository.findByBuyerId(id);
        if(byBuyerId.isPresent()){
            return new ResponseEntity<>(byBuyerId, HttpStatus.OK);
        }
        return new ResponseEntity<>("Buyer not found with ID : "+id, HttpStatus.NOT_FOUND);
    }
}
