package com.reify.supplier.helper;

import com.reify.supplier.DTO.SupplierSearchDTO;
import com.reify.supplier.model.SupplierDO;

import java.util.List;

public interface SearchSupplier {

    public List<SupplierDO> searchSupplierByFilter(SupplierSearchDTO supplierSearchDTO);

}
