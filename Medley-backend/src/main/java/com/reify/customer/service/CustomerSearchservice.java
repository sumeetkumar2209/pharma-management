package com.reify.customer.service;

import com.reify.customer.DTO.CustomerDTO;
import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.supplier.DTO.InProgressWorkFlowDTO;

import java.util.List;

public interface CustomerSearchservice {

    public List<CustomerDTO> getCustomer(CustomerSearchDTO customerSearchDTO);

    public long getTotalCustomerCount(CustomerSearchDTO customerSearchDTO);

    public List<CustomerDTO> getCustomerBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);

    public long getCustomerCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);
}
