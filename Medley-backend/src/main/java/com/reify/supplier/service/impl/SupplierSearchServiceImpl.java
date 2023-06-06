package com.reify.supplier.service.impl;

import com.reify.common.DTO.*;
import com.reify.common.DTO.InProgressWorkFlowDTO;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.DTO.SupplierSearchDTO;
import com.reify.supplier.model.SupplierDO;
import com.reify.supplier.model.SupplierDO_INT;
import com.reify.supplier.repo.SupplierIntRepo;
import com.reify.supplier.repo.SupplierRepo;
import com.reify.supplier.service.SupplierSearchService;
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
public class SupplierSearchServiceImpl implements SupplierSearchService {

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    ApplicationContext context;

    @Autowired
    SupplierIntRepo supplierIntRepo;


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

               StatusDTO supplierStatusDTO = StatusDTO.builder()
                       .statusCode(obj.getSupplierStatus().getStatusCode())
                       .statusName(obj.getSupplierStatus().getStatusName())
                       .build();

               ReviewStatusDTO reviewStatusDTO = ReviewStatusDTO.builder()
                       .reviewCode(obj.getReviewStatus().getReviewCode())
                       .reviewName(obj.getReviewStatus().getReviewName())
                       .build();

               QualificationStatusDTO supplierQualificationStatusDTO = QualificationStatusDTO.builder()
                       .qualificationCode(obj.getSupplierQualificationStatus().getQualificationCode())
                       .qualificationName(obj.getSupplierQualificationStatus().getQualificationName())
                       .build();


               supplierDTO.setCountry(countryDTO.getCountryCode());
               supplierDTO.setCurrency(currencyDTO.getCurrencyCode());
               supplierDTO.setSupplierStatus(supplierStatusDTO.getStatusCode());
               supplierDTO.setReviewStatus(reviewStatusDTO.getReviewCode());
               supplierDTO.setSupplierQualificationStatus(supplierQualificationStatusDTO.getQualificationCode());
               supplierDTO.setValidTillDate(new Date(obj.getValidTill()));


               return supplierDTO;

           }).collect(Collectors.toList());

           return supplierDTOList;
       }

        return null;
    }

    @Override
    public long getTotalSupplierCount(SupplierSearchDTO supplierSearchDTO) {

        return supplierRepo.countSupplierByFilter(supplierSearchDTO);
    }

    @Override
    public List<SupplierDTO> getSupplierBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        int size = inProgressWorkFlowDTO.getEndIndex() - inProgressWorkFlowDTO.getStartIndex();

        Pageable page = PageRequest.of(inProgressWorkFlowDTO.getStartIndex(), size, Sort.by("workFlowId"));

        List<SupplierDO_INT> createdSupplier = supplierIntRepo.findByUserId(inProgressWorkFlowDTO.getUserId(), page);

        List<SupplierDO_INT> approveSupplier = supplierIntRepo.findByApprover(inProgressWorkFlowDTO.getUserId(), page);

        List<SupplierDTO> combinedList = new ArrayList<>();
        combinedList.addAll(convertDOtoDTO(createdSupplier,"READ"));
        combinedList.addAll(convertDOtoDTO(approveSupplier,"WRITE"));

        return combinedList;
    }

    private List<SupplierDTO> convertDOtoDTO(List<SupplierDO_INT> list, String action) {

        List<SupplierDTO> supplierDTOList = list.stream().map(obj -> {

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

            StatusDTO supplierStatusDTO = StatusDTO.builder()
                    .statusCode(obj.getSupplierStatus().getStatusCode())
                    .statusName(obj.getSupplierStatus().getStatusName())
                    .build();

            ReviewStatusDTO reviewStatusDTO = ReviewStatusDTO.builder()
                    .reviewCode(obj.getReviewStatus().getReviewCode())
                    .reviewName(obj.getReviewStatus().getReviewName())
                    .build();

            QualificationStatusDTO supplierQualificationStatusDTO = QualificationStatusDTO.builder()
                    .qualificationCode(obj.getSupplierQualificationStatus().getQualificationCode())
                    .qualificationName(obj.getSupplierQualificationStatus().getQualificationName())
                    .build();


            supplierDTO.setCountry(countryDTO.getCountryCode());
            supplierDTO.setCurrency(currencyDTO.getCurrencyCode());
            supplierDTO.setSupplierStatus(supplierStatusDTO.getStatusCode());
            supplierDTO.setReviewStatus(reviewStatusDTO.getReviewCode());
            supplierDTO.setSupplierQualificationStatus(supplierQualificationStatusDTO.getQualificationCode());
            supplierDTO.setValidTillDate(new Date(obj.getValidTill()));
            supplierDTO.setAction(action);

            return supplierDTO;

        }).collect(Collectors.toList());

        return supplierDTOList;
    }

    @Override
    public long getSupplierCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        return supplierIntRepo.countByUserId(inProgressWorkFlowDTO.getUserId());
    }
}
