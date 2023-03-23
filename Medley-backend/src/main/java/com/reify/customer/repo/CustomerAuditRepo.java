package com.reify.customer.repo;

import com.reify.customer.model.CustomerAuditDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAuditRepo extends JpaRepository<CustomerAuditDO,String> {
}
