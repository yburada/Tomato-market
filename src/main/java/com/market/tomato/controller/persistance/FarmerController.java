package com.market.tomato.controller.persistance;

import com.market.tomato.exception.generate.AllotmentNotFoundException;
import com.market.tomato.exception.generate.VehicleNotFoundException;
import com.market.tomato.model.FarmerBillingDetails;
import com.market.tomato.model.allotment.Allotment;
import com.market.tomato.model.save.Farmer;
import com.market.tomato.repository.allotment.AllotmentRepository;
import com.market.tomato.repository.billing.FarmerBillingRepository;
import com.market.tomato.repository.save.FarmerRepository;
import com.market.tomato.service.PDFGenerator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farmer")
@Validated
public class FarmerController {
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private AllotmentRepository allotmentRepository;
    @Autowired
    private PDFGenerator pdfGenerator;
    @Autowired
    private FarmerBillingRepository farmerBillingRepository;

    @GetMapping("/findAll")
    public ResponseEntity<List<Farmer>> fetchAllFarmerDetails(){
        return new ResponseEntity<>(farmerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchByFarmerId(@NotNull @PathVariable(name = "id") Integer id){
        Optional<Farmer> byFarmerId = farmerRepository.findByFarmerId(id);
        if(byFarmerId.isPresent()){
            return new ResponseEntity<>(byFarmerId, HttpStatus.OK);
        }
        return new ResponseEntity<>("Farmer not found with ID : "+id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/pdf/{farmerId}/{less}")
    public ResponseEntity<?> farmerPdf(@NotNull @PathVariable(name = "farmerId") Integer farmerId, @PathVariable("less") Integer less) {
        Optional<Farmer> farmer = farmerRepository.findByFarmerId(farmerId);
        if(farmer.isPresent()) {
            Optional<Allotment> allotment = allotmentRepository.findByFarmerAndDate(LocalDate.now(), farmer.get());
             return allotment.map(allotment1 -> {
                 FarmerBillingDetails farmerBillingDetails = new FarmerBillingDetails(allotment1, LocalDateTime.now(), less);
                 FarmerBillingDetails byAllotment = farmerBillingRepository.findByAllotment(allotment1, LocalDate.now());
                 FarmerBillingDetails billingDetails = byAllotment == null ? farmerBillingRepository.save(farmerBillingDetails): byAllotment;
                 byte[] pdfContent = pdfGenerator.farmerPdfGenerator(billingDetails, generateLessCount(less, billingDetails.getAllotment().getNumberOfBoxes()));
                 HttpHeaders headers = new HttpHeaders();
                 headers.setContentType(MediaType.APPLICATION_PDF);
                 headers.setContentDispositionFormData("attachment",farmer.get().getName()+"_"+ farmerId+".pdf");
                 headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
                 return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
            }).orElseThrow(() -> {
                 try {
                     throw new AllotmentNotFoundException("Allotment Not found for farmer ID :"+farmerId);
                 } catch (AllotmentNotFoundException e) {
                     throw new RuntimeException(e);
                 }
             });
        }
          return  new ResponseEntity<>("Farmer with Id : "+farmerId+" is not found", HttpStatus.NOT_FOUND);
    }

    private Integer generateLessCount(Integer less, Integer noOfBoxes) {
        if(less == 0) {
            return (noOfBoxes -less)/50;
        }
        return less;
    }

}
