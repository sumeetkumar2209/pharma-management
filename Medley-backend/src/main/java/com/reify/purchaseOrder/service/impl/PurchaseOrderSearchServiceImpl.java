package com.reify.purchaseOrder.service.impl;

import com.reify.common.DTO.InProgressWorkFlowDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderResponseDTO;
import com.reify.purchaseOrder.DTO.PurchaseOrderSearchDTO;
import com.reify.purchaseOrder.model.PurchaseOrderDO;
import com.reify.purchaseOrder.model.PurchaseOrderDO_INT;
import com.reify.purchaseOrder.repo.PurchaseOrderIntRepo;
import com.reify.purchaseOrder.repo.PurchaseOrderRepo;
import com.reify.purchaseOrder.service.PurchaseOrderSearchService;
import com.reify.supplier.DTO.SupplierDTO;
import com.reify.supplier.model.SupplierDO_INT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderSearchServiceImpl implements PurchaseOrderSearchService {

    @Autowired
    PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    PurchaseOrderIntRepo purchaseOrderIntRepo;

    @Autowired
    ApplicationContext context;

    @Override
    public List<PurchaseOrderResponseDTO> getPurchaseOrder(PurchaseOrderSearchDTO purchaseOrderSearchDTO) {

       List<PurchaseOrderDO> purchaseOrderDOList = purchaseOrderRepo.searchPurchaseOrderByFilter(purchaseOrderSearchDTO);

        List<PurchaseOrderResponseDTO> purchaseOrderResponseDTOList;

        if(!purchaseOrderDOList.isEmpty()){

            purchaseOrderResponseDTOList = purchaseOrderDOList.stream().map( obj -> {

                PurchaseOrderResponseDTO purchaseOrderResponseDTO = context.getBean(PurchaseOrderResponseDTO.class);
                BeanUtils.copyProperties(obj, purchaseOrderResponseDTO);


                return purchaseOrderResponseDTO;

            }).collect(Collectors.toList());

            return purchaseOrderResponseDTOList;
        }

        return null;
    }

    @Override
    public long getTotalPurchaseOrderCount(PurchaseOrderSearchDTO purchaseOrderSearchDTO) {

        return purchaseOrderRepo.countPurchaseOrderByFilter(purchaseOrderSearchDTO);

    }

    @Override
    public List<PurchaseOrderResponseDTO> getPurchaseOrderBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        int size = inProgressWorkFlowDTO.getEndIndex() - inProgressWorkFlowDTO.getStartIndex();

        Pageable page = PageRequest.of(inProgressWorkFlowDTO.getStartIndex(), size, Sort.by("workFlowId"));

        List<PurchaseOrderDO_INT> createdSupplier = purchaseOrderIntRepo.findByUserId(inProgressWorkFlowDTO.getUserId(), page);

        List<PurchaseOrderDO_INT> approveSupplier = purchaseOrderIntRepo.findByApprover(inProgressWorkFlowDTO.getUserId(), page);

        List<PurchaseOrderResponseDTO> combinedList = new ArrayList<>();
        combinedList.addAll(convertDOtoDTO(createdSupplier,"READ"));
        combinedList.addAll(convertDOtoDTO(approveSupplier,"WRITE"));

        return combinedList;

    }

    private List<PurchaseOrderResponseDTO> convertDOtoDTO(List<PurchaseOrderDO_INT> list, String action) {

        List<PurchaseOrderResponseDTO> supplierDTOList = list.stream().map(obj -> {

            PurchaseOrderResponseDTO purchaseOrderResponseDTO = context.getBean(PurchaseOrderResponseDTO.class);
            BeanUtils.copyProperties(obj, purchaseOrderResponseDTO);

            purchaseOrderResponseDTO.setAction(action);


            return purchaseOrderResponseDTO;

        }).collect(Collectors.toList());

        return supplierDTOList;
    }

    @Override
    public long getPurchaseOrderCountBasedOnUser(InProgressWorkFlowDTO inProgressWorkFlowDTO) {

        return purchaseOrderIntRepo.countByUserId(inProgressWorkFlowDTO.getUserId());

    }
}
