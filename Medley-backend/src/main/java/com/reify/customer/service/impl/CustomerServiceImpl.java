package com.reify.customer.service.impl;

import com.reify.common.DTO.ApproveRejectDTO;
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
import com.reify.customer.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ApplicationContext context;

    @Autowired
    CustomerIntRepo customerIntRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerAuditRepo customerAuditRepo;

    @Transactional
    @Override
    public void addCustomer(CustomerDTO customerDTO) {

        CustomerDO_INT customerDOInt = context.getBean(CustomerDO_INT.class);
        BeanUtils.copyProperties(customerDTO,customerDOInt);


        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(customerDTO.getCountry());
        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(customerDTO.getCurrency());
        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        StatusDO customerStatusDO = context.getBean(StatusDO.class);
        customerStatusDO.setStatusCode(customerDTO.getCustomerStatus());
        QualificationStatusDO qualificationStatusDO = context.getBean(QualificationStatusDO.class);
        qualificationStatusDO.setQualificationCode(customerDTO.getCustomerQualificationStatus());

        reviewStatusDO.setReviewCode("PE");
        customerDOInt.setCountry(countryDO);
        customerDOInt.setCurrency(currencyDO);
        customerDOInt.setReviewStatus(reviewStatusDO);
        customerDOInt.setCustomerStatus(customerStatusDO);
        customerDOInt.setCustomerQualificationStatus(qualificationStatusDO);

        customerDOInt.setInitialAdditionDate(System.currentTimeMillis()/1000);
        customerDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        customerDOInt.setValidTill(customerDTO.getValidTillDate().getTime()/1000);
        customerDOInt.setLastUpdatedBy(customerDTO.getUserId());

        CustomerDO_INT insertedCustomerWorkFlowObj= customerIntRepo.save(customerDOInt);

        //code added for audit table

        CustomerAuditDO customerAuditDO = context.getBean(CustomerAuditDO.class);
        BeanUtils.copyProperties(customerDTO,customerAuditDO);
        customerAuditDO.setCustomerStatus(customerStatusDO);
        customerAuditDO.setReviewStatus(reviewStatusDO);
        customerAuditDO.setCustomerQualificationStatus(qualificationStatusDO);
        customerAuditDO.setCountry(countryDO);
        customerAuditDO.setCurrency(currencyDO);

        customerAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
        customerAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        customerAuditDO.setValidTill(customerDTO.getValidTillDate().getTime()/1000);
        customerAuditDO.setWorkFlowId(insertedCustomerWorkFlowObj.getWorkFlowId());
        customerAuditDO.setLastUpdatedBy(customerDTO.getUserId());

        customerAuditRepo.save(customerAuditDO);

    }

    @Override
    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException {

       Optional<CustomerDO> CustomerOpt = customerRepo.findById(customerDTO.getCustomerId());


       if(!CustomerOpt.isPresent()){

           throw new RecordNotFoundException("record not found");
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

        customerIntRepo.save(customerDOInt);

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
        customerAuditDO.setWorkFlowId(customerDO.getWorkFlowId());
        customerAuditDO.setLastUpdatedBy(customerDOInt.getUserId());

        customerAuditRepo.save(customerAuditDO);


    }

    @Transactional
    @Override
    public boolean approveRejectCustomer(ApproveRejectDTO approveRejectDTO) {

        Optional<CustomerDO_INT>  customerIntOpt= customerIntRepo.findById(approveRejectDTO.getWorkflowId());

        if (customerIntOpt.isPresent()){

            CustomerDO_INT customerDOInt = customerIntOpt.get();

            if (customerDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("PE")){

                CustomerDO customerDO = context.getBean(CustomerDO.class);

                BeanUtils.copyProperties(customerDOInt, customerDO);

                ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);

                if (approveRejectDTO.getDecision().equalsIgnoreCase("AP")){

                    reviewStatusDO.setReviewCode("AP");
                    customerDO.setReviewStatus(reviewStatusDO);
                    customerDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                    customerDO.setLastUpdatedBy(customerDOInt.getApprover());
                    customerDO.setComments(approveRejectDTO.getComments());

                    customerRepo.save(customerDO);

                } else {
                    reviewStatusDO.setReviewCode("RE");
                }

                customerDOInt.setReviewStatus(reviewStatusDO);
                customerDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                customerDOInt.setComments(approveRejectDTO.getComments());
                customerDOInt.setLastUpdatedBy(customerDOInt.getApprover());
                customerIntRepo.save(customerDOInt);

                CustomerAuditDO customerAuditDO = context.getBean(CustomerAuditDO.class);
                BeanUtils.copyProperties(customerDO, customerAuditDO);

                customerAuditDO.setReviewStatus(reviewStatusDO);
                customerAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
                customerAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                customerAuditDO.setValidTill(customerDOInt.getValidTill()/ 1000);
                customerAuditDO.setWorkFlowId(approveRejectDTO.getWorkflowId());
                customerAuditDO.setComments(approveRejectDTO.getComments());
                customerAuditDO.setLastUpdatedBy(customerDOInt.getApprover());

                customerAuditRepo.save(customerAuditDO);


                return true;
            }
        }

        return false;
    }
}
