package com.reify.common.repo;

import com.reify.common.model.CurrencyDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyDO,String> {
}
