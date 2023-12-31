package com.reify.login.service;

import com.reify.login.DTO.UserDTO;

public interface UserService {

    public void registerUser(UserDTO userDTO);

    public UserDTO fetchUserDetails (String emailId);

    public void resetPassword(String emailId, String newPassword);

    public String getUserDetails(String emailId);
}
