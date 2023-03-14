package com.reify.supplier.service.impl;

import com.reify.supplier.DTO.*;
import com.reify.supplier.model.SupplierDO;
import com.reify.supplier.repo.SupplierRepo;
import com.reify.supplier.service.SupplierSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierSearchServiceImpl implements SupplierSearchService {

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    ApplicationContext context;


    @Override
    public List<SupplierDTO> getSupplier(SupplierSearchDTO supplierSearchDTO) {

       List<SupplierDO> supplierDOList= supplierRepo.searchSupplierByFilter(supplierSearchDTO);

       List<SupplierDTO> supplierDTOList;

       if(!supplierDOList.isEmpty()){

           supplierDTOList = supplierDOList.stream().map( obj -> {

               SupplierDTO supplierDTO = context.getBean(SupplierDTO.class);
               BeanUtils.copyProperties(obj, supplierDTO);

               CountryDTO countryDTO = CountryDTO.builder()
                       .countryName(obj.getCountry().getCountryName())
                       .countryCode(obj.getCountry().getCountryCode())
                       .build();

               CurrencyDTO currencyDTO = CurrencyDTO.builder()
                       .currencyCode(obj.getCurrency().getCurrencyCode())
                       .currencyName(obj.getCurrency().getCurrencyName())
                       .build();

               SupplierStatusDTO supplierStatusDTO = SupplierStatusDTO.builder()
                       .supplierStatusCode(obj.getSupplierStatus().getSupplierStatusCode())
                       .supplierStatusName(obj.getSupplierStatus().getSupplierStatusName())
                       .build();

               ReviewStatusDTO reviewStatusDTO = ReviewStatusDTO.builder()
                       .reviewCode(obj.getReviewStatus().getReviewCode())
                       .reviewName(obj.getReviewStatus().getReviewName())
                       .build();

               SupplierQualificationStatusDTO supplierQualificationStatusDTO = context.getBean(SupplierQualificationStatusDTO.class);

               supplierDTO.setCountry(countryDTO);
               supplierDTO.setCurrency(currencyDTO);
               supplierDTO.setSupplierStatus(supplierStatusDTO);
               supplierDTO.setReviewStatus(reviewStatusDTO);
               supplierDTO.setSupplierQualificationStatus(supplierQualificationStatusDTO);

               return supplierDTO;

           }).collect(Collectors.toList());

           return supplierDTOList;
       }

        return null;
    }
}
