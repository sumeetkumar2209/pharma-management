package com.reify.product.repo;

import com.reify.product.model.ProductAuditDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAuditRepo extends JpaRepository<ProductAuditDO, Integer> {
}
