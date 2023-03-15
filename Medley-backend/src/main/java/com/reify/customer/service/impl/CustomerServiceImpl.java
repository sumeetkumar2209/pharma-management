package com.reify.customer.service.impl;

import com.reify.common.model.CountryDO;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.model.CustomerDO_INT;
import com.reify.customer.repo.CustomerRepo;
import com.reify.customer.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ApplicationContext context;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public void addCustomer(CustomerDTO customerDTO) {

        CustomerDO_INT customerDOInt = context.getBean(CustomerDO_INT.class);

        BeanUtils.copyProperties(customerDTO,customerDOInt);

        CountryDO countryDO = context.getBean(CountryDO.class);
        countryDO.setCountryCode(customerDTO.getCountryDO().getCountryCode());
        customerDOInt.setCountryDO(countryDO);


    }
}
