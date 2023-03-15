package com.reify.supplier.service.impl;

import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.QualificationStatusDO;
import com.reify.common.model.StatusDO;
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
        StatusDO supplierStatusDO = context.getBean(StatusDO.class);
        QualificationStatusDO supplierQualificationStatusDO = context.getBean(QualificationStatusDO.class);
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

        supplierStatusDO.setStatusCode(supplierDTO.getSupplierStatus().getStatusCode());
        SupplierDO_INT.setSupplierStatus(supplierStatusDO);

        supplierQualificationStatusDO.setQualificationCode(supplierDTO.getSupplierQualificationStatus().getQualificationCode());
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

        SupplierDO_INT supplierDO_INT = supplierIdOpt.get();

        supplierDO_INT.setContactName(supplierDTO.getContactName());
        supplierDO_INT.setCompanyName(supplierDTO.getCompanyName());
        supplierDO_INT.setContactNumber(supplierDTO.getContactNumber());
        supplierDO_INT.setAddressLine1(supplierDTO.getAddressLine1());
        supplierDO_INT.setAddressLine2(supplierDTO.getAddressLine2());
        supplierDO_INT.setAddressLine3(supplierDTO.getAddressLine3());
        supplierDO_INT.setApprovedBy(supplierDTO.getApprovedBy());
        supplierDO_INT.setContactEmail(supplierDTO.getContactEmail());
        supplierDO_INT.setPostalCode(supplierDTO.getPostalCode());
        supplierDO_INT.setTown(supplierDTO.getTown());
        supplierDO_INT.setUserId(supplierDTO.getUserId());
        supplierDO_INT.setValidTill(supplierDTO.getValidTillDate().getTime());
        supplierDO_INT.setLastUpdatedBy(supplierDTO.getLastUpdatedBy());
        supplierDO_INT.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(supplierDTO.getCountry().getCountryCode());
        supplierDO_INT.setCountry(countryDO);

        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(supplierDTO.getCurrency().getCurrencyCode());
        supplierDO_INT.setCurrency(currencyDO);

        StatusDO supplierStatusDO = context.getBean(StatusDO.class);
        supplierStatusDO.setStatusCode(supplierDTO.getSupplierStatus().getStatusCode());
        supplierDO_INT.setSupplierStatus(supplierStatusDO);

        QualificationStatusDO supplierQualificationStatusDO = context.getBean(QualificationStatusDO.class);
        supplierQualificationStatusDO.setQualificationCode(supplierDTO.getSupplierQualificationStatus().getQualificationCode());
        supplierDO_INT.setSupplierQualificationStatus(supplierQualificationStatusDO);

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode("PE");
        supplierDO_INT.setReviewStatus(reviewStatusDO);

        supplierIntRepo.save(supplierDO_INT);

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

              if(decision.equalsIgnoreCase("APPROVE") ||
                      decision.equalsIgnoreCase("AP")) {
                  reviewStatusDO.setReviewCode("AP");
              } else {
                  reviewStatusDO.setReviewCode("RE");
              }

              supplierDO.setReviewStatus(reviewStatusDO);
              supplierDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

              supplierRepo.save(supplierDO);

              supplierDOInt.setReviewStatus(reviewStatusDO);
              supplierDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
              supplierIntRepo.save(supplierDOInt);

              return true;

          }
       }

        return false;
    }

}
