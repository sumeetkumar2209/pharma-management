package com.reify.supplier.controller;

import com.reify.supplier.DTO.SupplierStatusDTO;
import com.reify.supplier.service.SupplierStatusService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping(value = "/supplierStatus")
public class SupplierStatusController {

    @Autowired
    SupplierStatusService supplierStatusService;

    @GetMapping(value = "/getAllSupplierStatus")
    public ResponseEntity<?> getAllSupplierStatus(@RequestHeader("Authorization") String token){

        List<SupplierStatusDTO> supplierStatusList = supplierStatusService.getAllStatus();

        JSONArray jsonArray = new JSONArray();

        for (SupplierStatusDTO supplierStatusDTO :supplierStatusList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", supplierStatusDTO.getSupplierStatusCode());
            jsonObject.put("supplierStatus",supplierStatusDTO.getSupplierStatusName());

            jsonArray.put(jsonObject);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
    }
}
