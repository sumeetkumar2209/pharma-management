package com.reify.product.service.impl;

import com.reify.common.DTO.*;
import com.reify.product.DTO.*;
import com.reify.product.model.ProductDO;
import com.reify.product.model.ProductDO_INT;
import com.reify.product.repo.ProductIntRepo;
import com.reify.product.repo.ProductRepo;
import com.reify.product.service.ProductSearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    ApplicationContext context;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProductIntRepo productIntRepo;

    @Override
    public List<ProductDTO> getProduct(ProductSearchDTO productSearchDTO) {

        List<ProductDO> productDOList= productRepo.searchProductByFilter(productSearchDTO);

        List<ProductDTO> productDTOList;

        if(!productDOList.isEmpty()){

            productDTOList = productDOList.stream().map( obj -> {

                ProductDTO productDTO = context.getBean(ProductDTO.class);
                BeanUtils.copyProperties(obj, productDTO);

                CountryDTO countryDTO = CountryDTO.builder()
                        .countryName(obj.getProductManufactureCountry().getCountryName())
                        .countryCode(obj.getProductManufactureCountry().getCountryCode())
                        .build();

                CurrencyDTO currencyDTO = CurrencyDTO.builder()
                        .currencyCode(obj.getCurrency().getCurrencyCode())
                        .currencyName(obj.getCurrency().getCurrencyName())
                        .build();

                PackTypeDTO packTypeDTO = PackTypeDTO.builder()
                        .packTypeCode(obj.getPackType().getPackTypeCode())
                        .packTypeName(obj.getPackType().getPackTypeName())
                        .build();

                ReviewStatusDTO reviewStatusDTO = ReviewStatusDTO.builder()
                        .reviewCode(obj.getReviewStatus().getReviewCode())
                        .reviewName(obj.getReviewStatus().getReviewName())
                        .build();

                ProductApprovalStatusDTO  productApprovalStatusDTO = ProductApprovalStatusDTO.builder()
                        .productApprovalStatusCode(obj.getProductApprovalStatus().getProductApprovalStatusCode())
                        .productApprovalStatusName(obj.getProductApprovalStatus().getProductApprovalStatusName())
                        .build();

                ProductDosageFormDTO productDosageFormDTO = ProductDosageFormDTO.builder()
                        .productDosageCode(obj.getProductDosageForm().getProductDosageCode())
                        .productDosageName(obj.getProductDosageForm().getGetProductDosageName())
                        .build();

                ProductApprovingAuthorityDTO  productApprovingAuthorityDTO = ProductApprovingAuthorityDTO.builder()
                        .productApprovingAuthorityCode(obj.getProductApprovingAuthority().getProductApprovingAuthorityCode())
                        .productApprovingAuthorityName(obj.getProductApprovingAuthority().getProductApprovingAuthorityName())
                        .build();

                TaxationTypeDTO  taxationTypeDTO = TaxationTypeDTO.builder()
                        .taxationTypeCode(obj.getTaxationType().getTaxationTypeCode())
                        .taxationTypeName(obj.getTaxationType().getTaxationTypeName())
                        .build();


                productDTO.setProductManufacturer(obj.getProductManufacturer().getCompanyName());
                productDTO.setProductManufactureCountry(countryDTO.getCountryCode());
                productDTO.setCurrency(currencyDTO.getCurrencyCode());
                productDTO.setProductApprovalStatus(productApprovalStatusDTO.getProductApprovalStatusCode());
                productDTO.setReviewStatus(reviewStatusDTO.getReviewCode());
                productDTO.setPackType(packTypeDTO.getPackTypeCode());
                productDTO.setProductDosageForm(productDosageFormDTO.getProductDosageCode());
                productDTO.setProductApprovingAuthority(productApprovingAuthorityDTO.getProductApprovingAuthorityCode());
                productDTO.setTaxationType(taxationTypeDTO.getTaxationTypeCode());



                return productDTO;

            }).collect(Collectors.toList());

            return productDTOList;
        }

        return null;
    }

    @Override
    public long getTotalProductCount(ProductSearchDTO productSearchDTO) {
        return productRepo.countProductByFilter(productSearchDTO);
    }

    @Override
    public List<ProductDTO> getProductBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        int size = inProgressWorkFlowDTO.getEndIndex() - inProgressWorkFlowDTO.getStartIndex();

        Pageable page = PageRequest.of(inProgressWorkFlowDTO.getStartIndex(), size, Sort.by("workFlowId"));

        List<ProductDO_INT> createdProduct = productIntRepo.findByUserId(inProgressWorkFlowDTO.getUserId(), page);

        List<ProductDO_INT> approveProduct = productIntRepo.findByApprover(inProgressWorkFlowDTO.getUserId(), page);

        List<ProductDTO> combinedList = new ArrayList<>();
        combinedList.addAll(convertDOtoDTO(createdProduct,"READ"));
        combinedList.addAll(convertDOtoDTO(approveProduct,"WRITE"));

        return combinedList;
    }

    private Collection<? extends ProductDTO> convertDOtoDTO(List<ProductDO_INT> list, String action) {

        List<ProductDTO>  productDTOList = list.stream().map( obj -> {

                ProductDTO productDTO = context.getBean(ProductDTO.class);
                BeanUtils.copyProperties(obj, productDTO);

                CountryDTO countryDTO = CountryDTO.builder()
                        .countryName(obj.getProductManufactureCountry().getCountryName())
                        .countryCode(obj.getProductManufactureCountry().getCountryCode())
                        .build();

                CurrencyDTO currencyDTO = CurrencyDTO.builder()
                        .currencyCode(obj.getCurrency().getCurrencyCode())
                        .currencyName(obj.getCurrency().getCurrencyName())
                        .build();

                PackTypeDTO packTypeDTO = PackTypeDTO.builder()
                        .packTypeCode(obj.getPackType().getPackTypeCode())
                        .packTypeName(obj.getPackType().getPackTypeName())
                        .build();

                ReviewStatusDTO reviewStatusDTO = ReviewStatusDTO.builder()
                        .reviewCode(obj.getReviewStatus().getReviewCode())
                        .reviewName(obj.getReviewStatus().getReviewName())
                        .build();

                ProductApprovalStatusDTO  productApprovalStatusDTO = ProductApprovalStatusDTO.builder()
                        .productApprovalStatusCode(obj.getProductApprovalStatus().getProductApprovalStatusCode())
                        .productApprovalStatusName(obj.getProductApprovalStatus().getProductApprovalStatusName())
                        .build();

                ProductDosageFormDTO productDosageFormDTO = ProductDosageFormDTO.builder()
                        .productDosageCode(obj.getProductDosageForm().getProductDosageCode())
                        .productDosageName(obj.getProductDosageForm().getGetProductDosageName())
                        .build();

                ProductApprovingAuthorityDTO  productApprovingAuthorityDTO = ProductApprovingAuthorityDTO.builder()
                        .productApprovingAuthorityCode(obj.getProductApprovingAuthority().getProductApprovingAuthorityCode())
                        .productApprovingAuthorityName(obj.getProductApprovingAuthority().getProductApprovingAuthorityName())
                        .build();

                TaxationTypeDTO  taxationTypeDTO = TaxationTypeDTO.builder()
                        .taxationTypeCode(obj.getTaxationType().getTaxationTypeCode())
                        .taxationTypeName(obj.getTaxationType().getTaxationTypeName())
                        .build();


                productDTO.setProductManufactureCountry(countryDTO.getCountryCode());
                productDTO.setCurrency(currencyDTO.getCurrencyCode());
                productDTO.setProductApprovalStatus(productApprovalStatusDTO.getProductApprovalStatusCode());
                productDTO.setReviewStatus(reviewStatusDTO.getReviewCode());
                productDTO.setPackType(packTypeDTO.getPackTypeCode());
                productDTO.setProductDosageForm(productDosageFormDTO.getProductDosageCode());
                productDTO.setProductApprovingAuthority(productApprovingAuthorityDTO.getProductApprovingAuthorityCode());
                productDTO.setTaxationType(taxationTypeDTO.getTaxationTypeCode());



                return productDTO;

            }).collect(Collectors.toList());

        return productDTOList;
    }

    @Override
    public long getProductCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {
        return productIntRepo.countByUserId(inProgressWorkFlowDTO.getUserId());
    }
}
