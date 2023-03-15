package com.reify.supplier.controller;

import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.DTO.SupplierSearchDTO;
import com.reify.supplier.service.SupplierSearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/SupplierSearch")
public class SupplierSearchController {

    @Autowired
    SupplierSearchService supplierSearchService;

    @PostMapping(value = "/getSuppliers")
    public ResponseEntity<?> getSupplier(@RequestHeader("Authorization") String token,
                                         @RequestBody SupplierSearchDTO supplierSearchDTO){

       List<SupplierDTO> supplierDTOList = supplierSearchService.getSupplier(supplierSearchDTO);

        JSONArray jsonArray = new JSONArray();

        if(CollectionUtils.isEmpty(supplierDTOList)){

            return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
        }

        for ( SupplierDTO supplierDTO : supplierDTOList) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("supplierId",supplierDTO.getSupplierId());
            jsonObject.put("companyName",supplierDTO.getCompanyName());
            jsonObject.put("contactName",supplierDTO.getContactName());
            jsonObject.put("contactEmail",supplierDTO.getContactEmail());
            jsonObject.put("contactNumber",supplierDTO.getContactNumber());
            jsonObject.put("countryCode",supplierDTO.getCountry().getCountryCode());
            jsonObject.put("countryName",supplierDTO.getCountry().getCountryName());
            jsonObject.put("currencyCode",supplierDTO.getCurrency().getCurrencyCode());
            jsonObject.put("currencyName",supplierDTO.getCurrency().getCurrencyName());
            jsonObject.put("supplierQualificationStatusCode",supplierDTO.getSupplierQualificationStatus().getQualificationCode());
            jsonObject.put("supplierQualificationStatusName",supplierDTO.getSupplierQualificationStatus().getQualificationName());
            jsonObject.put("validTillDate",supplierDTO.getValidTillDate());
            jsonObject.put("reviewStatusCode",supplierDTO.getReviewStatus().getReviewCode());
            jsonObject.put("reviewStatusName",supplierDTO.getReviewStatus().getReviewName());
            jsonObject.put("supplierStatusCode",supplierDTO.getSupplierStatus().getStatusCode());
            jsonObject.put("supplierStatusName",supplierDTO.getSupplierStatus().getStatusName());

            jsonArray.put(jsonObject);

        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
    }
}
