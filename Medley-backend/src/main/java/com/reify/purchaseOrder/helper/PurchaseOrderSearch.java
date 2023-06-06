package com.reify.purchaseOrder.helper;

import com.reify.purchaseOrder.DTO.PurchaseOrderSearchDTO;
import com.reify.purchaseOrder.model.PurchaseOrderDO;

import java.util.List;

public interface PurchaseOrderSearch {

    public List<PurchaseOrderDO> searchPurchaseOrderByFilter(PurchaseOrderSearchDTO purchaseOrderSearchDTO);

    public long countPurchaseOrderByFilter(PurchaseOrderSearchDTO purchaseOrderSearchDTO);
}
