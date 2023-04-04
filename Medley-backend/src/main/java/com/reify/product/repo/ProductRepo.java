package com.reify.product.repo;

import com.reify.product.helper.ProductSearch;
import com.reify.product.model.ProductDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductDO,String> , ProductSearch {
}
