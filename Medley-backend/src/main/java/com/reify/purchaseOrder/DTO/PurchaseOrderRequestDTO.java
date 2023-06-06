package com.reify.purchaseOrder.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"purchaseOrderDetailId"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderRequestDTO {

    private String workFlowId;

    private String purchaseOrderId;

    private String purchaseOrderNo;

    private String supplierId;

    private long validity;

    private double totalPoAmount;

    private String userId;

    private List<PurchaseOrderDetailsRequestDTO> purchaseItemList;

    private String option;

    private String approver;

    private String comments;

    private long purchaseOrderDate;

    private String action;
}
