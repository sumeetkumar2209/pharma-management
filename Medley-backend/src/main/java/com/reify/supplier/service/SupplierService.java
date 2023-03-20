package com.reify.supplier.service;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.supplier.DTO.SupplierDTO;

public interface SupplierService {

    public void addSupplier(SupplierDTO supplierIntDTO);

    public void modifySupplier(SupplierDTO supplierDTO) throws RecordNotFoundException;

    public boolean approveRejectSupplier(ApproveRejectDTO approveRejectDTO);

}
