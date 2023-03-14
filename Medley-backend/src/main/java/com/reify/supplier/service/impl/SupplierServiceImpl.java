package com.reify.supplier.service.impl;

import com.reify.common.exception.RecordNotFoundException;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.*;
import com.reify.supplier.repo.SupplierIntRepo;
import com.reify.supplier.repo.SupplierRepo;
import com.reify.supplier.service.SupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    ApplicationContext context;

    @Autowired
    SupplierIntRepo supplierIntRepo;

    @Autowired
    SupplierRepo supplierRepo;

    @Override
    public void addSupplier(SupplierDTO supplierDTO) {

        SupplierDO_INT SupplierDO_INT = context.getBean(SupplierDO_INT.class);
        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        SupplierStatusDO supplierStatusDO = context.getBean(SupplierStatusDO.class);
        SupplierQualificationStatusDO supplierQualificationStatusDO = context.getBean(SupplierQualificationStatusDO.class);
        CountryDO countryDO = context.getBean(CountryDO.class);
        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);

        BeanUtils.copyProperties(supplierDTO,SupplierDO_INT);
        SupplierDO_INT.setSupplierId(supplierDTO.getSupplierId());
        SupplierDO_INT.setInitialAdditionDate(System.currentTimeMillis()/1000);
        SupplierDO_INT.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        SupplierDO_INT.setValidTill(supplierDTO.getValidTillDate().getTime());

        if("DRAFT".equalsIgnoreCase(supplierDTO.getOption())) {
            reviewStatusDO.setReviewCode("DR");

        } else {
            reviewStatusDO.setReviewCode("PE");
        }

        SupplierDO_INT.setReviewStatus(reviewStatusDO);

        supplierStatusDO.setSupplierStatusCode(supplierDTO.getSupplierStatus().getSupplierStatusCode());
        SupplierDO_INT.setSupplierStatus(supplierStatusDO);

        supplierQualificationStatusDO.setSupplierQfCode(supplierDTO.getSupplierQualificationStatus().getSupplierQfCode());
        SupplierDO_INT.setSupplierQualificationStatus(supplierQualificationStatusDO);

        countryDO.setCountryCode(supplierDTO.getCountry().getCountryCode());
        SupplierDO_INT.setCountry(countryDO);

        currencyDO.setCurrencyCode(supplierDTO.getCurrency().getCurrencyCode());
        SupplierDO_INT.setCurrency(currencyDO);

        supplierIntRepo.save(SupplierDO_INT);
    }

    @Override
    public void modifySupplier(SupplierDTO supplierDTO) throws RecordNotFoundException {

        Optional<SupplierDO_INT> supplierIdOpt = supplierIntRepo.findById(supplierDTO.getSupplierId());

        if(!supplierIdOpt.isPresent()){

            throw new RecordNotFoundException("record not found");
        }

        SupplierDO_INT SupplierDO_INT = supplierIdOpt.get();

        SupplierDO_INT.setContactName(supplierDTO.getContactName());
        SupplierDO_INT.setCompanyName(supplierDTO.getCompanyName());
        SupplierDO_INT.setContactNumber(supplierDTO.getContactNumber());
        SupplierDO_INT.setAddressLine1(supplierDTO.getAddressLine1());
        SupplierDO_INT.setAddressLine2(supplierDTO.getAddressLine2());
        SupplierDO_INT.setAddressLine3(supplierDTO.getAddressLine3());
        SupplierDO_INT.setApprovedBy(supplierDTO.getApprovedBy());
        SupplierDO_INT.setContactEmail(supplierDTO.getContactEmail());
        SupplierDO_INT.setPostalCode(supplierDTO.getPostalCode());
        SupplierDO_INT.setTown(supplierDTO.getTown());
        SupplierDO_INT.setUserId(supplierDTO.getUserId());
        SupplierDO_INT.setValidTill(supplierDTO.getValidTillDate().getTime());
        SupplierDO_INT.setLastUpdatedBy(supplierDTO.getLastUpdatedBy());
        SupplierDO_INT.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(supplierDTO.getCountry().getCountryCode());
        SupplierDO_INT.setCountry(countryDO);

        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(supplierDTO.getCurrency().getCurrencyCode());
        SupplierDO_INT.setCurrency(currencyDO);

        SupplierStatusDO supplierStatusDO = context.getBean(SupplierStatusDO.class);
        supplierStatusDO.setSupplierStatusCode(supplierDTO.getSupplierStatus().getSupplierStatusCode());
        SupplierDO_INT.setSupplierStatus(supplierStatusDO);

        SupplierQualificationStatusDO supplierQualificationStatusDO = context.getBean(SupplierQualificationStatusDO.class);
        supplierQualificationStatusDO.setSupplierQfCode(supplierDTO.getSupplierQualificationStatus().getSupplierQfCode());
        SupplierDO_INT.setSupplierQualificationStatus(supplierQualificationStatusDO);

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode("PE");
        SupplierDO_INT.setReviewStatus(reviewStatusDO);

        supplierIntRepo.save(SupplierDO_INT);

    }

    @Override
    public boolean approveRejectSupplier(String supplierId, String decision) {

       Optional<SupplierDO_INT> optionalSupplierDOInt = supplierIntRepo.findById(supplierId);

       if(optionalSupplierDOInt.isPresent()){

          SupplierDO_INT supplierDOInt = optionalSupplierDOInt.get();

          if(supplierDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("PE")){

              SupplierDO supplierDO = context.getBean(SupplierDO.class);

              BeanUtils.copyProperties(supplierDOInt, supplierDO);

              ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);

              if(decision.equalsIgnoreCase("APPROVE")) {
                  reviewStatusDO.setReviewCode("AP");
              } else {
                  reviewStatusDO.setReviewCode("RE");
              }

              supplierDO.setReviewStatus(reviewStatusDO);

              supplierRepo.save(supplierDO);

              supplierDOInt.setReviewStatus(reviewStatusDO);
              supplierIntRepo.save(supplierDOInt);

              return true;

          }
       }

        return false;
    }

}
