package com.reify.purchaseOrder.controller;

import com.reify.common.DTO.InProgressWorkFlowDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderResponseDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderSearchDTO;
import com.reify.purchaseOrder.service.PurchaseOrderSearchService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PurchaseOrderSearchController {

    @Autowired
    PurchaseOrderSearchService purchaseOrderSearchService;

    @PostMapping(value = "/purchaseOrders")
    public ResponseEntity<?> getPurchaseOrders(@RequestHeader("Authorization") String token,
                                               @RequestBody PurchaseOrderSearchDTO purchaseOrderSearchDTO) {


        List<PurchaseOrderResponseDTO> responseDTOList = purchaseOrderSearchService.getPurchaseOrder(purchaseOrderSearchDTO);

        long purchaseOrderCount = purchaseOrderSearchService.getTotalPurchaseOrderCount(purchaseOrderSearchDTO);

        JSONObject root = new JSONObject();
        root.put("totalCount", purchaseOrderCount);
        root.put("response", responseDTOList.toString());


        return ResponseEntity.status(HttpStatus.OK).body(root.toString());

    }

    @PostMapping(value = "/purchaseOrderWorkFlow")
    public ResponseEntity<?> getInProgressWorkFlow(@RequestHeader("Authorization") String token,
                                                   @RequestBody InProgressWorkFlowDTO inProgressWorkFlowDTO) {


        List<PurchaseOrderResponseDTO> purchaseOrderDTOList = purchaseOrderSearchService.getPurchaseOrderBasedOnUser(inProgressWorkFlowDTO);

        long purchaseOrderCount = purchaseOrderSearchService.getPurchaseOrderCountBasedOnUser(inProgressWorkFlowDTO);

        JSONObject root = new JSONObject();
        root.put("totalCount", purchaseOrderCount);
        root.put("response", purchaseOrderDTOList.toString());

        return ResponseEntity.status(HttpStatus.OK).body(root.toString());
    }
}
