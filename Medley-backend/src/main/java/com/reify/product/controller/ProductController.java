package com.reify.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.networknt.schema.ValidationMessage;
import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.constant.JsonSchemaEnum;
import com.reify.common.exception.AlreadyExistException;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.utils.CommonUtils;
import com.reify.common.validation.JsonValidator;
import com.reify.product.DTO.ProductDTO;
import com.reify.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "/product")
    public ResponseEntity addProduct(@RequestHeader("Authorization") String token,
                                     @RequestBody ProductDTO productDTO){

        productDTO = CommonUtils.removeWhiteSpace(productDTO);

        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String workFlowId = null;
        try {
            String json = ow.writeValueAsString(productDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.PRODUCT_SCHEMA.getName());
            if (!validationResult.isEmpty()) {
                List<String> errors =  validationResult.stream().map(obj -> obj.getMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
            }
            workFlowId = productService.addProduct(productDTO);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        } catch (AlreadyExistException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(workFlowId);
    }

    @PutMapping(value = "/product")
    public ResponseEntity<?> modifyProduct(@RequestHeader("Authorization") String token,
                                           @RequestBody ProductDTO productDTO){

        productDTO = CommonUtils.removeWhiteSpace(productDTO);

        //validate json
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String workFlowId = null;

        try {
            String json = ow.writeValueAsString(productDTO);
            Set<ValidationMessage> validationResult = JsonValidator.validateJson(json,
                    JsonSchemaEnum.PRODUCT_SCHEMA.getName());
            if (!validationResult.isEmpty()) {
                List<String> errors =  validationResult.stream().map(obj -> obj.getMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
            }
            workFlowId = productService.modifyProduct(productDTO);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        }
        catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product Id not present");
        } catch (InvalidStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Illegal Review Status ");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in validating json");
        }

        return ResponseEntity.status(HttpStatus.OK).body(workFlowId);
    }
    @PutMapping(value = "/approveRejectProduct")
    public ResponseEntity<?> approveRejectProduct(@RequestHeader("Authorization") String token,
                                           @RequestBody ApproveRejectDTO approveRejectDTO){

        approveRejectDTO = CommonUtils.removeWhiteSpace(approveRejectDTO);
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
        String res = productService.approveRejectProduct(approveRejectDTO);
        if (res != null && approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {
            return ResponseEntity.status(HttpStatus.OK).body("res");
        } else if (res != null && approveRejectDTO.getDecision().equalsIgnoreCase("RE")) {
            return ResponseEntity.status(HttpStatus.OK).body("Product Rejected");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product approval not acted");

    }

}
