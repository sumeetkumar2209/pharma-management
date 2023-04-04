package com.reify.supplier.service.impl;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.*;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.SupplierAuditDO;
import com.reify.supplier.model.SupplierDO_INT;
import com.reify.supplier.repo.SupplierAuditRepo;
import com.reify.supplier.repo.SupplierIntRepo;
import com.reify.supplier.service.ModifySupplier;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class ModifyRejectedSupplier implements ModifySupplier {

    @Autowired
    SupplierIntRepo supplierIntRepo;

    @Autowired
    ApplicationContext context;

    @Autowired
    SupplierAuditRepo supplierAuditRepo;

    @Override
    @Transactional
    public void modifySupplier(SupplierDTO supplierDTO) throws InvalidStatusException, RecordNotFoundException {

        SupplierDO_INT supplierDOIntPending = supplierIntRepo.findBySupplierId(supplierDTO.getSupplierId());
        if (supplierDOIntPending != null &&
                !supplierDOIntPending.getReviewStatus().getReviewCode().equalsIgnoreCase("RE")) {
            throw new InvalidStatusException("Already pending modification");
        } else if (supplierDOIntPending == null) {
            throw new RecordNotFoundException("Record not found error");
        }

        SupplierDO_INT supplierDO_INT = context.getBean(SupplierDO_INT.class);
        BeanUtils.copyProperties(supplierDOIntPending, supplierDO_INT);

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

        SupplierDO_INT insertedObj = supplierIntRepo.save(supplierDO_INT);


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
        supplierAuditDO.setLastUpdatedBy(supplierDTO.getUserId());
        supplierAuditDO.setWorkFlowId(insertedObj.getWorkFlowId());

        supplierAuditRepo.save(supplierAuditDO);

    }
}
