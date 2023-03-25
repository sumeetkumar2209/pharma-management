package com.reify.supplier.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.networknt.schema.ValidationMessage;
import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.constant.JsonSchemaEnum;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.validation.JsonValidator;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping(value = "/supplier")
    public ResponseEntity<?> addSupplier(@RequestHeader("Authorization") String token,
                                         @RequestBody SupplierDTO supplierDTO){

        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(supplierDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.SUPPLIER_SCHEMA.getName());
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

        supplierService.addSupplier(supplierDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Supplier Added");
    }

    @PutMapping(value = "/supplier")
    public ResponseEntity<?> modifySupplier(@RequestHeader("Authorization") String token,
                                            @RequestBody SupplierDTO supplierDTO) {

        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(supplierDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.SUPPLIER_SCHEMA.getName());
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
            supplierService.modifySupplier(supplierDTO);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("supplier id not present");
        } catch (InvalidStatusException e) {

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Illegal Review Status ");
        }

        return ResponseEntity.status(HttpStatus.OK).body("supplier updated");
    }

    @PutMapping(value = "/approveRejectSupplier")
    public ResponseEntity<?> approveRejectSupplier(@RequestHeader("Authorization") String token,
                                                   @RequestBody  ApproveRejectDTO approveRejectDTO){

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

        boolean res = supplierService.approveRejectSupplier(approveRejectDTO);
        if (res && approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {
            return ResponseEntity.status(HttpStatus.OK).body("Supplier approved");
        } else if (res && approveRejectDTO.getDecision().equalsIgnoreCase("RE")) {
            return ResponseEntity.status(HttpStatus.OK).body("Supplier Rejected");
        }

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Supplier approval not acted");
    }

}
