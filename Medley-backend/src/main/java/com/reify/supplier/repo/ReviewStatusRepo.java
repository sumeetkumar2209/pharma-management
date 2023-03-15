package com.reify.supplier.repo;

import com.reify.common.model.ReviewStatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewStatusRepo extends JpaRepository<ReviewStatusDO,String> {
}
