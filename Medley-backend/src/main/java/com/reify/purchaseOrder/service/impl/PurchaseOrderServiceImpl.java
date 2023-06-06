package com.reify.purchaseOrder.service.impl;

import com.reify.common.DTO.ApproveRejectDTO;
import com.reify.common.exception.InvalidStatusException;
import com.reify.common.exception.RecordNotFoundException;
import com.reify.common.model.ReviewStatusDO;
import com.reify.product.model.PackTypeDO;
import com.reify.product.model.ProductDosageFormDO;
import com.reify.purchaseOrder.DTO.PurchaseOrderRequestDTO;
import com.reify.purchaseOrder.factory.ModifyPurchaseOrderFactory;
import com.reify.purchaseOrder.model.*;
import com.reify.purchaseOrder.repo.PurchaseOrderAuditRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderIntRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderRepo;
import com.reify.purchaseOrder.service.ModifyPurchaseOrder;
import com.reify.purchaseOrder.service.PurchaseOrderService;
import com.reify.supplier.model.SupplierDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderIntRepo purchaseOrderIntRepo;

    @Autowired
    ApplicationContext context;

    @Autowired
    PurchaseOrderAuditRepo purchaseOrderAuditRepo;

    @Autowired
    PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    ModifyPurchaseOrderFactory modifyPurchaseOrderFactory;

    @Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderIntRepo purchaseOrderIntRepo, ApplicationContext context){
        this.purchaseOrderIntRepo = purchaseOrderIntRepo;
        this.context = context;
    }

    @Override
    public String createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) {

        PurchaseOrderDO_INT purchaseOrderDOInt = context.getBean(PurchaseOrderDO_INT.class);

        BeanUtils.copyProperties(purchaseOrderRequestDTO, purchaseOrderDOInt);

        SupplierDO supplierDO = context.getBean(SupplierDO.class);
        supplierDO.setSupplierId(purchaseOrderRequestDTO.getSupplierId());

        purchaseOrderDOInt.setSupplierId(supplierDO);
        purchaseOrderDOInt.setCreatedTimeStamp(System.currentTimeMillis() /1000);
        purchaseOrderDOInt.setCreatedBy(purchaseOrderRequestDTO.getUserId());
        purchaseOrderDOInt.setLastUpdatedDate(System.currentTimeMillis() / 1000);
        purchaseOrderDOInt.setUpdatedBy(purchaseOrderRequestDTO.getUserId());

        ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);

        if (purchaseOrderRequestDTO.getOption() != null &&
                purchaseOrderRequestDTO.getOption().equalsIgnoreCase("DRAFT")) {
            reviewStatusDO.setReviewCode("DR");
        } else {
            reviewStatusDO.setReviewCode("PE");
        }

        purchaseOrderDOInt.setReviewStatus(reviewStatusDO);

       List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsDOList = purchaseOrderRequestDTO.getPurchaseItemList().stream().map(obj -> {

            PurchaseOrderDetailsDO_INT purchaseOrderDetailsDOInt = context.getBean(PurchaseOrderDetailsDO_INT.class);
            BeanUtils.copyProperties(obj,purchaseOrderDetailsDOInt);

           PackTypeDO packTypeDO = context.getBean(PackTypeDO.class);
           ProductDosageFormDO productDosageFormDO = context.getBean(ProductDosageFormDO.class);

           packTypeDO.setPackTypeCode(obj.getPackType());
           purchaseOrderDetailsDOInt.setPackType(packTypeDO);

           productDosageFormDO.setProductDosageCode(obj.getProductDosageForm());
           purchaseOrderDetailsDOInt.setProductDosageForm(productDosageFormDO);

           return purchaseOrderDetailsDOInt;

        }).collect(Collectors.toList());

        purchaseOrderDOInt.setPurchaseOrderDetailsList(purchaseOrderDetailsDOList);

        //PurchaseOrderDO_INT purchaseOrderDOIntInserted=purchaseOrderIntRepo.save(purchaseOrderDOInt);

        //auditing

       List<PurchaseOrderDetailsAuditDO> purchaseOrderDetailsAuditDOList = purchaseOrderDetailsDOList.stream().map(obj -> {
            PurchaseOrderDetailsAuditDO purchaseOrderDetailsAuditDO = context.getBean(PurchaseOrderDetailsAuditDO.class);
            BeanUtils.copyProperties(obj, purchaseOrderDetailsAuditDO);
            return purchaseOrderDetailsAuditDO;
        }).collect(Collectors.toList());


        PurchaseOrderAuditDO purchaseOrderAuditDO = context.getBean(PurchaseOrderAuditDO.class);

        BeanUtils.copyProperties(purchaseOrderDOInt, purchaseOrderAuditDO);
        purchaseOrderAuditDO.setPurchaseOrderDetailsList(purchaseOrderDetailsAuditDOList);

        PurchaseOrderDO_INT purchaseOrderDOIntInserted= purchaseOrderIntRepo.save(purchaseOrderDOInt);
        purchaseOrderAuditRepo.save(purchaseOrderAuditDO);

        return purchaseOrderDOIntInserted.getWorkFlowId();
    }

    @Override
    public String approveRejectPurchaseOrder(ApproveRejectDTO approveRejectDTO) {

       Optional<PurchaseOrderDO_INT> purchaseOrderDOInt =
               purchaseOrderIntRepo.findById(approveRejectDTO.getWorkFlowId());

       if (purchaseOrderDOInt.isPresent()) {

           PurchaseOrderDO_INT purchaseOrderDO_INT = purchaseOrderDOInt.get();

           if (purchaseOrderDO_INT.getReviewStatus().getReviewCode().equalsIgnoreCase("PE")) {
               PurchaseOrderDO purchaseOrderDO = context.getBean(PurchaseOrderDO.class);
               BeanUtils.copyProperties(purchaseOrderDO_INT, purchaseOrderDO);

               List<PurchaseOrderDetailsDO_INT> purchaseOrderDetailsDO_intList = purchaseOrderDO_INT.getPurchaseOrderDetailsList();

               List<PurchaseOrderDetailsDO> purchaseOrderDetailsDOList = purchaseOrderDetailsDO_intList.stream().map( obj ->{

                   PurchaseOrderDetailsDO purchaseOrderDetailsDO = context.getBean(PurchaseOrderDetailsDO.class);
                   BeanUtils.copyProperties(obj,purchaseOrderDetailsDO );

                   return purchaseOrderDetailsDO;

               }).collect(Collectors.toList());


               ReviewStatusDO reviewStatusDO = context.getBean(ReviewStatusDO.class);
               PurchaseOrderDO purchaseOrderDOInserted = null;
               if(approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {

                   reviewStatusDO.setReviewCode("AP");
                   purchaseOrderDO.setReviewStatus(reviewStatusDO);
                   purchaseOrderDO.setPurchaseOrderDetailsList(purchaseOrderDetailsDOList);
                   purchaseOrderDO.setLastUpdatedDate(System.currentTimeMillis()/1000);
                   purchaseOrderDO.setUpdatedBy(purchaseOrderDO_INT.getApprover());
                   purchaseOrderDO.setComments(approveRejectDTO.getComments());

                   purchaseOrderDOInserted = purchaseOrderRepo.save(purchaseOrderDO);

               } else {
                   reviewStatusDO.setReviewCode("RE");
               }

               PurchaseOrderAuditDO purchaseOrderAuditDO =
                       context.getBean(PurchaseOrderAuditDO.class);

               BeanUtils.copyProperties(purchaseOrderDO, purchaseOrderAuditDO);

               purchaseOrderAuditDO.setReviewStatus(reviewStatusDO);
               purchaseOrderAuditDO.setInitialAdditionDate(System.currentTimeMillis() / 1000);
               purchaseOrderAuditDO.setLastUpdatedDate(System.currentTimeMillis() / 1000);
               purchaseOrderAuditDO.setWorkFlowId(approveRejectDTO.getWorkFlowId());
               purchaseOrderAuditDO.setComments(approveRejectDTO.getComments());
               purchaseOrderAuditDO.setUpdatedBy(purchaseOrderDO.getApprover());

               purchaseOrderAuditRepo.saveAndFlush(purchaseOrderAuditDO);


               if (approveRejectDTO.getDecision().equalsIgnoreCase("AP")) {
                   purchaseOrderIntRepo.deleteById(approveRejectDTO.getWorkFlowId());
               } else {
                   purchaseOrderDO_INT.setReviewStatus(reviewStatusDO);
                   purchaseOrderDO_INT.setLastUpdatedDate(System.currentTimeMillis() / 1000);
                   purchaseOrderDO_INT.setUpdatedBy(purchaseOrderDO_INT.getApprover());
                   purchaseOrderDO_INT.setComments(approveRejectDTO.getComments());
                    purchaseOrderIntRepo.save(purchaseOrderDO_INT);
                   return approveRejectDTO.getWorkFlowId();
               }
               return purchaseOrderDOInserted.getPurchaseOrderNo();



           }
       }
        return null;
    }

    @Override
    public void modifyPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws RecordNotFoundException, InvalidStatusException {

        Optional<PurchaseOrderDO> purchaseOrderIdOpt =
                purchaseOrderRepo.findById(purchaseOrderRequestDTO.getPurchaseOrderId());
        PurchaseOrderDO_INT purchaseOrderDOIntPending =
                purchaseOrderIntRepo.findByPurchaseOrderId(purchaseOrderRequestDTO.getPurchaseOrderId());

        boolean main = false;
        boolean stage = false;

        if (purchaseOrderIdOpt.isPresent() &&
                purchaseOrderIdOpt.get().getReviewStatus().getReviewCode().equalsIgnoreCase("AP")) {
            main = true;
        }

        if (purchaseOrderDOIntPending != null &&
                purchaseOrderDOIntPending.getReviewStatus().getReviewCode().equalsIgnoreCase("RE")) {
            stage = true;
        }

        ModifyPurchaseOrder modifyPurchaseOrder = modifyPurchaseOrderFactory.getInstanceName(main, stage);
        modifyPurchaseOrder.modifyPurchaseOrder(purchaseOrderRequestDTO);

    }
}
