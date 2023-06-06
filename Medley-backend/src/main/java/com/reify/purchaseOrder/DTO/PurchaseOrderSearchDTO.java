package com.reify.purchaseOrder.DTO;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PurchaseOrderSearchDTO {

    private PurchaseOrderFilterDTO filter;

    private int startIndex;

    private int endIndex;

    private String orderBy;

    private String orderType;
}
