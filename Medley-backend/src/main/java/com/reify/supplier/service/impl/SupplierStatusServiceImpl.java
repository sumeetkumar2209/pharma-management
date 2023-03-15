package com.reify.supplier.service.impl;

import com.reify.common.DTO.StatusDTO;
import com.reify.common.model.StatusDO;
import com.reify.supplier.repo.SupplierStatusRepo;
import com.reify.supplier.service.SupplierStatusService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierStatusServiceImpl implements SupplierStatusService {

    @Autowired
    SupplierStatusRepo supplierStatusRepo;

    @Autowired
    ApplicationContext context;

    @Override
    public List<StatusDTO> getAllStatus() {

        List<StatusDO> supplierStatusDOList = supplierStatusRepo.findAll();

        List<StatusDTO> supplierStatusDTOList;

        if(!supplierStatusDOList.isEmpty()){

            supplierStatusDTOList = supplierStatusDOList.stream().map(obj -> {

                StatusDTO supplierStatusDTO = context.getBean(StatusDTO.class);
                BeanUtils.copyProperties(obj,supplierStatusDTO);
                return supplierStatusDTO;
            }).collect(Collectors.toList());

            return supplierStatusDTOList;

        }

        return null;
    }
}
