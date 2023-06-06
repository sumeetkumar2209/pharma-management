package com.reify.product.repo;

import com.reify.product.model.ProductDO_INT;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductIntRepo extends JpaRepository<ProductDO_INT, String> {

    public static final String findUser_query = "Select prod from ProductDO_INT prod " +
            "JOIN prod.reviewStatus rev " +
            "where prod.userId=:userId and  rev.reviewCode<> 'AP'";

    public static final String findApprover_query = "Select prod from ProductDO_INT prod " +
            "JOIN prod.reviewStatus rev " +
            "where prod.approver=:approver and  rev.reviewCode<> 'AP'";

    public static final String count_query = "select count(*) FROM ProductDO_INT prod " +
            "JOIN prod.reviewStatus rev " +
            "where (prod.userId =:userId OR prod.approver =:userId) and rev.reviewCode <> 'AP'";

    @Query(findUser_query)
    List<ProductDO_INT> findByUserId(@Param("userId") String userId, Pageable pageable);

    @Query(findApprover_query)
    List<ProductDO_INT> findByApprover(@Param("approver") String approver, Pageable pageable);

    @Query(count_query)
    long countByUserId(@Param("userId") String userId);

    ProductDO_INT findByProductId(String productId);
}
