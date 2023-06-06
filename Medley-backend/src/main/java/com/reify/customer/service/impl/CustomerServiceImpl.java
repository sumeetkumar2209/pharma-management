package com.reify.customer.service.impl;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.*;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.factory.ModifyCustomerFactory;
import com.reify.customer.model.CustomerAuditDO;
import com.reify.customer.model.CustomerDO;
import com.reify.customer.model.CustomerDO_INT;
import com.reify.customer.repo.CustomerAuditRepo;
import com.reify.customer.repo.CustomerIntRepo;
import com.reify.customer.repo.CustomerRepo;
import com.reify.customer.service.CustomerService;
import com.reify.customer.service.ModifyCustomer;
import com.reify.supplier.model.SupplierDO_INT;
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
    @Autowired
    ModifyCustomerFactory modifyCustomerFactory;

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

    @Transactional
    @Override
    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<CustomerDO> customerDOOptional = customerRepo.findById(customerDTO.getCustomerId());

        CustomerDO_INT customerDOInt = customerIntRepo.findByCustomerId(customerDTO.getCustomerId());

        boolean main = false;
        boolean stage = false;

        if(customerDOOptional.isPresent() && customerDOOptional.get().getReviewStatus()
                .getReviewCode().equalsIgnoreCase("AP")){
            main = true;
        }
        if(customerDOInt != null && customerDOInt.getReviewStatus().getReviewCode()
                  .equalsIgnoreCase("RE")){
            stage = true;
        }

        ModifyCustomer modifyCustomer = modifyCustomerFactory.getInstanceName(main, stage);
        modifyCustomer.modifyCustomer(customerDTO);

    }

    @Transactional
    @Override
    public boolean approveRejectCustomer(ApproveRejectDTO approveRejectDTO) {

        Optional<CustomerDO_INT>  customerIntOpt= customerIntRepo.findById(approveRejectDTO.getWorkFlowId());

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


                CustomerAuditDO customerAuditDO = context.getBean(CustomerAuditDO.class);
                BeanUtils.copyProperties(customerDO, customerAuditDO);

                customerAuditDO.setReviewStatus(reviewStatusDO);
                customerAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
                customerAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                customerAuditDO.setValidTill(customerDOInt.getValidTill()/ 1000);
                customerAuditDO.setWorkFlowId(approveRejectDTO.getWorkFlowId());
                customerAuditDO.setComments(approveRejectDTO.getComments());
                customerAuditDO.setLastUpdatedBy(customerDOInt.getApprover());

                customerAuditRepo.saveAndFlush(customerAuditDO);

                if (approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {
                    customerIntRepo.deleteById(approveRejectDTO.getWorkFlowId());
                } else {
                    customerDOInt.setReviewStatus(reviewStatusDO);
                    customerDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                    customerDOInt.setLastUpdatedBy(customerDOInt.getApprover());
                    customerDOInt.setComments(approveRejectDTO.getComments());
                    customerIntRepo.save(customerDOInt);
                }


                return true;
            }
        }

        return false;
    }
}
