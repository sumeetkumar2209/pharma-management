package com.reify.login.service.impl;

import com.reify.login.DTO.UserDTO;
import com.reify.login.model.UserDO;
import com.reify.login.repo.UserRepo;
import com.reify.login.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ApplicationContext context;

    @Override
    public void registerUser(UserDTO userDTO) {

        UserDO userDO = context.getBean(UserDO.class);
        BeanUtils.copyProperties(userDTO, userDO);

        userRepo.save(userDO);

    }
}
