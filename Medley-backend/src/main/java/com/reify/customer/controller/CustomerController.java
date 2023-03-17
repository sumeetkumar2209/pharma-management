package com.reify.customer.controller;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestHeader("Authorization") String token,
                                         @RequestBody CustomerDTO customerDTO){

        customerService.addCustomer(customerDTO);

        return ResponseEntity.status(HttpStatus.OK).body("customer added");

    }

    @PutMapping(value = "/modifyCustomer")
    public ResponseEntity<?> modifyCustomer(@RequestHeader("Authorization") String token,
                                            @RequestBody CustomerDTO customerDTO){
        try {
            customerService.modifyCustomer(customerDTO);
        } catch (RecordNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customerId not present");
        } catch (InvalidStatusException e) {

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Illegal Review Status ");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Customer Details Updated");
    }

    @PostMapping(value = "/approveRejectCustomer")
    public ResponseEntity<?> approveRejectCustomer(@RequestHeader("Authorization") String token,
                                                   @RequestParam("customerId") String customerId,
                                                   @RequestParam String decision){

        boolean res = customerService.approveRejectCustomer(customerId, decision);

        if(res){
            if(decision.equalsIgnoreCase("AP") ||
                    decision.equalsIgnoreCase("APPROVE")){

                return ResponseEntity.status(HttpStatus.OK).body("Customer Approved");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body("Customer Rejected");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not Approved");
    }
}
