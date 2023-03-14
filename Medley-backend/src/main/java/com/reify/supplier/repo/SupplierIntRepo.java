package com.reify.supplier.repo;

import com.reify.supplier.model.SupplierDO_INT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierIntRepo extends JpaRepository<SupplierDO_INT,String> {
}
