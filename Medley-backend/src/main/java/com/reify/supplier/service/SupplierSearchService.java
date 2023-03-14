package com.reify.supplier.service;

import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.DTO.SupplierSearchDTO;

import java.util.List;

public interface SupplierSearchService {

    public List<SupplierDTO> getSupplier(SupplierSearchDTO supplierSearchDTO);
}
