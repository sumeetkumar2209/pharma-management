package com.reify.customer.service;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.customer.DTO.CustomerDTO;

public interface CustomerService {

    public void addCustomer(CustomerDTO customerDTO);

    public void modifyCustomer(CustomerDTO customerDTO) throws RecordNotFoundException, InvalidStatusException;

    public boolean approveRejectCustomer(ApproveRejectDTO approveRejectDTO);
}
