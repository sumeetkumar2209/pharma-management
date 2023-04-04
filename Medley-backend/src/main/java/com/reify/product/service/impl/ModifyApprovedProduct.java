package com.reify.product.service.impl;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.CountryDO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.model.ReviewStatusDO;
import com.reify.product.DTO.ProductDTO;
import com.reify.product.model.*;
import com.reify.product.repo.ProductAuditRepo;
import com.reify.product.repo.ProductIntRepo;
import com.reify.product.repo.ProductRepo;
import com.reify.product.service.ModifyProduct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ModifyApprovedProduct implements ModifyProduct {

    @Autowired
    ProductIntRepo productIntRepo;

    @Autowired
    ApplicationContext context;
    @Autowired
    ProductAuditRepo productAuditRepo;
    @Autowired
    ProductRepo productRepo;
    @Override
    public  String modifyProduct(ProductDTO productDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<ProductDO> productDOOpt = productRepo.findById(productDTO.getProductId());

        if (!productDOOpt.isPresent()) {

            throw new RecordNotFoundException("record not found");
        }
        ProductDO_INT productDOIntPending = productIntRepo.findByProductId(productDTO.getProductId());

        if (productDOIntPending != null) {
            throw new InvalidStatusException("Already pending modification");
        }

        ProductDO productDO = productDOOpt.get();
        System.out.println(productDO.getReviewStatus().getReviewCode() +" product review status");

        if (!(productDO.getReviewStatus().getReviewCode().equalsIgnoreCase("AP"))) {

            throw new InvalidStatusException("Only Approved or Rejected Review Status can be modified");


        }
        ProductDO_INT productDOInt = context.getBean(ProductDO_INT.class);
        BeanUtils.copyProperties(productDO, productDOInt);

        productDOInt.setProductId(productDTO.getProductId());
        productDOInt.setComments(productDTO.getComments());
        productDOInt.setLicenceNumber(productDTO.getLicenceNumber());
        productDOInt.setPackSize(productDOInt.getPackSize());
        productDOInt.setPricePerPack(productDTO.getPricePerPack());
        productDOInt.setProductName(productDTO.getProductName());
        productDOInt.setStrength(productDTO.getStrength());
        try {
            productDOInt.setProductPhoto(productDTO.getMultipartFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productDOInt.setTaxPercent(productDTO.getTaxPercent());
        productDOInt.setUserId(productDTO.getUserId());
        productDOInt.setLastUpdatedBy(productDTO.getUserId());
        productDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis() / 1000);

        PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
        ProductApprovalStatusDO productApprovalStatusDO = context.getBean(ProductApprovalStatusDO.class);
        ProductApprovingAuthorityDO productApprovingAuthorityDO = context.getBean(ProductApprovingAuthorityDO.class);
        ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);
        TaxationTypeDO taxationTypeDO = context.getBean(TaxationTypeDO.class);
        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        CountryDO countryDO = context.getBean(CountryDO.class);
        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);

        packTypeDO.setPackTypeCode(productDTO.getPackType());
        productDOInt.setPackType(packTypeDO);
        productApprovingAuthorityDO.setProductApprovingAuthorityCode(productDTO.getProductApprovingAuthority());
        productDOInt.setProductApprovingAuthority(productApprovingAuthorityDO);
        productApprovalStatusDO.setProductApprovalStatusCode(productDTO.getProductApprovalStatus());
        productDOInt.setProductApprovalStatus(productApprovalStatusDO);
        productDosageFormDO.setProductDosageCode(productDTO.getProductDosageForm());
        productDOInt.setProductDosageForm(productDosageFormDO);
        taxationTypeDO.setTaxationTypeCode(productDTO.getTaxationType());
        productDOInt.setTaxationType(taxationTypeDO);
        reviewStatusDO.setReviewCode("PE");
        productDOInt.setReviewStatus(reviewStatusDO);
        currencyDO.setCurrencyCode(productDTO.getCurrency());
        productDOInt.setCurrency(currencyDO);
        countryDO.setCountryCode(productDTO.getProductManufactureCountry());
        productDOInt.setProductManufactureCountry(countryDO);

        ProductDO_INT insertedObj = productIntRepo.save(productDOInt);

        //code for Audit table
        ProductAuditDO productAuditDO = context.getBean(ProductAuditDO.class);
        BeanUtils.copyProperties(productDOInt, productAuditDO);
        ReviewStatusDO reviewStatusAuditDO = context.getBean(ReviewStatusDO.class);
        reviewStatusAuditDO.setReviewCode("MD");

        productAuditDO.setReviewStatus(reviewStatusAuditDO);
        productAuditDO.setWorkFlowId(insertedObj.getWorkFlowId());
        productAuditDO.setLastUpdatedBy(productDOInt.getUserId());
        productAuditDO.setInitialAdditionDate(System.currentTimeMillis() / 1000);
        productAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis() / 1000);

        productAuditRepo.save(productAuditDO);

        return insertedObj.getWorkFlowId();
    }
}
