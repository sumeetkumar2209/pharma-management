package com.reify.supplier.repo;

import com.reify.supplier.model.SupplierDO_INT;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierIntRepo extends JpaRepository<SupplierDO_INT,String> {


    List<SupplierDO_INT> findByUserId(String userId, Pageable pageable);

    List<SupplierDO_INT> findByApprover(String approver, Pageable pageable);

    @Query("select count(*) FROM SupplierDO_INT where userId =:userId OR approver =:userId")
    long countByUserId(@Param("userId") String userId);
}
