package com.reify.product.helper;

import com.reify.product.DTO.ProductSearchDTO;
import com.reify.product.model.ProductDO;

import java.util.List;

public interface ProductSearch {

    public List<ProductDO> searchProductByFilter(ProductSearchDTO productSearchDTO);

    public long countProductByFilter(ProductSearchDTO productSearchDTO);

}
