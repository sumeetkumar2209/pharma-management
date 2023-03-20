package com.reify.common.repo;

import com.reify.common.model.CountryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<CountryDO,String> {
}
