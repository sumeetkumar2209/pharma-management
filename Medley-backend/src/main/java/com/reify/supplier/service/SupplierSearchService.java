package com.reify.supplier.service;

import com.reify.common.DTO.InProgressWorkFlowDTO;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.DTO.SupplierSearchDTO;

import java.util.List;

public interface SupplierSearchService {

    public List<SupplierDTO> getSupplier(SupplierSearchDTO supplierSearchDTO);

    public long getTotalSupplierCount(SupplierSearchDTO supplierSearchDTO);

    public List<SupplierDTO> getSupplierBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);

    public long getSupplierCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO);
}
