package com.reify.purchaseOrder.service;

import com.reify.common.DTO.InProgressWorkFlowDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderResponseDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderSearchDTO;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.DTO.SupplierSearchDTO;

import java.util.List;

public interface PurchaseOrderSearchService {

    public List<PurchaseOrderResponseDTO> getPurchaseOrder(PurchaseOrderSearchDTO purchaseOrderSearchDTO);

    public long getTotalPurchaseOrderCount(PurchaseOrderSearchDTO purchaseOrderSearchDTO);

    public List<PurchaseOrderResponseDTO> getPurchaseOrderBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);

    public long getPurchaseOrderCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);
}
