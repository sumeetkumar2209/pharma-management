package com.reify.supplier.controller;

import com.reify.common.utils.DateConvertorUtils;
import com.reify.supplier.DTO.InProgressWorkFlowDTO;
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

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SupplierSearchController {

    @Autowired
    SupplierSearchService supplierSearchService;

    @PostMapping(value = "/suppliers")
    public ResponseEntity<?> getSupplier(@RequestHeader("Authorization") String token,
                                         @RequestBody SupplierSearchDTO supplierSearchDTO){

       List<SupplierDTO> supplierDTOList = supplierSearchService.getSupplier(supplierSearchDTO);

       long supplierCount = supplierSearchService.getTotalSupplierCount(supplierSearchDTO);

       JSONObject root = new JSONObject();
       root.put("totalCount", supplierCount);

        JSONArray jsonArray = new JSONArray();

        if(CollectionUtils.isEmpty(supplierDTOList)){

            return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
        }

        for ( SupplierDTO supplierDTO : supplierDTOList) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("workFlowId", supplierDTO.getWorkFlowId());
            jsonObject.put("supplierId", supplierDTO.getSupplierId());
            jsonObject.put("supplierStatus", supplierDTO.getSupplierStatus());
            jsonObject.put("companyName", supplierDTO.getCompanyName());
            jsonObject.put("contactName", supplierDTO.getContactName());
            jsonObject.put("contactNumber", supplierDTO.getContactNumber());
            jsonObject.put("contactEmail", supplierDTO.getContactEmail());
            jsonObject.put("addressLine1", supplierDTO.getAddressLine1());
            jsonObject.put("addressLine2", supplierDTO.getAddressLine2());
            jsonObject.put("addressLine3", supplierDTO.getAddressLine3());
            jsonObject.put("town", supplierDTO.getTown());
            jsonObject.put("country", supplierDTO.getCountry());
            jsonObject.put("postalCode", supplierDTO.getPostalCode());
            jsonObject.put("supplierQualificationStatus", supplierDTO.getSupplierQualificationStatus());
            jsonObject.put("validTillDate", new SimpleDateFormat("dd/MM/yyyy").format(supplierDTO.getValidTillDate()));
            jsonObject.put("currency", supplierDTO.getCurrency());
            jsonObject.put("approver", supplierDTO.getApprover());
            jsonObject.put("userId", supplierDTO.getUserId());
            jsonObject.put("initialAdditionDate", DateConvertorUtils.eprocToDDMMYYYYY(supplierDTO.getInitialAdditionDate()));
            jsonObject.put("lastUpdatedBy", supplierDTO.getLastUpdatedBy());
            jsonObject.put("lastUpdatedTimeStamp", DateConvertorUtils.eprocToDDMMYYYYY(supplierDTO.getLastUpdatedTimeStamp()));
            jsonObject.put("reviewStatus", supplierDTO.getReviewStatus());
            jsonObject.put("comments", supplierDTO.getComments());


            jsonArray.put(jsonObject);

        }

        root.put("suppliers", jsonArray);

        return ResponseEntity.status(HttpStatus.OK).body(root.toString());
    }

    @PostMapping(value = "/getInProgressWorkFlow")
    public ResponseEntity<?> getInProgressWorkFlow(@RequestHeader("Authorization") String token,
                                         @RequestBody InProgressWorkFlowDTO inProgressWorkFlowDTO){


        List<SupplierDTO> supplierDTOList = supplierSearchService.getSupplierBasedOnUser(inProgressWorkFlowDTO);

        long supplierCount = supplierSearchService.getSupplierCountBasedOnUser(inProgressWorkFlowDTO);

        JSONObject root = new JSONObject();
        root.put("totalCount", supplierCount);

        JSONArray jsonArray = new JSONArray();

        if(CollectionUtils.isEmpty(supplierDTOList)){

            return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());
        }

        for ( SupplierDTO supplierDTO : supplierDTOList) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("workFlowId", supplierDTO.getWorkFlowId());
            jsonObject.put("supplierId", supplierDTO.getSupplierId());
            jsonObject.put("supplierStatus", supplierDTO.getSupplierStatus());
            jsonObject.put("companyName", supplierDTO.getCompanyName());
            jsonObject.put("contactName", supplierDTO.getContactName());
            jsonObject.put("contactNumber", supplierDTO.getContactNumber());
            jsonObject.put("contactEmail", supplierDTO.getContactEmail());
            jsonObject.put("addressLine1", supplierDTO.getAddressLine1());
            jsonObject.put("addressLine2", supplierDTO.getAddressLine2());
            jsonObject.put("addressLine3", supplierDTO.getAddressLine3());
            jsonObject.put("town", supplierDTO.getTown());
            jsonObject.put("country", supplierDTO.getCountry());
            jsonObject.put("postalCode", supplierDTO.getPostalCode());
            jsonObject.put("supplierQualificationStatus", supplierDTO.getSupplierQualificationStatus());
            jsonObject.put("validTillDate", new SimpleDateFormat("dd/MM/yyyy").format(supplierDTO.getValidTillDate()));
            jsonObject.put("currency", supplierDTO.getCurrency());
            jsonObject.put("approver", supplierDTO.getApprover());
            jsonObject.put("userId", supplierDTO.getUserId());
            jsonObject.put("initialAdditionDate", DateConvertorUtils.eprocToDDMMYYYYY(supplierDTO.getInitialAdditionDate()));
            jsonObject.put("lastUpdatedBy", supplierDTO.getLastUpdatedBy());
            jsonObject.put("lastUpdatedTimeStamp", DateConvertorUtils.eprocToDDMMYYYYY(supplierDTO.getLastUpdatedTimeStamp()));
            jsonObject.put("reviewStatus", supplierDTO.getReviewStatus());
            jsonObject.put("comments", supplierDTO.getComments());
            jsonObject.put("action", supplierDTO.getAction());


            jsonArray.put(jsonObject);

        }

        root.put("suppliers", jsonArray);

        return ResponseEntity.status(HttpStatus.OK).body(root.toString());

    }
}
