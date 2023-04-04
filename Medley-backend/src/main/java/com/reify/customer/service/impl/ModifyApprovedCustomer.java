package com.reify.customer.service.impl;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.*;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.model.CustomerAuditDO;
import com.reify.customer.model.CustomerDO;
import com.reify.customer.model.CustomerDO_INT;
import com.reify.customer.repo.CustomerAuditRepo;
import com.reify.customer.repo.CustomerIntRepo;
import com.reify.customer.repo.CustomerRepo;
import com.reify.customer.service.ModifyCustomer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ModifyApprovedCustomer implements ModifyCustomer {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerIntRepo customerIntRepo;
    @Autowired
    ApplicationContext context;
    @Autowired
    CustomerAuditRepo customerAuditRepo;

    @Override
    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<CustomerDO> CustomerOpt = customerRepo.findById(customerDTO.getCustomerId());

        if(!CustomerOpt.isPresent()){

            throw new RecordNotFoundException("record not found");
        }

        CustomerDO_INT customerDOIntPending = customerIntRepo.findByCustomerId(customerDTO.getCustomerId());
        if (customerDOIntPending != null) {
            throw new InvalidStatusException("Already pending modification");
        }
        CustomerDO customerDO = CustomerOpt.get();

        if(!(customerDO.getReviewStatus().getReviewCode().equalsIgnoreCase("AP"))){

            throw new InvalidStatusException("Only Approved or Rejected Review Status can be modified");
        }
        CustomerDO_INT customerDOInt = context.getBean(CustomerDO_INT.class);
        BeanUtils.copyProperties(customerDO, customerDOInt);

        customerDOInt.setCustomerName(customerDTO.getCustomerName());
        customerDOInt.setContactName(customerDTO.getContactName());
        customerDOInt.setContactNumber(customerDTO.getContactNumber());
        customerDOInt.setAddressLine1(customerDTO.getAddressLine1());
        customerDOInt.setAddressLine2(customerDTO.getAddressLine2());
        customerDOInt.setAddressLine3(customerDTO.getAddressLine3());
        customerDOInt.setApprover(customerDTO.getApprover());
        customerDOInt.setContactEmail(customerDTO.getContactEmail());
        customerDOInt.setPostalCode(customerDTO.getPostalCode());
        customerDOInt.setTown(customerDTO.getTown());
        customerDOInt.setUserId(customerDTO.getUserId());
        customerDOInt.setValidTill(customerDTO.getValidTillDate().getTime()/1000);
        customerDOInt.setLastUpdatedBy(customerDTO.getUserId());
        customerDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(customerDTO.getCountry());
        customerDOInt.setCountry(countryDO);

        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(customerDTO.getCurrency());
        customerDOInt.setCurrency(currencyDO);

        StatusDO customerStatusDO = context.getBean(StatusDO.class);
        customerStatusDO.setStatusCode(customerDTO.getCustomerStatus());
        customerDOInt.setCustomerStatus(customerStatusDO);

        QualificationStatusDO customerQualificationStatusDO = context.getBean(QualificationStatusDO.class);
        customerQualificationStatusDO.setQualificationCode(customerDTO.getCustomerQualificationStatus());
        customerDOInt.setCustomerQualificationStatus(customerQualificationStatusDO);

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode("PE");
        customerDOInt.setReviewStatus(reviewStatusDO);

        CustomerDO_INT insertedObj = customerIntRepo.save(customerDOInt);

        //code added for Audit
        CustomerAuditDO customerAuditDO = context.getBean(CustomerAuditDO.class);
        BeanUtils.copyProperties(customerDTO, customerAuditDO);

        ReviewStatusDO reviewStatusAuditDO = context.getBean(ReviewStatusDO.class);
        reviewStatusAuditDO.setReviewCode("MD");

        customerAuditDO.setReviewStatus(reviewStatusAuditDO);
        customerAuditDO.setCustomerStatus(customerStatusDO);
        customerAuditDO.setCustomerQualificationStatus(customerQualificationStatusDO);
        customerAuditDO.setCountry(countryDO);
        customerAuditDO.setCurrency(currencyDO);
        customerAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
        customerAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        customerAuditDO.setValidTill(customerDOInt.getValidTill()/ 1000);
        customerAuditDO.setWorkFlowId(insertedObj.getWorkFlowId());
        customerAuditDO.setLastUpdatedBy(customerDOInt.getUserId());

        customerAuditRepo.save(customerAuditDO);


    }
}
