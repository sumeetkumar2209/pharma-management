package com.reify.supplier.service.impl;

import com.reify.supplier.DTO.SupplierStatusDTO;
import com.reify.supplier.model.SupplierStatusDO;
import com.reify.supplier.repo.SupplierStatusRepo;
import com.reify.supplier.service.SupplierStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierStatusServiceImpl implements SupplierStatusService {

    @Autowired
    SupplierStatusRepo supplierStatusRepo;

    @Autowired
    ApplicationContext context;

    @Override
    public List<SupplierStatusDTO> getAllStatus() {

        List<SupplierStatusDO> supplierStatusDOList = supplierStatusRepo.findAll();

        List<SupplierStatusDTO> supplierStatusDTOList;

        if(!supplierStatusDOList.isEmpty()){

            supplierStatusDTOList = supplierStatusDOList.stream().map(obj -> {

                SupplierStatusDTO supplierStatusDTO = context.getBean(SupplierStatusDTO.class);
                BeanUtils.copyProperties(obj,supplierStatusDTO);
                return supplierStatusDTO;
            }).collect(Collectors.toList());

            return supplierStatusDTOList;

        }

        return null;
    }
}
