package com.reify.login.service.impl;

import com.reify.login.DTO.RoleDTO;
import com.reify.login.model.RoleDO;
import com.reify.login.model.UserDO;
import com.reify.login.repo.RoleRepo;
import com.reify.login.service.RoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    Logger log = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    ApplicationContext context;

    @Override
    public List<RoleDTO> getRoles() {

       List<RoleDO> list = roleRepo.findAll();

       List<RoleDTO> response = new ArrayList<>();

        if(!list.isEmpty()){
            for (RoleDO roleDO: list) {

                RoleDTO roleDTO = context.getBean(RoleDTO.class);

                roleDTO.setRoleId(roleDO.getRoleId());
                roleDTO.setRoleName(roleDO.getRoleName());
                response.add(roleDTO);
            }
        }

        return response;
    }
}
