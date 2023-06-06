package com.reify.customer.repo;

import com.reify.customer.helper.CustomerSearch;
import com.reify.customer.model.CustomerDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerDO,String>, CustomerSearch {
}
