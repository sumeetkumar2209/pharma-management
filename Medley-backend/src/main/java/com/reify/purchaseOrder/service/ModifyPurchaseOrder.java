package com.reify.purchaseOrder.service;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;

public interface ModifyPurchaseOrder {

    public void modifyPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws RecordNotFoundException, InvalidStatusException;
}
