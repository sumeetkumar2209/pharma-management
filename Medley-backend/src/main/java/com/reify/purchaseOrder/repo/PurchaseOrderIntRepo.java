package com.reify.purchaseOrder.repo;

import com.reify.purchaseOrder.model.PurchaseOrderDO;
import com.reify.purchaseOrder.model.PurchaseOrderDO_INT;
import com.reify.supplier.model.SupplierDO_INT;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderIntRepo extends JpaRepository<PurchaseOrderDO_INT, String> {

     public static final String findUser_query = "Select cust from PurchaseOrderDO_INT cust " +
             "JOIN cust.reviewStatus rev " +
             "where cust.userId=:userId and  rev.reviewCode<> 'AP'";

     public static final String findApprover_query = "Select cust from PurchaseOrderDO_INT cust " +
             "JOIN cust.reviewStatus rev " +
             "where cust.approver=:approver and  rev.reviewCode<> 'AP'";

     public static final String count_query = "select count(*) FROM PurchaseOrderDO_INT cust " +
             "JOIN cust.reviewStatus rev " +
             "where (cust.userId =:userId OR cust.approver =:userId) and rev.reviewCode <> 'AP'";

     @Query(findUser_query)
     List<PurchaseOrderDO_INT> findByUserId(@Param("userId") String userId, Pageable pageable);

     @Query(findApprover_query)
     List<PurchaseOrderDO_INT> findByApprover(@Param("approver") String approver, Pageable pageable);

     @Query(count_query)
     long countByUserId(@Param("userId") String userId);

     PurchaseOrderDO_INT findByPurchaseOrderId(String purchaseOrderId);

}
