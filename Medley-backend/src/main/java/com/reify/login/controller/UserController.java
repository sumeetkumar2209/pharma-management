package com.reify.login.controller;

import com.reify.login.DTO.UserDTO;
import com.reify.login.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationController authenticationController;

    @PostMapping(value = "/registerUser")
    public ResponseEntity<?> registerUser (@RequestHeader("createdBy") String createdBy,
                                           @RequestHeader("Authorization") String token,@RequestBody UserDTO userDTO){

        userDTO.setCreationTimestamp(Timestamp.from(Instant.now()));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setCreatedBy(createdBy);
        userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

    @GetMapping(value = "/fetchUserDetails")
    public ResponseEntity<?> fetchUserDetails (@RequestHeader("Authorization") String token,
                                               @RequestParam("emailId") String emailId) {

        UserDTO userDTO = userService.fetchUserDetails(emailId);

        System.out.println(userDTO.toString());

        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping(value = "/resetPassword")
    public ResponseEntity<?> resetPassword (@RequestHeader("Authorization") String token,
                                           @RequestHeader("NewCredentials") String cred) {

        String base64Credentials = cred.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 3);

        if (values.length != 3) {
            log.error("Username, old password and new password not provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and old password and new password is mandatory");
        }

        try {
            authenticationController.authenticate(values[0], values[1]);
            userService.resetPassword(values[0] ,passwordEncoder.encode(values[2]));

        } catch (Exception e) {
            log.error("Authentication failed ::" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username and password");
        }


        return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully");

    }

    @GetMapping(value = "/getUserDetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token,
                                            @RequestParam("emailId") String emailId){

        String response = null;

        try {
            response = userService.getUserDetails(emailId);
        } catch (Exception e) {
            log.error("error in fetching user details" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error in fetching user details");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
