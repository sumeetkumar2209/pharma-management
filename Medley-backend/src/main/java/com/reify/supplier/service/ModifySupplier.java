package com.reify.supplier.service;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.supplier.DTO.SupplierDTO;

public interface ModifySupplier {

    public void modifySupplier(SupplierDTO supplierDTO) throws RecordNotFoundException, InvalidStatusException;
}
