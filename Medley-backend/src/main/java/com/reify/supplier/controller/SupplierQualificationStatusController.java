package com.reify.supplier.controller;

import com.reify.supplier.DTO.SupplierQualificationStatusDTO;
import com.reify.supplier.service.SupplierQualificationStatusService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/SupplierQualificationStatus")
public class SupplierQualificationStatusController {

    @Autowired
    SupplierQualificationStatusService supplierQualificationStatusService;

    @GetMapping(value = "/getAllSupplierQualificationStatus")
    public ResponseEntity<?> getAllSupplierQualificationStatus(@RequestHeader("Authorization") String token){

        List<SupplierQualificationStatusDTO> supplierQualificationStatusList = supplierQualificationStatusService.getAllSupplierQualificationStatus();

        JSONArray jsonArray = new JSONArray();

        for (SupplierQualificationStatusDTO supplierQualificationStatusDTO : supplierQualificationStatusList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",supplierQualificationStatusDTO.getSupplierQfCode());
            jsonObject.put("status",supplierQualificationStatusDTO.getSupplierQfName());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());

    }
}
