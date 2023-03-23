package com.reify.supplier.service.impl;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.*;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.SupplierAuditDO;
import com.reify.supplier.model.SupplierDO;
import com.reify.supplier.model.SupplierDO_INT;
import com.reify.supplier.repo.SupplierAuditRepo;
import com.reify.supplier.repo.SupplierIntRepo;
import com.reify.supplier.repo.SupplierRepo;
import com.reify.supplier.service.SupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    ApplicationContext context;

    @Autowired
    SupplierIntRepo supplierIntRepo;

    @Autowired
    SupplierRepo supplierRepo;
    @Autowired
    SupplierAuditRepo supplierAuditRepo;

    @Transactional
    @Override
    public void addSupplier(SupplierDTO supplierDTO) {

        SupplierDO_INT supplierDO_INT = context.getBean(SupplierDO_INT.class);
        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        StatusDO supplierStatusDO = context.getBean(StatusDO.class);
        QualificationStatusDO supplierQualificationStatusDO = context.getBean(QualificationStatusDO.class);
        CountryDO countryDO = context.getBean(CountryDO.class);
        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);

        BeanUtils.copyProperties(supplierDTO,supplierDO_INT);
        supplierDO_INT.setWorkFlowId(supplierDTO.getWorkFlowId());
        supplierDO_INT.setInitialAdditionDate(System.currentTimeMillis()/1000);
        supplierDO_INT.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        supplierDO_INT.setValidTill(supplierDTO.getValidTillDate().getTime()/1000);
        supplierDO_INT.setLastUpdatedBy(supplierDTO.getUserId());

        reviewStatusDO.setReviewCode("PE");

        supplierDO_INT.setReviewStatus(reviewStatusDO);

        supplierStatusDO.setStatusCode(supplierDTO.getSupplierStatus());
        supplierDO_INT.setSupplierStatus(supplierStatusDO);

        supplierQualificationStatusDO.setQualificationCode(supplierDTO.getSupplierQualificationStatus());
        supplierDO_INT.setSupplierQualificationStatus(supplierQualificationStatusDO);

        countryDO.setCountryCode(supplierDTO.getCountry());
        supplierDO_INT.setCountry(countryDO);

        currencyDO.setCurrencyCode(supplierDTO.getCurrency());
        supplierDO_INT.setCurrency(currencyDO);

        SupplierDO_INT insertedSupplierWorkFlowObj = supplierIntRepo.save(supplierDO_INT);

        //code added for audit table

        SupplierAuditDO supplierAuditDO = context.getBean(SupplierAuditDO.class);
        BeanUtils.copyProperties(supplierDTO,supplierAuditDO );
        supplierAuditDO.setReviewStatus(reviewStatusDO);
        supplierAuditDO.setSupplierStatus(supplierStatusDO);
        supplierAuditDO.setSupplierQualificationStatus(supplierQualificationStatusDO);
        supplierAuditDO.setCountry(countryDO);
        supplierAuditDO.setCurrency(currencyDO);

        supplierAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
        supplierAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        supplierAuditDO.setValidTill(supplierDTO.getValidTillDate().getTime()/ 1000);
        supplierAuditDO.setWorkFlowId(insertedSupplierWorkFlowObj.getWorkFlowId());
        supplierAuditDO.setLastUpdatedBy(supplierDTO.getUserId());

        supplierAuditRepo.save(supplierAuditDO);


    }
    @Transactional
    @Override
    public void modifySupplier(SupplierDTO supplierDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<SupplierDO> supplierIdOpt = supplierRepo.findById(supplierDTO.getSupplierId());

        if(!supplierIdOpt.isPresent()){

            throw new RecordNotFoundException("record not found");
        }

        SupplierDO supplierDO = supplierIdOpt.get();

        if(!(supplierDO.getReviewStatus().getReviewCode().equalsIgnoreCase("AP"))){

            throw new InvalidStatusException("Only Approved or Rejected Review Status can be modified");

        }
        SupplierDO_INT supplierDO_INT = context.getBean(SupplierDO_INT.class);
        BeanUtils.copyProperties(supplierDO, supplierDO_INT);

        supplierDO_INT.setContactName(supplierDTO.getContactName());
        supplierDO_INT.setCompanyName(supplierDTO.getCompanyName());
        supplierDO_INT.setContactNumber(supplierDTO.getContactNumber());
        supplierDO_INT.setAddressLine1(supplierDTO.getAddressLine1());
        supplierDO_INT.setAddressLine2(supplierDTO.getAddressLine2());
        supplierDO_INT.setAddressLine3(supplierDTO.getAddressLine3());
        supplierDO_INT.setApprover(supplierDTO.getApprover());
        supplierDO_INT.setContactEmail(supplierDTO.getContactEmail());
        supplierDO_INT.setPostalCode(supplierDTO.getPostalCode());
        supplierDO_INT.setTown(supplierDTO.getTown());
        supplierDO_INT.setUserId(supplierDTO.getUserId());
        supplierDO_INT.setValidTill(supplierDTO.getValidTillDate().getTime());
        supplierDO_INT.setLastUpdatedBy(supplierDTO.getUserId());
        supplierDO_INT.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(supplierDTO.getCountry());
        supplierDO_INT.setCountry(countryDO);

        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(supplierDTO.getCurrency());
        supplierDO_INT.setCurrency(currencyDO);

        StatusDO supplierStatusDO = context.getBean(StatusDO.class);
        supplierStatusDO.setStatusCode(supplierDTO.getSupplierStatus());
        supplierDO_INT.setSupplierStatus(supplierStatusDO);

        QualificationStatusDO supplierQualificationStatusDO = context.getBean(QualificationStatusDO.class);
        supplierQualificationStatusDO.setQualificationCode(supplierDTO.getSupplierQualificationStatus());
        supplierDO_INT.setSupplierQualificationStatus(supplierQualificationStatusDO);

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode("PE");
        supplierDO_INT.setReviewStatus(reviewStatusDO);

        supplierIntRepo.save(supplierDO_INT);


        //code added for audit table

        SupplierAuditDO supplierAuditDO = context.getBean(SupplierAuditDO.class);
        BeanUtils.copyProperties(supplierDTO,supplierAuditDO );

        ReviewStatusDO reviewStatusAuditDO = context.getBean(ReviewStatusDO.class);
        reviewStatusAuditDO.setReviewCode("MD");

        supplierAuditDO.setReviewStatus(reviewStatusAuditDO);
        supplierAuditDO.setSupplierStatus(supplierStatusDO);
        supplierAuditDO.setSupplierQualificationStatus(supplierQualificationStatusDO);
        supplierAuditDO.setCountry(countryDO);
        supplierAuditDO.setCurrency(currencyDO);

        supplierAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
        supplierAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        supplierAuditDO.setValidTill(supplierDTO.getValidTillDate().getTime()/ 1000);
        supplierAuditDO.setWorkFlowId(supplierDO.getWorkFlowId());
        supplierAuditDO.setLastUpdatedBy(supplierDTO.getUserId());

        supplierAuditRepo.save(supplierAuditDO);

    }
    @Override
    public boolean approveRejectSupplier(ApproveRejectDTO approveRejectDTO)  {

       Optional<SupplierDO_INT> optionalSupplierDOInt = supplierIntRepo.findById(approveRejectDTO.getWorkflowId());

       if(optionalSupplierDOInt.isPresent()){

          SupplierDO_INT supplierDOInt = optionalSupplierDOInt.get();

          if(supplierDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("PE")){

              SupplierDO supplierDO = context.getBean(SupplierDO.class);

              BeanUtils.copyProperties(supplierDOInt, supplierDO);

              ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);

              if(approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {

                  reviewStatusDO.setReviewCode("AP");
                  supplierDO.setReviewStatus(reviewStatusDO);
                  supplierDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                  supplierDO.setLastUpdatedBy(supplierDOInt.getApprover());
                  supplierDO.setComments(approveRejectDTO.getComments());

                  supplierRepo.save(supplierDO);

              } else {
                  reviewStatusDO.setReviewCode("RE");
              }



              supplierDOInt.setReviewStatus(reviewStatusDO);
              supplierDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
              supplierDOInt.setLastUpdatedBy(supplierDOInt.getApprover());
              supplierDOInt.setComments(approveRejectDTO.getComments());

              supplierIntRepo.save(supplierDOInt);

              SupplierAuditDO supplierAuditDO = context.getBean(SupplierAuditDO.class);
              BeanUtils.copyProperties(supplierDO,supplierAuditDO );
              CountryDO countryDO = context.getBean(CountryDO.class);

              countryDO.setCountryCode(supplierDO.getCountry().getCountryCode());
              supplierAuditDO.setCountry(countryDO);

              CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
              currencyDO.setCurrencyCode(supplierDO.getCurrency().getCurrencyCode());
              supplierAuditDO.setCurrency(currencyDO);

              StatusDO supplierStatusDO = context.getBean(StatusDO.class);
              supplierStatusDO.setStatusCode(supplierDO.getSupplierStatus().getStatusCode());
              supplierAuditDO.setSupplierStatus(supplierStatusDO);

              QualificationStatusDO supplierQualificationStatusDO = context.getBean(QualificationStatusDO.class);
              supplierQualificationStatusDO.setQualificationCode(supplierDO.getSupplierQualificationStatus().getQualificationCode());
              supplierAuditDO.setSupplierQualificationStatus(supplierQualificationStatusDO);

              supplierAuditDO.setReviewStatus(reviewStatusDO);
              supplierAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
              supplierAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
              supplierAuditDO.setValidTill(supplierDO.getValidTill()/ 1000);
              supplierAuditDO.setWorkFlowId(approveRejectDTO.getWorkflowId());
              supplierAuditDO.setComments(approveRejectDTO.getComments());
              supplierAuditDO.setLastUpdatedBy(supplierDOInt.getApprover());

              supplierAuditRepo.save(supplierAuditDO);

              //supplierIntRepo.deleteById(approveRejectDTO.getWorkflowId());

              return true;

          }
       }

        return false;
    }

}
