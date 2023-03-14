package com.reify.supplier.service.impl;

import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.DTO.SupplierSearchDTO;
import com.reify.supplier.model.SupplierDO;
import com.reify.supplier.repo.SupplierRepo;
import com.reify.supplier.service.SupplierSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierSearchServiceImpl implements SupplierSearchService {

    @Autowired
    SupplierRepo supplierRepo;

    @Autowired
    ApplicationContext context;


    @Override
    public List<SupplierDTO> getSupplier(SupplierSearchDTO supplierSearchDTO) {

       List<SupplierDO> supplierDOList= supplierRepo.searchSupplierByFilter(supplierSearchDTO);

       List<SupplierDTO> supplierDTOList;

       if(!supplierDOList.isEmpty()){

           supplierDTOList = supplierDOList.stream().map( obj -> {

               SupplierDTO supplierDTO = context.getBean(SupplierDTO.class);
               BeanUtils.copyProperties(obj, supplierDTO);

               return supplierDTO;

           }).collect(Collectors.toList());

           return supplierDTOList;
       }

        return null;
    }
}
