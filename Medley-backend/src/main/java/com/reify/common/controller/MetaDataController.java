package com.reify.common.controller;

import com.reify.common.service.QualificationStatusService;
import com.reify.common.service.ReviewStatusService;
import com.reify.common.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MetaDataController {

    @Autowired
    ReviewStatusService reviewStatusService;

    @Autowired
    StatusService supplierStatusService;

    @Autowired
    QualificationStatusService qualificationStatusService;

    @GetMapping
    public ResponseEntity<?> getMetadata(){

        return null;
    }
}
