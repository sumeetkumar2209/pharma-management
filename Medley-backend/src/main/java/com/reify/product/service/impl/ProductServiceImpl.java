package com.reify.product.service.impl;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.AlreadyExistException;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.CountryDO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.model.ReviewStatusDO;
import com.reify.customer.model.CustomerDO;
import com.reify.customer.model.CustomerDO_INT;
import com.reify.customer.service.ModifyCustomer;
import com.reify.product.DTO.ProductDTO;
import com.reify.product.factory.ModifyProductFactory;
import com.reify.product.model.*;
import com.reify.product.repo.ProductAuditRepo;
import com.reify.product.repo.ProductIntRepo;
import com.reify.product.repo.ProductRepo;
import com.reify.product.service.ModifyProduct;
import com.reify.product.service.ProductService;
import com.reify.supplier.model.SupplierDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductIntRepo productIntRepo;

    @Autowired
    ApplicationContext context;
    @Autowired
    ProductAuditRepo productAuditRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ModifyProductFactory modifyProductFactory;

    @Transactional
    @Override
    public String addProduct(ProductDTO productDTO) throws AlreadyExistException {

        Optional<ProductDO> productDO = productRepo.findById(productDTO.getProductId());

        if (productDO.isPresent()) {

            throw new AlreadyExistException("Product already added");
        }

        ProductDO_INT productDOInt = context.getBean(ProductDO_INT.class);
        PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
        ProductApprovalStatusDO productApprovalStatusDO = context.getBean(ProductApprovalStatusDO.class);
        ProductApprovingAuthorityDO productApprovingAuthorityDO = context.getBean(ProductApprovingAuthorityDO.class);
        ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);
        TaxationTypeDO taxationTypeDO = context.getBean(TaxationTypeDO.class);
        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        CountryDO countryDO = context.getBean(CountryDO.class);
        CurrencyDO currencyDO = context.getBean(CurrencyDO.class);

        SupplierDO supplierDO = context.getBean(SupplierDO.class);
        supplierDO.setSupplierId(productDTO.getProductManufacturer());
        productDOInt.setProductManufacturer(supplierDO);

        BeanUtils.copyProperties(productDTO, productDOInt);
        try {
            productDOInt.setProductPhoto(productDTO.getMultipartFile().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        productDOInt.setInitialAdditionDate(System.currentTimeMillis() / 1000);
        productDOInt.setLastUpdatedBy(productDTO.getUserId());
        productDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis() / 1000);

        ProductDO_INT insertedObj = productIntRepo.save(productDOInt);

        ProductAuditDO productAuditDO = context.getBean(ProductAuditDO.class);
        BeanUtils.copyProperties(productDOInt, productAuditDO);
        productAuditDO.setWorkFlowId(insertedObj.getWorkFlowId());

        productAuditRepo.save(productAuditDO);

        return insertedObj.getWorkFlowId();

    }

    @Override
    public String modifyProduct(ProductDTO productDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<ProductDO> ProductDOOptional = productRepo.findById(productDTO.getProductId());

        ProductDO_INT productDOInt = productIntRepo.findByProductId(productDTO.getProductId());

        boolean main = false;
        boolean stage = false;

        if(ProductDOOptional.isPresent() && ProductDOOptional.get().getReviewStatus()
                .getReviewCode().equalsIgnoreCase("AP")){
            main = true;
        }
        if(productDOInt != null && productDOInt.getReviewStatus().getReviewCode()
                .equalsIgnoreCase("RE")){
            stage = true;
        }

        ModifyProduct modifyProduct = modifyProductFactory.getInstanceName(main, stage);
       String workFlowId = modifyProduct.modifyProduct(productDTO);

        return workFlowId;
    }

    @Transactional
    @Override
    public String approveRejectProduct(ApproveRejectDTO approveRejectDTO) {

        Optional<ProductDO_INT> productDOIntOpt = productIntRepo.findById(approveRejectDTO.getWorkFlowId());


        if (productDOIntOpt.isPresent()) {

            ProductDO_INT productDOInt = productDOIntOpt.get();

            if (productDOInt.getReviewStatus().getReviewCode().equalsIgnoreCase("PE")) {

                ProductDO productDO = context.getBean(ProductDO.class);
                BeanUtils.copyProperties(productDOInt, productDO);
                ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
                ProductDO productDOInserted = null;

                if (approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {

                    reviewStatusDO.setReviewCode("AP");
                    productDO.setReviewStatus(reviewStatusDO);
                    productDO.setLastUpdatedTimeStamp(System.currentTimeMillis() / 1000);
                    productDO.setLastUpdatedBy(productDOInt.getApprover());
                    productDO.setComments(approveRejectDTO.getComments());

                    productDOInserted = productRepo.save(productDO);

                } else {
                    reviewStatusDO.setReviewCode("RE");
                }

                //code for audit table
                ProductAuditDO productAuditDO = context.getBean(ProductAuditDO.class);
                BeanUtils.copyProperties(productDO, productAuditDO);

                productAuditDO.setReviewStatus(reviewStatusDO);
                productAuditDO.setInitialAdditionDate(System.currentTimeMillis() / 1000);
                productAuditDO.setLastUpdatedTimeStamp(System.currentTimeMillis() / 1000);
                productAuditDO.setWorkFlowId(approveRejectDTO.getWorkFlowId());
                productAuditDO.setComments(approveRejectDTO.getComments());
                productAuditDO.setLastUpdatedBy(productAuditDO.getApprover());

                productAuditRepo.saveAndFlush(productAuditDO);

                if (approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {
                    productIntRepo.deleteById(approveRejectDTO.getWorkFlowId());
                } else {
                    productDOInt.setReviewStatus(reviewStatusDO);
                    productDOInt.setLastUpdatedTimeStamp(System.currentTimeMillis() / 1000);
                    productDOInt.setLastUpdatedBy(productDOInt.getApprover());
                    productDOInt.setComments(approveRejectDTO.getComments());
                    productIntRepo.save(productDOInt);
                    return approveRejectDTO.getWorkFlowId();
                }
                return productDOInserted.getProductId();
            }
        }
        return  null;

    }
}
