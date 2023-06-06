package com.reify.login.controller;

import com.reify.login.DTO.RoleDTO;
import com.reify.login.service.RoleService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RoleController {

    Logger log = Logger.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/getRoles")
    public ResponseEntity<?> getRoles(@RequestHeader("Authorization") String token){

        List<RoleDTO> rolesList = roleService.getRoles();

        return ResponseEntity.status(HttpStatus.OK).body(rolesList);
    }
}
