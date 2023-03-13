package com.reify.supplier.service;

import com.reify.supplier.DTO.SupplierStatusDTO;
import com.reify.supplier.model.SupplierStatusDO;

import java.util.List;

public interface SupplierStatusService {

    public List<SupplierStatusDTO> getAllStatus();
}
