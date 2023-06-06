package com.reify.product.service;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.AlreadyExistException;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.product.DTO.ProductDTO;

public interface ProductService {

    public String addProduct(ProductDTO productDTO) throws AlreadyExistException;

    public String modifyProduct(ProductDTO productDTO) throws RecordNotFoundException, InvalidStatusException;

    public String approveRejectProduct(ApproveRejectDTO approveRejectDTO);

}
