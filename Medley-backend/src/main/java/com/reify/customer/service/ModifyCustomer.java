package com.reify.customer.service;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.customer.DTO.CustomerDTO;

public interface ModifyCustomer {

    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException;
}
