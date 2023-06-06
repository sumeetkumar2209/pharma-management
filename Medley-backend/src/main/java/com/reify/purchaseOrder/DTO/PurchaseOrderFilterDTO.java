package com.reify.purchaseOrder.DTO;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PurchaseOrderFilterDTO {

    private String purchaseOrderNo;

    private String supplierName;

    private long validity;

    private long purchaseOrderDate;

}
