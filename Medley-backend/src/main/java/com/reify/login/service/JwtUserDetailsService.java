package com.reify.login.service;

import com.reify.login.model.UserDO;
import com.reify.login.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {

       UserDO userDO =  userRepo.findByEmailId(emailId);

       if (userDO != null) {
           return new User(userDO.getEmailId(), userDO.getPassword(),
                   new ArrayList<>());
       } else {
           throw new UsernameNotFoundException("User not found with email Id: " + emailId);
       }
    }
}