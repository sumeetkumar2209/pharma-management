package com.reify.customer.controller;

import com.reify.common.utils.DateConvertorUtils;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.service.CustomerSearchservice;
import com.reify.supplier.DTO.InProgressWorkFlowDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
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

        long customerCount = customerSearchservice.getTotalCustomerCount(customerSearchDTO);

        JSONObject root = new JSONObject();
        root.put("totalCount", customerCount);

        JSONArray jsonArray = new JSONArray();

       if(customerDTOList.isEmpty()){
           return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
       }
        for ( CustomerDTO customerDTO: customerDTOList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("customerId" ,customerDTO.getCustomerId());
            jsonObject.put("workFlowId",customerDTO.getWorkFlowId());
            jsonObject.put("customerStatus",customerDTO.getCustomerStatus());
            jsonObject.put("customerName",customerDTO.getCustomerName());
            jsonObject.put("contactNumber",customerDTO.getContactNumber());
            jsonObject.put("contactName",customerDTO.getContactName());
            jsonObject.put("contactEmail",customerDTO.getContactEmail());
            jsonObject.put("addressLine1",customerDTO.getAddressLine1());
            jsonObject.put("addressLine2",customerDTO.getAddressLine2());
            jsonObject.put("addressLine3",customerDTO.getAddressLine3());
            jsonObject.put("town",customerDTO.getTown());
            jsonObject.put("postalCode",customerDTO.getPostalCode());
            jsonObject.put("country",customerDTO.getCountry());
            jsonObject.put("currency",customerDTO.getCurrency());
            jsonObject.put("customerQualificationStatus",customerDTO.getCustomerQualificationStatus());
            jsonObject.put("validTillDate", new SimpleDateFormat("dd/MM/yyyy").format(customerDTO.getValidTillDate()));

            jsonObject.put("approver",customerDTO.getApprover());
            jsonObject.put("userId",customerDTO.getUserId());
            jsonObject.put("initialAdditionDate", DateConvertorUtils.eprocToDDMMYYYYY(customerDTO.getInitialAdditionDate()));
            jsonObject.put("lastUpdatedBy",customerDTO.getLastUpdatedBy());
            jsonObject.put("comments",customerDTO.getComments());
            jsonObject.put("reviewStatusCode",customerDTO.getReviewStatus());

            jsonArray.put(jsonObject);
        }

        root.put("customers", jsonArray);

        return ResponseEntity.status(HttpStatus.OK).body(root.toString());

    }

    @PostMapping(value = "/getCustomerWorkFlow")
    public ResponseEntity<?> getInProgressWorkFlow(@RequestHeader("Authorization") String token,
                                                   @RequestBody InProgressWorkFlowDTO inProgressWorkFlowDTO){

        List<CustomerDTO> customerDTOList = customerSearchservice.getCustomerBasedOnUser(inProgressWorkFlowDTO);

        long customerCount = customerSearchservice.getCustomerCountBasedOnUser(inProgressWorkFlowDTO);

        JSONObject root = new JSONObject();
        root.put("totalCount", customerCount);

        JSONArray jsonArray = new JSONArray();

        if(CollectionUtils.isEmpty(customerDTOList)){

            return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
        }

        for ( CustomerDTO customerDTO: customerDTOList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("customerId" ,customerDTO.getCustomerId());
            jsonObject.put("workFlowId",customerDTO.getWorkFlowId());
            jsonObject.put("customerStatus",customerDTO.getCustomerStatus());
            jsonObject.put("customerName",customerDTO.getCustomerName());
            jsonObject.put("contactNumber",customerDTO.getContactNumber());
            jsonObject.put("contactName",customerDTO.getContactName());
            jsonObject.put("contactEmail",customerDTO.getContactEmail());
            jsonObject.put("addressLine1",customerDTO.getAddressLine1());
            jsonObject.put("addressLine2",customerDTO.getAddressLine2());
            jsonObject.put("addressLine3",customerDTO.getAddressLine3());
            jsonObject.put("town",customerDTO.getTown());
            jsonObject.put("postalCode",customerDTO.getPostalCode());
            jsonObject.put("country",customerDTO.getCountry());
            jsonObject.put("currency",customerDTO.getCurrency());
            jsonObject.put("customerQualificationStatus",customerDTO.getCustomerQualificationStatus());
            jsonObject.put("validTillDate", new SimpleDateFormat("dd/MM/yyyy").format(customerDTO.getValidTillDate()));

            jsonObject.put("approver",customerDTO.getApprover());
            jsonObject.put("userId",customerDTO.getUserId());
            jsonObject.put("initialAdditionDate", DateConvertorUtils.eprocToDDMMYYYYY(customerDTO.getInitialAdditionDate()));
            jsonObject.put("lastUpdatedBy",customerDTO.getLastUpdatedBy());
            jsonObject.put("comments",customerDTO.getComments());
            jsonObject.put("reviewStatusCode",customerDTO.getReviewStatus());

            jsonArray.put(jsonObject);
        }

        root.put("customers", jsonArray);

        return ResponseEntity.status(HttpStatus.OK).body(root.toString());

    }
}
