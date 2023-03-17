package com.reify.customer.service.impl;

import com.reify.common.DTO.*;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.model.CustomerDO;
import com.reify.customer.repo.CustomerRepo;
import com.reify.customer.service.CustomerSearchservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchservice {

    @Autowired
    ApplicationContext context;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<CustomerDTO> getCustomer(CustomerSearchDTO customerSearchDTO) {

       List<CustomerDO> customerDOList = customerRepo.searchCustomerByCriteria(customerSearchDTO);

       List<CustomerDTO> customerDTOList;

       if(!customerDOList.isEmpty()){
           customerDTOList = customerDOList.stream().map( obj ->
           {
               CustomerDTO customerDTO = context.getBean(CustomerDTO.class);
               BeanUtils.copyProperties(obj , customerDTO);

               CountryDTO countryDTO = CountryDTO.builder()
                       .countryName(obj.getCountry().getCountryName())
                       .countryCode(obj.getCountry().getCountryCode())
                       .build();

               CurrencyDTO currencyDTO = CurrencyDTO.builder()
                       .currencyCode(obj.getCurrency().getCurrencyCode())
                       .currencyName(obj.getCurrency().getCurrencyName())
                       .build();

               StatusDTO customerStatusDTO = StatusDTO.builder()
                       .statusCode(obj.getCustomerStatus().getStatusCode())
                       .statusName(obj.getCustomerStatus().getStatusName())
                       .build();

               ReviewStatusDTO reviewStatusDTO = ReviewStatusDTO.builder()
                       .reviewCode(obj.getReviewStatus().getReviewCode())
                       .reviewName(obj.getReviewStatus().getReviewName())
                       .build();

               QualificationStatusDTO customerQualificationStatusDTO = QualificationStatusDTO.builder()
                       .qualificationCode(obj.getCustomerQualificationStatus().getQualificationCode())
                       .qualificationName(obj.getCustomerQualificationStatus().getQualificationName())
                       .build();


               customerDTO.setCustomerStatus(customerStatusDTO);
               customerDTO.setCountry(countryDTO);
               customerDTO.setCurrency(currencyDTO);
               customerDTO.setReviewStatus(reviewStatusDTO);
               customerDTO.setCustomerQualificationStatus(customerQualificationStatusDTO);
               return customerDTO;


           }).collect(Collectors.toList());

           return customerDTOList;
       }
            return null;
    }
}
