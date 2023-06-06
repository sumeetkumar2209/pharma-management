package com.reify.product.service;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.customer.service.ModifyCustomer;
import com.reify.product.DTO.ProductDTO;

public interface ModifyProduct {
    public String modifyProduct(ProductDTO productDTO) throws RecordNotFoundException, InvalidStatusException;
}
