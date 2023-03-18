package com.reify.supplier.controller;

import com.reify.common.exception.RecordNotFoundException;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping(value = "/supplier")
    public ResponseEntity<?> addSupplier(@RequestHeader("Authorization") String token,
                                         @RequestBody SupplierDTO supplierDTO){

        supplierService.addSupplier(supplierDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Supplier Added");
    }

    @PutMapping(value = "/supplier")
    public ResponseEntity<?> modifySupplier(@RequestHeader("Authorization") String token,
                                            @RequestBody SupplierDTO supplierDTO) {

        try {
            supplierService.modifySupplier(supplierDTO);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("supplier id not present");
        }

        return ResponseEntity.status(HttpStatus.OK).body("supplier updated");
    }

    @PostMapping(value = "/approveRejectSupplier")
    public ResponseEntity<?> approveRejectSupplier(@RequestHeader("Authorization") String token,
                                             @RequestParam("supplierId") String supplierId,
                                                   @RequestParam String decision){

        boolean res = supplierService.approveRejectSupplier(supplierId, decision);
        if (res) {
            return ResponseEntity.status(HttpStatus.OK).body("Supplier approved");
        }

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supplier not approved");
    }

}
