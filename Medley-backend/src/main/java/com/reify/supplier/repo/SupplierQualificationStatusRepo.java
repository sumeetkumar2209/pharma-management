package com.reify.supplier.repo;

import com.reify.supplier.model.SupplierQualificationStatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierQualificationStatusRepo extends JpaRepository<SupplierQualificationStatusDO,String> {
}
