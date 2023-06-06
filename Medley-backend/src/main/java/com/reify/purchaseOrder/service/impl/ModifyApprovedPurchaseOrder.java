package com.reify.purchaseOrder.service.impl;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.ReviewStatusDO;
import com.reify.product.model.PackTypeDO;
import com.reify.product.model.ProductDosageFormDO;
import com.reify.purchaseOrder.DTO.PurchaseOrderDetailsRequestDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;
import com.reify.purchaseOrder.model.*;
import com.reify.purchaseOrder.repo.PurchaseOrderAuditRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderIntRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderRepo;
import com.reify.purchaseOrder.service.ModifyPurchaseOrder;
import com.reify.supplier.model.SupplierDO;
import com.reify.supplier.model.SupplierDO_INT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModifyApprovedPurchaseOrder implements ModifyPurchaseOrder {

    @Autowired
    PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    PurchaseOrderIntRepo purchaseOrderIntRepo;

    @Autowired
    PurchaseOrderAuditRepo purchaseOrderAuditRepo;

    @Autowired
    ApplicationContext context;

    @Override
    public void modifyPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<PurchaseOrderDO> purchaseOrderDoOpt =
                purchaseOrderRepo.findById(purchaseOrderRequestDTO.getPurchaseOrderId());

        if (!purchaseOrderDoOpt.isPresent()) {

            throw new RecordNotFoundException("record not found");
        }

        PurchaseOrderDO_INT purchaseOrderDOIntPending
                = purchaseOrderIntRepo.findByPurchaseOrderId(purchaseOrderRequestDTO.getPurchaseOrderId());
        if (purchaseOrderDOIntPending != null) {
            throw new InvalidStatusException("Already pending modification");
        }

        PurchaseOrderDO purchaseOrderDO = purchaseOrderDoOpt.get();

        if (!(purchaseOrderDO.getReviewStatus().getReviewCode().equalsIgnoreCase("AP"))) {

            throw new InvalidStatusException("Only Approved or Rejected Review Status can be modified");

        }

        PurchaseOrderDO_INT purchaseOrderDOInt = context.getBean(PurchaseOrderDO_INT.class);
        BeanUtils.copyProperties(purchaseOrderDO, purchaseOrderDOInt); //to check child table list copied or not

        //List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsListBefore = null;
        List<PurchaseOrderDetailsDO> purchaseOrderDetailsDO_intList
                = purchaseOrderDO.getPurchaseOrderDetailsList();

        List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsListBefore
                = purchaseOrderDO.getPurchaseOrderDetailsList().stream().map( obj ->{

            PurchaseOrderDetailsDO_INT purchaseOrderDetailsDOInt = context.getBean(PurchaseOrderDetailsDO_INT.class);
            BeanUtils.copyProperties(obj,purchaseOrderDetailsDOInt );

            PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
            ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);

            packTypeDO.setPackTypeCode(obj.getPackType().getPackTypeCode());
            purchaseOrderDetailsDOInt.setPackType(packTypeDO);

            productDosageFormDO.setProductDosageCode(obj.getProductDosageForm().getProductDosageCode());
            purchaseOrderDetailsDOInt.setProductDosageForm(productDosageFormDO);

            System.out.println(purchaseOrderDetailsDOInt.toString());


            return purchaseOrderDetailsDOInt;

        }).collect(Collectors.toList());

        purchaseOrderDOInt.setPurchaseOrderDetailsList(purchaseOrderDetailsListBefore);




        /*if (!purchaseOrderDO.getPurchaseOrderDetailsList().isEmpty()) {

            for (PurchaseOrderDetailsDO purchaseOrderDetailsDO : purchaseOrderDO.getPurchaseOrderDetailsList()) {

                PurchaseOrderDetailsDO_INT purchaseOrderDetailsDOInt = context.getBean(PurchaseOrderDetailsDO_INT.class);
                purchaseOrderDetailsDOInt.setPurchaseOrderAmt(purchaseOrderDetailsDO.getPurchaseOrderAmt());
                purchaseOrderDetailsDOInt.setPurchaseOrderQuantity(purchaseOrderDetailsDO.getPurchaseOrderQuantity());
                purchaseOrderDetailsDOInt.setPricePerPack(purchaseOrderDetailsDO.getPricePerPack());
                purchaseOrderDetailsDOInt.setProductName(purchaseOrderDetailsDO.getProductName());
                purchaseOrderDetailsDOInt.setLicenceNumber(purchaseOrderDetailsDO.getLicenceNumber());
                purchaseOrderDetailsDOInt.setStrength(purchaseOrderDetailsDO.getStrength());
                purchaseOrderDetailsDOInt.setTotalProductAmt(purchaseOrderDetailsDO.getTotalProductAmt());
                purchaseOrderDetailsDOInt.setPackSize(purchaseOrderDetailsDO.getPackSize());

                PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
                ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);

                packTypeDO.setPackTypeCode(purchaseOrderDetailsDO.getPackType().getPackTypeCode());
                purchaseOrderDetailsDOInt.setPackType(packTypeDO);

                productDosageFormDO.setProductDosageCode(purchaseOrderDetailsDO.getProductDosageForm().getProductDosageCode());
                purchaseOrderDetailsDOInt.setProductDosageForm(productDosageFormDO);

                purchaseOrderDetailsListBefore.add(purchaseOrderDetailsDOInt);


            }
        }*/

            //purchaseOrderDOInt.setPurchaseOrderDetailsList(purchaseOrderDetailsListBefore);
            SupplierDO supplierDO = context.getBean(SupplierDO.class);
            supplierDO.setSupplierId(purchaseOrderRequestDTO.getSupplierId());
            purchaseOrderDOInt.setSupplierId(supplierDO);
            purchaseOrderDOInt.setCreatedBy(purchaseOrderRequestDTO.getUserId());
            purchaseOrderDOInt.setApprover(purchaseOrderRequestDTO.getApprover());
            purchaseOrderDOInt.setComments(purchaseOrderRequestDTO.getComments());
            purchaseOrderDOInt.setTotalPoAmount(purchaseOrderRequestDTO.getTotalPoAmount());
            purchaseOrderDOInt.setPurchaseOrderNo(purchaseOrderRequestDTO.getPurchaseOrderNo());
            purchaseOrderDOInt.setWorkFlowId(purchaseOrderRequestDTO.getWorkFlowId());
            purchaseOrderDOInt.setUpdatedBy(purchaseOrderRequestDTO.getUserId());

            List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsList = null;

        List<PurchaseOrderDetailsRequestDTO> purchaseOrderDetailsRequestDTOS = purchaseOrderRequestDTO.getPurchaseItemList();

        List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsDOIntList = purchaseOrderDetailsRequestDTOS.stream().map( obj ->{

            PurchaseOrderDetailsDO_INT purchaseOrderDetailsDOInt = context.getBean(PurchaseOrderDetailsDO_INT.class);
            BeanUtils.copyProperties(obj,purchaseOrderDetailsDOInt );

            return purchaseOrderDetailsDOInt;

        }).collect(Collectors.toList());

        purchaseOrderDOInt.setPurchaseOrderDetailsList(purchaseOrderDetailsDOIntList);



           /* if (!purchaseOrderRequestDTO.getPurchaseItemList().isEmpty()) {

                for (PurchaseOrderDetailsRequestDTO purchaseOrderDetailsRequestDTO :
                        purchaseOrderRequestDTO.getPurchaseItemList()) {

                    PurchaseOrderDetailsDO_INT purchaseOrderDetailsDOInt = context.getBean(PurchaseOrderDetailsDO_INT.class);
                    purchaseOrderDetailsDOInt.setPurchaseOrderAmt(purchaseOrderDetailsRequestDTO.getPurchaseOrderAmt());
                    purchaseOrderDetailsDOInt.setPurchaseOrderQuantity(purchaseOrderDetailsRequestDTO.getPurchaseOrderQuantity());
                    purchaseOrderDetailsDOInt.setPricePerPack(purchaseOrderDetailsRequestDTO.getPricePerPack());
                    purchaseOrderDetailsDOInt.setProductName(purchaseOrderDetailsRequestDTO.getProductName());
                    purchaseOrderDetailsDOInt.setLicenceNumber(purchaseOrderDetailsRequestDTO.getLicenceNumber());
                    purchaseOrderDetailsDOInt.setStrength(purchaseOrderDetailsRequestDTO.getStrength());
                    purchaseOrderDetailsDOInt.setTotalProductAmt(purchaseOrderDetailsRequestDTO.getTotalProductAmt());
                    purchaseOrderDetailsDOInt.setPackSize(purchaseOrderDetailsRequestDTO.getPackSize());

                    PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
                    ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);

                    packTypeDO.setPackTypeCode(purchaseOrderDetailsRequestDTO.getPackType());
                    purchaseOrderDetailsDOInt.setPackType(packTypeDO);

                    productDosageFormDO.setProductDosageCode(purchaseOrderDetailsRequestDTO.getProductDosageForm());
                    purchaseOrderDetailsDOInt.setProductDosageForm(productDosageFormDO);

                    purchaseOrderDetailsList.add(purchaseOrderDetailsDOInt);


                }
                purchaseOrderDOInt.setPurchaseOrderDetailsList(purchaseOrderDetailsList);

            }*/

            ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
            reviewStatusDO.setReviewCode("PE");
            purchaseOrderDOInt.setReviewStatus(reviewStatusDO);

           PurchaseOrderDO_INT purchaseOrderDOIntInserted = purchaseOrderIntRepo.save(purchaseOrderDOInt);

           // code for audit table

        PurchaseOrderAuditDO purchaseOrderAuditDO = context.getBean(PurchaseOrderAuditDO.class);
        BeanUtils.copyProperties(purchaseOrderRequestDTO, purchaseOrderAuditDO);

        //List<PurchaseOrderDetailsAuditDO> purchaseOrderDetailsListAudit = null;

        //List<PurchaseOrderDetailsRequestDTO> purchaseOrderDetailsRequestDTOS = purchaseOrderRequestDTO.getPurchaseItemList();

        List<PurchaseOrderDetailsAuditDO> purchaseOrderDetailsDOAuditList = purchaseOrderDetailsRequestDTOS.stream().map( obj ->{

            PurchaseOrderDetailsAuditDO purchaseOrderDetailsAuditDO = context.getBean(PurchaseOrderDetailsAuditDO.class);
            BeanUtils.copyProperties(obj,purchaseOrderDetailsAuditDO );

            return purchaseOrderDetailsAuditDO;

        }).collect(Collectors.toList());

        purchaseOrderAuditDO.setPurchaseOrderDetailsList(purchaseOrderDetailsDOAuditList);

        /*if (!purchaseOrderRequestDTO.getPurchaseItemList().isEmpty()) {

            for (PurchaseOrderDetailsRequestDTO purchaseOrderDetailsRequestDTO : purchaseOrderRequestDTO.getPurchaseItemList()) {

                PurchaseOrderDetailsAuditDO purchaseOrderDetailsAuditDO = context.getBean(PurchaseOrderDetailsAuditDO.class);
                purchaseOrderDetailsAuditDO.setPurchaseOrderAmt(purchaseOrderDetailsRequestDTO.getPurchaseOrderAmt());
                purchaseOrderDetailsAuditDO.setPurchaseOrderQuantity(purchaseOrderDetailsRequestDTO.getPurchaseOrderQuantity());
                purchaseOrderDetailsAuditDO.setPricePerPack(purchaseOrderDetailsRequestDTO.getPricePerPack());
                purchaseOrderDetailsAuditDO.setProductName(purchaseOrderDetailsRequestDTO.getProductName());
                purchaseOrderDetailsAuditDO.setLicenceNumber(purchaseOrderDetailsRequestDTO.getLicenceNumber());
                purchaseOrderDetailsAuditDO.setStrength(purchaseOrderDetailsRequestDTO.getStrength());
                purchaseOrderDetailsAuditDO.setTotalProductAmt(purchaseOrderDetailsRequestDTO.getTotalProductAmt());
                purchaseOrderDetailsAuditDO.setPackSize(purchaseOrderDetailsRequestDTO.getPackSize());

                PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
                ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);

                packTypeDO.setPackTypeCode(purchaseOrderDetailsRequestDTO.getPackType());
                purchaseOrderDetailsAuditDO.setPackType(packTypeDO);

                productDosageFormDO.setProductDosageCode(purchaseOrderDetailsRequestDTO.getProductDosageForm());
                purchaseOrderDetailsAuditDO.setProductDosageForm(productDosageFormDO);

                purchaseOrderDetailsListAudit.add(purchaseOrderDetailsAuditDO);


            }
            purchaseOrderAuditDO.setPurchaseOrderDetailsList(purchaseOrderDetailsListAudit);

        }*/
        ReviewStatusDO reviewStatusAuditDO = context.getBean(ReviewStatusDO.class);
        reviewStatusAuditDO.setReviewCode("MD");
        purchaseOrderAuditDO.setReviewStatus(reviewStatusDO);

        purchaseOrderAuditDO.setWorkFlowId(purchaseOrderDOIntInserted.getWorkFlowId());
        purchaseOrderAuditDO.setInitialAdditionDate(System.currentTimeMillis()/1000);
        purchaseOrderAuditDO.setLastUpdatedDate(System.currentTimeMillis()/1000);
        purchaseOrderAuditDO.setUpdatedBy(purchaseOrderRequestDTO.getUserId());

        purchaseOrderAuditRepo.save(purchaseOrderAuditDO);

    }
}
