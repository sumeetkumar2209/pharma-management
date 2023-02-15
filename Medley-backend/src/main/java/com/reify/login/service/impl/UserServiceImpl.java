package com.reify.login.service.impl;

import com.reify.login.DTO.UserDTO;
import com.reify.login.model.RoleDO;
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

        RoleDO roleDO = context.getBean(RoleDO.class);
        roleDO.setRoleId(userDTO.getRoleId());
        userDO.setRoleDO(roleDO);

        userRepo.save(userDO);
    }

    @Override
    public UserDTO fetchUserDetails(String emailId) {
        UserDO userDO = userRepo.findByEmailId(emailId);
        System.out.println(userDO);
        UserDTO userDTO = context.getBean(UserDTO.class);
        BeanUtils.copyProperties(userDO, userDTO);
        return userDTO;
    }
}
