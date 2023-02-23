package com.reify.login.controller;

import com.reify.login.service.JwtUserDetailsService;
import com.reify.login.util.JwtTokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

    Logger log = Logger.getLogger(AuthenticationController.class);


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @ApiOperation(value = "authenticate user credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication Successful"),
            @ApiResponse(code = 401, message = "Authentication failed")
    })
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken (@RequestHeader("Credentials") String cred) {

        String base64Credentials = cred.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 2);

        if (values.length != 2) {
            log.error("Username and password not provided");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password is mandatory");
        }

        try {
            authenticate(values[0],values[1]);
        } catch (Exception e) {
            log.error("Authentication failed ::" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username and password");
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(values[0]);
        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}