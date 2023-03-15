package com.reify.supplier.repo;

import com.reify.common.model.StatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierStatusRepo extends JpaRepository<StatusDO, String> {

}
