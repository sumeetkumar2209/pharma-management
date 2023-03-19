package com.reify.supplier.repo;

import com.reify.supplier.model.SupplierAuditDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierAuditRepo extends JpaRepository<SupplierAuditDO, Integer> {
}
