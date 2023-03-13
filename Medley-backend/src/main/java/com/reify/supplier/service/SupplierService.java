package com.reify.supplier.service;

import com.reify.supplier.DTO.ReviewStatusDTO;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.ReviewStatusDO;

import java.util.List;

public interface SupplierService {

    public void addSupplier(SupplierDTO supplierDTO);

    public void modifySupplier(SupplierDTO supplierDTO);

}
