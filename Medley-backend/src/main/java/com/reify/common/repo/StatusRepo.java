package com.reify.common.repo;

import com.reify.common.model.StatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<StatusDO, String> {

}
