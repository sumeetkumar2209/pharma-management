package com.reify.supplier.repo;

import com.reify.supplier.model.SupplierStatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierStatusRepo extends JpaRepository<SupplierStatusDO, String> {

}
