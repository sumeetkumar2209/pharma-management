package com.reify.common.repo;

import com.reify.common.model.QualificationStatusDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationStatusRepo extends JpaRepository<QualificationStatusDO,String> {
}
