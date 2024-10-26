package com.market.tomato.exception.advices;

import com.market.tomato.exception.generate.AllotmentNotFoundException;
import com.market.tomato.exception.generate.VehicleNotFoundException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvices {

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<String> propertyValueException(PropertyValueException valueException){
        return new ResponseEntity<>("Please check the request. Don't Enter Null Values", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<String> vehicleNotFoundException(VehicleNotFoundException vehicleNotFoundException){
        return new ResponseEntity<>(vehicleNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AllotmentNotFoundException.class)
    public ResponseEntity<String> allotmentNotFoundException(AllotmentNotFoundException allotmentNotFoundException){
        return new ResponseEntity<>(allotmentNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
