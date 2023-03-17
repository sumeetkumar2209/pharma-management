package com.reify.customer.service;

import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.DTO.CustomerSearchDTO;

import java.util.List;

public interface CustomerSearchservice {

    public List<CustomerDTO> getCustomer(CustomerSearchDTO customerSearchDTO);
}
