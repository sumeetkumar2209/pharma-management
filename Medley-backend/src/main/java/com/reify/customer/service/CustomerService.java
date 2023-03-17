package com.reify.customer.service;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.model.CustomerDO_INT;

public interface CustomerService {

    public void addCustomer(CustomerDTO customerDTO);

    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException;

    public boolean approveRejectCustomer(String customerId, String decision);
}
