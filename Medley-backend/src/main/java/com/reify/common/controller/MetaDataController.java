package com.reify.common.controller;

import com.reify.common.DTO.*;
import com.reify.common.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MetaDataController {

    @Autowired
    ReviewStatusService reviewStatusService;

    @Autowired
    StatusService statusService;

    @Autowired
    QualificationStatusService qualificationStatusService;
    @Autowired
    CountryService countryService;
    @Autowired
    CurrencyService currencyService;

    @Autowired
    ApplicationContext context;

    @GetMapping(value = "/metadata")
    public ResponseEntity<?> getMetadata(@RequestHeader("Authorization") String token){

        List<ReviewStatusDTO> reviewStatusDTOList = reviewStatusService.getAllRoleStatus();
        List<StatusDTO> statusDTOList = statusService.getAllStatus();
        List<QualificationStatusDTO> qualificationStatusDTOList =
                qualificationStatusService.getAllQualificationStatus();
        List<CurrencyDTO> currencyDTOList = currencyService.getCurrency();
        List<CountryDTO> countryDTOList = countryService.getCountry();

        MetadataDTO metadataDTO = MetadataDTO.builder()
                .countryList(countryDTOList)
                .currencyList(currencyDTOList)
                .qualificationStatusList(qualificationStatusDTOList)
                .reviewStatusList(reviewStatusDTOList)
                .statusList(statusDTOList)
                .build();

        JSONObject jsonObject = new JSONObject(metadataDTO);


        return ResponseEntity.status(HttpStatus.OK).body(jsonObject.toString());
    }
}
