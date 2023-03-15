package com.reify.customer.controller;

import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    public ResponseEntity<?> addCustomer(@RequestHeader("Authorization") String token,
                                         @RequestBody CustomerDTO customerDTO){

        customerService.addCustomer(customerDTO);

        return ResponseEntity.status(HttpStatus.OK).body("customer added");

    }

}
