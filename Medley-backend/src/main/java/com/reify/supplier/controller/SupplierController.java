package com.reify.supplier.controller;

import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping(value = "/addSupplier")
    public ResponseEntity<?> addSupplier(@RequestHeader("Authorization") String token,
                                         @RequestBody SupplierDTO supplierDTO){

        supplierService.addSupplier(supplierDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Supplier Added");
    }

    @PutMapping(value = "/modifySupplier")
    public ResponseEntity<?> modifySupplier(@RequestHeader("Authorization") String token,@RequestBody SupplierDTO supplierDTO){

        supplierService.modifySupplier(supplierDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("supplier updated");
    }

}
