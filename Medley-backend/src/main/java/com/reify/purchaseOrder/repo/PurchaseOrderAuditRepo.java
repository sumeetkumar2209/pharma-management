package com.reify.purchaseOrder.repo;

import com.reify.purchaseOrder.model.PurchaseOrderAuditDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderAuditRepo extends JpaRepository<PurchaseOrderAuditDO, Integer> {
}
