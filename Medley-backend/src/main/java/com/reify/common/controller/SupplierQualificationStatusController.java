package com.reify.common.controller;

import com.reify.common.DTO.QualificationStatusDTO;
import com.reify.common.service.QualificationStatusService;
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
    QualificationStatusService supplierQualificationStatusService;

    @GetMapping(value = "/getAllSupplierQualificationStatus")
    public ResponseEntity<?> getAllSupplierQualificationStatus(@RequestHeader("Authorization") String token){

        List<QualificationStatusDTO> supplierQualificationStatusList = supplierQualificationStatusService.getAllSupplierQualificationStatus();

        JSONArray jsonArray = new JSONArray();

        for (QualificationStatusDTO supplierQualificationStatusDTO : supplierQualificationStatusList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",supplierQualificationStatusDTO.getQualificationCode());
            jsonObject.put("status",supplierQualificationStatusDTO.getQualificationName());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());

    }
}
