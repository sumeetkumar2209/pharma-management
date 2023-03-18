package com.reify.customer.controller;

import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.service.CustomerSearchservice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CustomerSearchController {

    @Autowired
    CustomerSearchservice customerSearchservice;

    @PostMapping(value = "/customers")
    public ResponseEntity<?> getCustomer(@RequestHeader("Authorization") String token,
                                         @RequestBody CustomerSearchDTO customerSearchDTO){

       List<CustomerDTO> customerDTOList = customerSearchservice.getCustomer(customerSearchDTO);

        JSONArray jsonArray = new JSONArray();

       if(customerDTOList.isEmpty()){
           return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
       }
        for ( CustomerDTO customerDTO: customerDTOList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("customerId" ,customerDTO.getCustomerId());
            jsonObject.put("customerName",customerDTO.getCustomerName());
            jsonObject.put("contactNumber",customerDTO.getContactNumber());
            jsonObject.put("contactName",customerDTO.getContactName());
            jsonObject.put("contactEmail",customerDTO.getContactEmail());

            jsonObject.put("countryCode",customerDTO.getCountry().getCountryCode());
            jsonObject.put("countryName",customerDTO.getCountry().getCountryName());
            jsonObject.put("currencyCode",customerDTO.getCurrency().getCurrencyCode());
            jsonObject.put("currencyName",customerDTO.getCurrency().getCurrencyName());

            jsonObject.put("customerQualificationStatusCode",customerDTO.getCustomerQualificationStatus().getQualificationCode());
            jsonObject.put("customerQualificationStatusName",customerDTO.getCustomerQualificationStatus().getQualificationName());
            jsonObject.put("validTillDate",customerDTO.getValidTillDate());
            jsonObject.put("reviewStatusCode",customerDTO.getReviewStatus().getReviewCode());
            jsonObject.put("reviewStatusName",customerDTO.getReviewStatus().getReviewName());
            jsonObject.put("customerStatusCode",customerDTO.getCustomerStatus().getStatusCode());
            jsonObject.put("customerStatusName",customerDTO.getCustomerStatus().getStatusName());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());

    }
}
