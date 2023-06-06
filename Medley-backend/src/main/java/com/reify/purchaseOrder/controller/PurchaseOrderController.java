package com.reify.purchaseOrder.controller;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;
import com.reify.purchaseOrder.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @PostMapping(value = "/purchaseOrder")
    public ResponseEntity<?> addPurchaseOrder(@RequestHeader("Authorization") String token,
                                             @RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO){

      String workFlowId = purchaseOrderService.createPurchaseOrder(purchaseOrderRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workFlowId +" " +"Created Successfully and awaiting approval");

    }

    @PutMapping(value = "/approveRejectPurchaseOrder")
    public ResponseEntity<?> approveRejectPurchaseOrder(@RequestHeader("Authorization") String token,
                                                        @RequestBody ApproveRejectDTO approveRejectDTO) {

        String res = purchaseOrderService.approveRejectPurchaseOrder(approveRejectDTO);
        System.out.println(res);

        if (res != null && approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {
            return ResponseEntity.status(HttpStatus.OK).body(res +" has been approved");
        } else if (res != null && approveRejectDTO.getDecision().equalsIgnoreCase("RE")) {
            return ResponseEntity.status(HttpStatus.OK).body(res +" has been rejected");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PurchaseOrder approval not acted");
    }

    @PutMapping(value = "/purchaseOrder")
    public ResponseEntity<?> modifyPurchaseOrder(@RequestHeader("Authorization") String token,
                                            @RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO) {

        try {
            purchaseOrderService.modifyPurchaseOrder(purchaseOrderRequestDTO);
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("purchase order id not present");
        } catch (InvalidStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Illegal Review Status ");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Modified Successfully and awaiting approval");

    }
}
