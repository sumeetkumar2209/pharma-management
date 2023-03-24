package com.reify.customer.helper;

import com.reify.customer.DTO.CustomerSearchDTO;
import com.reify.customer.model.CustomerDO;
import com.reify.supplier.DTO.SupplierSearchDTO;

import java.util.List;

public interface CustomerSearch {

    public List<CustomerDO> searchCustomerByCriteria(CustomerSearchDTO customerSearchDTO);

    public long countCustomerByFilter(CustomerSearchDTO customerSearchDTO);

}
