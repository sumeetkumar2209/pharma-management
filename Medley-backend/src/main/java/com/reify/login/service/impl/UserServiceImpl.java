package com.reify.login.service.impl;

import com.reify.login.DTO.UserDTO;
import com.reify.login.constant.UserEnum;
import com.reify.login.model.RoleDO;
import com.reify.login.model.UserDO;
import com.reify.login.repo.UserRepo;
import com.reify.login.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Override
    public void resetPassword(String emailId, String newPassword) {

       UserDO userDO = userRepo.findByEmailId(emailId);
       userDO.setPassword(newPassword);
       userRepo.save(userDO);
    }

    @Override
    public String getUserDetails(String emailId) {

        UserDO userDO = userRepo.findByEmailId(emailId);
        return convertUserDOToJson(userDO);
    }

    private String convertUserDOToJson(UserDO userDO){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put(UserEnum.USERID.getName(), userDO.getUserId());
        jsonObject.put(UserEnum.EMAILID.getName(), userDO.getEmailId());
        jsonObject.put(UserEnum.FIRSTNAME.getName(), userDO.getFirstName());
        jsonObject.put(UserEnum.MIDDLENAME.getName(), userDO.getMiddleName());
        jsonObject.put(UserEnum.LASTNAME.getName(), userDO.getLastName());
        jsonObject.put(UserEnum.CONTACTNO.getName(), userDO.getContactNo());
        jsonObject.put(UserEnum.SUPERVISORID.getName(), userDO.getSupervisorId());
        jsonObject.put(UserEnum.LASTLOGINTIME.getName(), userDO.getLastLoginTime());
        jsonObject.put(UserEnum.ROLEID.getName(), userDO.getRoleDO().getRoleId());
        jsonObject.put(UserEnum.ROLENAME.getName(), userDO.getRoleDO().getRoleName());
        jsonObject.put(UserEnum.MENU_LIST.getName(), userDO.getRoleDO().getMenuList());

       return jsonObject.toString();

    }

}
