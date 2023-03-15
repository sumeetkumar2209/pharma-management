package com.reify.customer.repo;

import com.reify.customer.model.CustomerDO_INT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerDO_INT,String> {
}
