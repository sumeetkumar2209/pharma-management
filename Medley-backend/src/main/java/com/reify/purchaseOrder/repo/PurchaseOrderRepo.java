package com.reify.purchaseOrder.repo;

import com.reify.purchaseOrder.helper.PurchaseOrderSearch;
import com.reify.purchaseOrder.model.PurchaseOrderDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrderDO, String>, PurchaseOrderSearch {
}
