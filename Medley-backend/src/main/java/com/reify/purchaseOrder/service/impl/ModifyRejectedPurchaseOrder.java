package com.reify.purchaseOrder.service.impl;

import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.ReviewStatusDO;
import com.reify.product.model.PackTypeDO;
import com.reify.product.model.ProductDosageFormDO;
import com.reify.purchaseOrder.DTO.PurchaseOrderDetailsRequestDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;
import com.reify.purchaseOrder.model.PurchaseOrderAuditDO;
import com.reify.purchaseOrder.model.PurchaseOrderDO_INT;
import com.reify.purchaseOrder.model.PurchaseOrderDetailsAuditDO;
import com.reify.purchaseOrder.model.PurchaseOrderDetailsDO_INT;
import com.reify.purchaseOrder.repo.PurchaseOrderAuditRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderIntRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderRepo;
import com.reify.purchaseOrder.service.ModifyPurchaseOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModifyRejectedPurchaseOrder implements ModifyPurchaseOrder {

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


        PurchaseOrderDO_INT purchaseOrderDOIntPending = purchaseOrderIntRepo.findByPurchaseOrderId(purchaseOrderRequestDTO.getSupplierId());
        if (purchaseOrderDOIntPending != null &&
                !purchaseOrderDOIntPending.getReviewStatus().getReviewCode().equalsIgnoreCase("RE")) {
            throw new InvalidStatusException("Already pending modification");
        } else if (purchaseOrderDOIntPending == null) {
            throw new RecordNotFoundException("Record not found error");
        }


        PurchaseOrderDO_INT purchaseOrderDOInt = context.getBean(PurchaseOrderDO_INT.class);
        BeanUtils.copyProperties(purchaseOrderDOIntPending, purchaseOrderDOInt);

        List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsList = null;

        if (!purchaseOrderDOIntPending.getPurchaseOrderDetailsList().isEmpty()) {

            for (PurchaseOrderDetailsRequestDTO purchaseOrderDetailsRequestDTO : purchaseOrderRequestDTO.getPurchaseItemList()) {

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
        }

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
        reviewStatusDO.setReviewCode("PE");
        purchaseOrderDOInt.setReviewStatus(reviewStatusDO);

        PurchaseOrderDO_INT purchaseOrderDOIntInserted = purchaseOrderIntRepo.save(purchaseOrderDOInt);

        // code for audit table

        PurchaseOrderAuditDO purchaseOrderAuditDO = context.getBean(PurchaseOrderAuditDO.class);
        BeanUtils.copyProperties(purchaseOrderRequestDTO, purchaseOrderAuditDO);

        List<PurchaseOrderDetailsAuditDO> purchaseOrderDetailsListAudit = null;

        if (!purchaseOrderRequestDTO.getPurchaseItemList().isEmpty()) {

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

        }
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
