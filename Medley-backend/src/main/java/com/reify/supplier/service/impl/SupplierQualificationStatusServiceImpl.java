package com.reify.supplier.service.impl;

import com.reify.supplier.DTO.SupplierQualificationStatusDTO;
import com.reify.supplier.model.SupplierQualificationStatusDO;
import com.reify.supplier.repo.SupplierQualificationStatusRepo;
import com.reify.supplier.service.SupplierQualificationStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierQualificationStatusServiceImpl implements SupplierQualificationStatusService {

    @Autowired
    ApplicationContext context;

    @Autowired
    SupplierQualificationStatusRepo supplierQualificationStatusRepo;

    @Override
    public List<SupplierQualificationStatusDTO> getAllSupplierQualificationStatus() {

    List<SupplierQualificationStatusDO> supplierQualificationStatusDOList = supplierQualificationStatusRepo.findAll();

    List<SupplierQualificationStatusDTO> supplierQualificationStatusDTOList;

    if(!supplierQualificationStatusDOList.isEmpty()){

        supplierQualificationStatusDTOList = supplierQualificationStatusDOList.stream().map(obj -> {

            SupplierQualificationStatusDTO supplierQualificationStatusDTO = context.getBean(SupplierQualificationStatusDTO.class);
            BeanUtils.copyProperties(obj,supplierQualificationStatusDTO);
            return supplierQualificationStatusDTO;
        }).collect(Collectors.toList());

        return supplierQualificationStatusDTOList;

    }

        return null;
    }
}
