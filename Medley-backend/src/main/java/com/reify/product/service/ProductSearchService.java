package com.reify.product.service;

import com.reify.product.DTO.ProductDTO;
import com.reify.product.DTO.ProductSearchDTO;
import com.reify.common.DTO.InProgressWorkFlowDTO;

import java.util.List;

public interface ProductSearchService {

    public List<ProductDTO> getProduct(ProductSearchDTO productSearchDTO);

    public long getTotalProductCount(ProductSearchDTO productSearchDTO);

    public List<ProductDTO> getProductBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);

    public long getProductCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);
}
