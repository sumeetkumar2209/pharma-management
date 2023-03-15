package com.reify.supplier.repo;

import com.reify.common.model.QualificationStatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierQualificationStatusRepo extends JpaRepository<QualificationStatusDO,String> {
}
