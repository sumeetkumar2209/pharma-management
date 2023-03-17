package com.reify.customer.service.impl;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.*;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.model.CustomerDO;
import com.reify.customer.model.CustomerDO_INT;
import com.reify.customer.repo.CustomerIntRepo;
import com.reify.customer.repo.CustomerRepo;
import com.reify.customer.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ApplicationContext context;

    @Autowired
    CustomerIntRepo customerIntRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public void addCustomer(CustomerDTO customerDTO) {

        CustomerDO_INT customerDOInt = context.getBean(CustomerDO_INT.class);

        BeanUtils.copyProperties(customerDTO,customerDOInt);
        System.out.println(customerDOInt.toString());

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(customerDTO.getCountry().getCountryCode());
        customerDOInt.setCountry(countryDO);

        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(customerDTO.getCurrency().getCurrencyCode());
        customerDOInt.setCurrency(currencyDO);

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode(customerDTO.getReviewStatus().getReviewCode());
        customerDOInt.setReviewStatus(reviewStatusDO);

        StatusDO customerStatusDO = context.getBean(StatusDO.class);
        customerStatusDO.setStatusCode(customerDTO.getCustomerStatus().getStatusCode());
        customerDOInt.setCustomerStatus(customerStatusDO);

        QualificationStatusDO qualificationStatusDO = context.getBean(QualificationStatusDO.class);
        qualificationStatusDO.setQualificationCode(customerDTO.getCustomerQualificationStatus().getQualificationCode());
        customerDOInt.setCustomerQualificationStatus(qualificationStatusDO);

        customerIntRepo.save(customerDOInt);

    }

    @Override
    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException {

       Optional<CustomerDO_INT> CustomerOpt = customerIntRepo.findById(customerDTO.getCustomerId());


       if(!CustomerOpt.isPresent()){

           throw new RecordNotFoundException("record not found");
       }
       CustomerDO_INT customerDOInt = CustomerOpt.get();

       if(!(customerDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("AP")
       || customerDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("RE"))){

         throw new InvalidStatusException("Only Approved or Rejected Review Status can be modified");

       }
        customerDOInt.setCustomerName(customerDTO.getCustomerName());
        customerDOInt.setContactName(customerDTO.getContactName());
        customerDOInt.setContactNumber(customerDTO.getContactNumber());
        customerDOInt.setAddressLine1(customerDTO.getAddressLine1());
        customerDOInt.setAddressLine2(customerDTO.getAddressLine2());
        customerDOInt.setAddressLine3(customerDTO.getAddressLine3());
        customerDOInt.setApprovedBy(customerDTO.getApprovedBy());
        customerDOInt.setContactEmail(customerDTO.getContactEmail());
        customerDOInt.setPostalCode(customerDTO.getPostalCode());
        customerDOInt.setTown(customerDTO.getTown());
        customerDOInt.setUserId(customerDTO.getUserId());
        customerDOInt.setValidTill(customerDTO.getValidTillDate().getTime());
        customerDOInt.setLastUpdatedBy(customerDTO.getLastUpdatedBy());
        customerDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(customerDTO.getCountry().getCountryCode());
        customerDOInt.setCountry(countryDO);

        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);
        currencyDO.setCurrencyCode(customerDTO.getCurrency().getCurrencyCode());
        customerDOInt.setCurrency(currencyDO);

        StatusDO supplierStatusDO = context.getBean(StatusDO.class);
        supplierStatusDO.setStatusCode(customerDTO.getCustomerStatus().getStatusCode());
        customerDOInt.setCustomerStatus(supplierStatusDO);

        QualificationStatusDO supplierQualificationStatusDO = context.getBean(QualificationStatusDO.class);
        supplierQualificationStatusDO.setQualificationCode(customerDTO.getCustomerQualificationStatus().getQualificationCode());
        customerDOInt.setCustomerQualificationStatus(supplierQualificationStatusDO);

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode("PE");
        customerDOInt.setReviewStatus(reviewStatusDO);

        customerIntRepo.save(customerDOInt);

    }

    @Override
    public boolean approveRejectCustomer(String customerId, String decision) {

        Optional<CustomerDO_INT>  customerIntOpt= customerIntRepo.findById(customerId);

        if (customerIntOpt.isPresent()){

            CustomerDO_INT customerDOInt = customerIntOpt.get();

            if (customerDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("PE")){

                CustomerDO customerDO = context.getBean(CustomerDO.class);

                BeanUtils.copyProperties(customerDOInt, customerDO);

                ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);

                if (decision.equalsIgnoreCase("AP") ||
                        decision.equalsIgnoreCase("APPROVE")){

                    reviewStatusDO.setReviewCode("AP");

                } else {
                    reviewStatusDO.setReviewCode("RE");
                }
                customerDO.setReviewStatus(reviewStatusDO);
                customerDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                customerRepo.save(customerDO);

                customerDOInt.setReviewStatus(reviewStatusDO);
                customerDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
                customerIntRepo.save(customerDOInt);

                return true;
            }
        }

        return false;
    }
}
