package com.reify.common.controller;

import com.reify.common.DTO.ReviewStatusDTO;
import com.reify.common.service.ReviewStatusService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/reviewStatus")
public class ReviewController {

    @Autowired
    ReviewStatusService reviewStatusService;

    @GetMapping(value = "/getAllReviewStatus")
    public ResponseEntity<?> getAllReviewStatus(@RequestHeader("Authorization") String token){

        List<ReviewStatusDTO> reviewStatusList = reviewStatusService.getAllRoleStatus();

        JSONArray jsonArray = new JSONArray();

        for ( ReviewStatusDTO reviewStatusDTO : reviewStatusList) {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("code",reviewStatusDTO.getReviewCode());
            jsonObject.put("status",reviewStatusDTO.getReviewName());

            jsonArray.put(jsonObject);

        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString());

    }
}
