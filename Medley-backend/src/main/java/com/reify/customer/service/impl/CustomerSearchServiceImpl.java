package com.reify.customer.service.impl;

import com.reify.common.DTO.*;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.model.CustomerDO;
import com.reify.customer.model.CustomerDO_INT;
import com.reify.customer.repo.CustomerIntRepo;
import com.reify.customer.repo.CustomerRepo;
import com.reify.customer.service.CustomerSearchservice;
import com.reify.supplier.DTO.InProgressWorkFlowDTO;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.SupplierDO_INT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchservice {

    @Autowired
    ApplicationContext context;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerIntRepo customerIntRepo;

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


               customerDTO.setCustomerStatus(customerStatusDTO.getStatusCode());
               customerDTO.setCountry(countryDTO.getCountryCode());
               customerDTO.setCurrency(currencyDTO.getCurrencyCode());
               customerDTO.setReviewStatus(reviewStatusDTO.getReviewCode());
               customerDTO.setCustomerQualificationStatus(customerQualificationStatusDTO.getQualificationCode());
               customerDTO.setValidTillDate(new Date(obj.getValidTill()));
               return customerDTO;


           }).collect(Collectors.toList());

           return customerDTOList;
       }
            return null;
    }

    @Override
    public long getTotalCustomerCount(CustomerSearchDTO customerSearchDTO) {
        return customerRepo.countCustomerByFilter(customerSearchDTO);
    }

    @Override
    public List<CustomerDTO> getCustomerBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        int size = inProgressWorkFlowDTO.getEndIndex() - inProgressWorkFlowDTO.getStartIndex();

        Pageable page = PageRequest.of(inProgressWorkFlowDTO.getStartIndex(), size, Sort.by("workFlowId"));

        List<CustomerDO_INT> createdCustomer = customerIntRepo.findByUserId(inProgressWorkFlowDTO.getUserId(), page);

        List<CustomerDO_INT> approveCustomer = customerIntRepo.findByApprover(inProgressWorkFlowDTO.getUserId(), page);

        List<CustomerDTO> combinedList = new ArrayList<>();
        combinedList.addAll(convertDOtoDTO(createdCustomer,"READ"));
        combinedList.addAll(convertDOtoDTO(approveCustomer,"WRITE"));

        return combinedList;
    }

    private List<CustomerDTO> convertDOtoDTO(List<CustomerDO_INT> list, String action) {

        List<CustomerDTO> customerDTOList = list.stream().map(obj -> {

            CustomerDTO customerDTO = context.getBean(CustomerDTO.class);
            BeanUtils.copyProperties(obj, customerDTO);

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


            customerDTO.setCountry(countryDTO.getCountryCode());
            customerDTO.setCurrency(currencyDTO.getCurrencyCode());
            customerDTO.setCustomerStatus(customerStatusDTO.getStatusCode());
            customerDTO.setReviewStatus(reviewStatusDTO.getReviewCode());
            customerDTO.setCustomerQualificationStatus(customerQualificationStatusDTO.getQualificationCode());
            customerDTO.setValidTillDate(new Date(obj.getValidTill()));
            customerDTO.setAction(action);

            return customerDTO;

        }).collect(Collectors.toList());

        return customerDTOList;
    }

    @Override
    public long getCustomerCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        return customerIntRepo.countByUserId(inProgressWorkFlowDTO.getUserId());
    }
}
