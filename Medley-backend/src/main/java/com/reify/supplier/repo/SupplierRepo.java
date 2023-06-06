package com.reify.supplier.repo;

import com.reify.supplier.helper.SearchSupplier;
import com.reify.supplier.model.SupplierDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepo extends JpaRepository<SupplierDO,String>, SearchSupplier {


}
