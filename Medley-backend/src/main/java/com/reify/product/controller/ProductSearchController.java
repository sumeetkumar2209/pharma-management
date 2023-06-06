package com.reify.product.controller;

import com.reify.common.DTO.InProgressWorkFlowDTO;
import com.reify.common.utils.DateConvertorUtils;
import com.reify.product.DTO.ProductDTO;
import com.reify.product.DTO.ProductSearchDTO;
import com.reify.product.service.ProductSearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping(value = "/api")
    public class ProductSearchController {

        @Autowired
        ProductSearchService productSearchService;

        @PostMapping(value = "/products")
        public ResponseEntity<?> getProduct(@RequestHeader("Authorization") String token,
                                             @RequestBody ProductSearchDTO productSearchDTO){

            List<ProductDTO> productDTOList = productSearchService.getProduct(productSearchDTO);

            long productCount = productSearchService.getTotalProductCount(productSearchDTO);

            JSONObject root = new JSONObject();
            root.put("totalCount", productCount);

            JSONArray jsonArray = new JSONArray();

            if(CollectionUtils.isEmpty(productDTOList)){

                return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
            }

            for ( ProductDTO productDTO : productDTOList) {

                JSONObject jsonObject = new JSONObject();



                jsonObject.put("workFlowId", productDTO.getWorkFlowId());
                jsonObject.put("productId", productDTO.getProductId());
                jsonObject.put("productName", productDTO.getProductName());
                jsonObject.put("licenceNumber", productDTO.getLicenceNumber());
                jsonObject.put("productDosageForm", productDTO.getProductDosageForm());
                jsonObject.put("productManufacturer", productDTO.getProductManufacturer());
                jsonObject.put("packType", productDTO.getPackType());
                jsonObject.put("packSize", productDTO.getPackSize());
                jsonObject.put("pricePerPack", productDTO.getPricePerPack());
                jsonObject.put("strength", productDTO.getStrength());
                jsonObject.put("productManufactureCountry", productDTO.getProductManufactureCountry());
                jsonObject.put("productApprovalStatus", productDTO.getProductApprovalStatus());
                jsonObject.put("currency", productDTO.getCurrency());
                jsonObject.put("approver", productDTO.getApprover());
                jsonObject.put("userId", productDTO.getUserId());
                jsonObject.put("initialAdditionDate", DateConvertorUtils.eprocToDDMMYYYYY(productDTO.getInitialAdditionDate()));
                jsonObject.put("lastUpdatedBy", productDTO.getLastUpdatedBy());
                jsonObject.put("lastUpdatedTimeStamp", DateConvertorUtils.eprocToDDMMYYYYY(productDTO.getLastUpdatedTimestamp()));
                jsonObject.put("reviewStatus", productDTO.getReviewStatus());
                jsonObject.put("comments", productDTO.getComments());
                jsonObject.put("productPhoto", productDTO.getMultipartFile());
                jsonObject.put("taxationType", productDTO.getTaxationType());
                jsonObject.put("taxPercent", productDTO.getTaxPercent());
                jsonObject.put("productApprovingAuthority", productDTO.getProductApprovingAuthority());



                jsonArray.put(jsonObject);

            }

            root.put("products", jsonArray);

            return ResponseEntity.status(HttpStatus.OK).body(root.toString());
        }

        @PostMapping(value = "/getProductWorkFlow")
        public ResponseEntity<?> getInProgressWorkFlow(@RequestHeader("Authorization") String token,
                                                       @RequestBody InProgressWorkFlowDTO inProgressWorkFlowDTO){


            List<ProductDTO> productDTOList = productSearchService.getProductBasedOnUser(inProgressWorkFlowDTO);

            long productCount = productSearchService.getProductCountBasedOnUser(inProgressWorkFlowDTO);

            JSONObject root = new JSONObject();
            root.put("totalCount", productCount);

            JSONArray jsonArray = new JSONArray();

            if(CollectionUtils.isEmpty(productDTOList)){

                return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
            }

            for ( ProductDTO productDTO : productDTOList) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("workFlowId", productDTO.getWorkFlowId());
                jsonObject.put("productId", productDTO.getProductId());
                jsonObject.put("productName", productDTO.getProductName());
                jsonObject.put("licenceNumber", productDTO.getLicenceNumber());
                jsonObject.put("productDosageForm", productDTO.getProductDosageForm());
                jsonObject.put("productManufacturer", productDTO.getProductManufacturer());
                jsonObject.put("packType", productDTO.getPackType());
                jsonObject.put("packSize", productDTO.getPackSize());
                jsonObject.put("pricePerPack", productDTO.getPricePerPack());
                jsonObject.put("strength", productDTO.getStrength());
                jsonObject.put("productManufactureCountry", productDTO.getProductManufactureCountry());
                jsonObject.put("productApprovalStatus", productDTO.getProductApprovalStatus());
                jsonObject.put("currency", productDTO.getCurrency());
                jsonObject.put("approver", productDTO.getApprover());
                jsonObject.put("userId", productDTO.getUserId());
                jsonObject.put("initialAdditionDate", DateConvertorUtils.eprocToDDMMYYYYY(productDTO.getInitialAdditionDate()));
                jsonObject.put("lastUpdatedBy", productDTO.getLastUpdatedBy());
                jsonObject.put("lastUpdatedTimeStamp", DateConvertorUtils.eprocToDDMMYYYYY(productDTO.getLastUpdatedTimestamp()));
                jsonObject.put("reviewStatus", productDTO.getReviewStatus());
                jsonObject.put("comments", productDTO.getComments());
                jsonObject.put("productPhoto", productDTO.getMultipartFile());
                jsonObject.put("taxationType", productDTO.getTaxationType());
                jsonObject.put("taxPercent", productDTO.getTaxPercent());
                jsonObject.put("productApprovingAuthority", productDTO.getProductApprovingAuthority());
                jsonObject.put("action", productDTO.getAction());


                jsonArray.put(jsonObject);

            }

            root.put("products", jsonArray);

            return ResponseEntity.status(HttpStatus.OK).body(root.toString());

        }
        @GetMapping (value = "/allProducts")
        public ResponseEntity<?> getAllProduct(@RequestHeader("Authorization") String token){

           List<String> productIdList = productSearchService.getAllProduct();

           return ResponseEntity.status(HttpStatus.OK).body(productIdList);


        }
        @GetMapping(value = "/getProductById")
        public ResponseEntity<?> getProductById(@RequestHeader("Authorization") String token,
                                                @RequestParam ("productId") String productId){

            ProductDTO productDTO = productSearchService.getProductById(productId);

            return ResponseEntity.status(HttpStatus.OK).body(productDTO);

        }
    }


