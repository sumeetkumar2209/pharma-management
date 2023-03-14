package com.reify.supplier.service;

import com.reify.common.exception.RecordNotFoundException;
import com.reify.supplier.DTO.SupplierDTO;

import java.util.List;

public interface SupplierService {

    public void addSupplier(SupplierDTO supplierDTO);

    public void modifySupplier(SupplierDTO supplierDTO) throws RecordNotFoundException;

    public boolean approveRejectSupplier(String supplierId,String decision);

    //public List<SupplierDTO> getSupplier();


}
