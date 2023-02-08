package com.reify.login.controller;

import com.reify.login.DTO.UserDTO;
import com.reify.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/registerUser")
    public ResponseEntity<?> registerUser (@RequestHeader("createdBy") String createdBy,
                                           @RequestHeader("Authorization") String token,@RequestBody UserDTO userDTO){

        userDTO.setCreationTimestamp(Timestamp.from(Instant.now()));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCreatedBy(createdBy);
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

}
