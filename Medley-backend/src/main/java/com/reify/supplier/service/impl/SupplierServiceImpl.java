package com.reify.supplier.service.impl;

import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.*;
import com.reify.supplier.repo.SupplierRepo;
import com.reify.supplier.service.SupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    ApplicationContext context;

    @Autowired
    SupplierRepo supplierRepo;

    @Override
    public void addSupplier(SupplierDTO supplierDTO) {

        SupplierDO supplierDO = context.getBean(SupplierDO.class);
        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        SupplierStatusDO supplierStatusDO = context.getBean(SupplierStatusDO.class);
        SupplierQualificationStatusDO supplierQualificationStatusDO = context.getBean(SupplierQualificationStatusDO.class);
        CountryDO countryDO = context.getBean(CountryDO.class);
        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);

        BeanUtils.copyProperties(supplierDTO,supplierDO);
        supplierDO.setSupplierId(supplierDTO.getSupplierId());
        supplierDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
        supplierDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);
        supplierDO.setValidTill(supplierDTO.getValidTillDate().getTime());

        if("DRAFT".equalsIgnoreCase(supplierDTO.getOption())) {
            reviewStatusDO.setReviewCode("DR");

        } else {
            reviewStatusDO.setReviewCode("PE");
        }

        supplierDO.setReviewStatus(reviewStatusDO);

        supplierStatusDO.setSupplierStatusCode(supplierDTO.getSupplierStatus().getSupplierStatusCode());
        supplierDO.setSupplierStatus(supplierStatusDO);

        supplierQualificationStatusDO.setSupplierQfCode(supplierDTO.getSupplierQualificationStatus().getSupplierQfCode());
        supplierDO.setSupplierQualificationStatus(supplierQualificationStatusDO);

        countryDO.setCountryCode(supplierDTO.getCountry().getCountryCode());
        supplierDO.setCountry(countryDO);

        currencyDO.setCurrencyCode(supplierDTO.getCurrency().getCurrencyCode());
        supplierDO.setCurrency(currencyDO);

        supplierRepo.save(supplierDO);
    }

    @Override
    public void modifySupplier(SupplierDTO supplierDTO) {

       SupplierDO supplierDO = supplierRepo.getOne(supplierDTO.getSupplierId());
        SupplierStatusDO supplierStatusDO = new SupplierStatusDO();
        supplierStatusDO.setSupplierStatusCode(supplierDTO.getSupplierStatus().getSupplierStatusCode());
        supplierDO.setSupplierStatus(supplierStatusDO);
        supplierDO.setLastUpdatedTimeStamp(System.currentTimeMillis()/1000);

        supplierRepo.save(supplierDO);

    }

}
