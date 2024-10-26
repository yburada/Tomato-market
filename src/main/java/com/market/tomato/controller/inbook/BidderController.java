package com.market.tomato.controller.inbook;

import com.market.tomato.exception.generate.VehicleNotFoundException;
import com.market.tomato.model.bidder.Bidder;
import com.market.tomato.model.save.Buyer;
import com.market.tomato.repository.bidder.BidderRepository;
import com.market.tomato.repository.save.BuyerRepository;
import com.market.tomato.repository.save.VehicleRepository;
import com.market.tomato.service.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/bidder")
public class BidderController {

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private BidderRepository bidderRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private PDFGenerator pdfGenerator;

    @PostMapping("/bid/{lot}/{bId}/{price}")
    public ResponseEntity<String> saveBiddingDetails(@PathVariable("lot") Integer lot,@PathVariable("bId") Integer bId,@PathVariable("price") Integer price){
        Optional<Buyer> byBuyerId = buyerRepository.findByBuyerId(bId);
        return byBuyerId.map(buyer -> saveBuyerWithBidding(lot, buyer, price)).orElse(new ResponseEntity<>("Buyer Not Found Please Add Buyer", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pdf/{vehicleNumber}")
    public ResponseEntity<?> getPdf(@PathVariable("vehicleNumber") String vehicleNumber) {
        return  vehicleRepository.findByVehicleNumber(vehicleNumber).map(vehicle -> {
            byte[] pdfContent = pdfGenerator.bidderPdfGenerator(vehicle);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", vehicleNumber+".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        }).orElseThrow(() -> {
            try {
                throw new VehicleNotFoundException("Vehicle Not found with ID :"+vehicleNumber);
            } catch (VehicleNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public ResponseEntity<String> saveBuyerWithBidding(Integer lot, Buyer buyer, Integer price) {
        Bidder bidder = bidderRepository.save(new Bidder(lot, buyer, price, LocalDateTime.now()));
        return new ResponseEntity<>("Bidder Saved with Id : "+bidder.getBiddingId(), HttpStatus.OK);
    }
}
