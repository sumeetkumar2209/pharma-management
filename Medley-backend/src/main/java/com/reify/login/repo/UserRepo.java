package com.reify.login.repo;


import com.reify.login.model.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDO, String> {

    public UserDO findByEmailId(String emailId);
}