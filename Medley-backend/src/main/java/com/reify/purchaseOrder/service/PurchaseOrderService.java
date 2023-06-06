package com.reify.purchaseOrder.service;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;

public interface PurchaseOrderService {

 public String createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

 public String approveRejectPurchaseOrder(ApproveRejectDTO approveRejectDTO);

 public void modifyPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws RecordNotFoundException, InvalidStatusException;

}
