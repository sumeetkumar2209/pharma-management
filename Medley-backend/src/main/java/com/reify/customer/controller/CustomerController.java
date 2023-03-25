package com.reify.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.networknt.schema.ValidationMessage;
import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.constant.JsonSchemaEnum;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.utils.CommonUtils;
import com.reify.common.validation.JsonValidator;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/customer")
    public ResponseEntity<?> addCustomer(@RequestHeader("Authorization") String token,
                                         @RequestBody CustomerDTO customerDTO){
        customerDTO = CommonUtils.removeWhiteSpace(customerDTO);

        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(customerDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.CUSTOMER_SCHEMA.getName());
            if (!validationResult.isEmpty()) {
                List<String> errors =  validationResult.stream().map(obj -> obj.getMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        }


        customerService.addCustomer(customerDTO);

        return ResponseEntity.status(HttpStatus.OK).body("customer added");

    }

    @PutMapping(value = "/customer")
    public ResponseEntity<?> modifyCustomer(@RequestHeader("Authorization") String token,
                                            @RequestBody CustomerDTO customerDTO){
        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(customerDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.CUSTOMER_SCHEMA.getName());
            if (!validationResult.isEmpty()) {
                List<String> errors =  validationResult.stream().map(obj -> obj.getMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        }


        try {
            customerService.modifyCustomer(customerDTO);
        } catch (RecordNotFoundException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customerId not present");
        } catch (InvalidStatusException e) {

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Illegal Review Status ");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Customer Details Updated");
    }

    @PutMapping(value = "/approveRejectCustomer")
    public ResponseEntity<?> approveRejectCustomer(@RequestHeader("Authorization") String token,
                                                  @RequestBody ApproveRejectDTO approveRejectDTO){

        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(approveRejectDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.APPROVE_SCHEMA.getName());
            if (!validationResult.isEmpty()) {
                List<String> errors =  validationResult.stream().map(obj -> obj.getMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        }

        boolean res = customerService.approveRejectCustomer(approveRejectDTO);

        if(res){
            return ResponseEntity.status(HttpStatus.OK).body("Customer Approved");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not Approved");
    }
}
