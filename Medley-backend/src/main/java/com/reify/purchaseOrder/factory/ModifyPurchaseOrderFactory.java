package com.reify.purchaseOrder.factory;

import com.reify.purchaseOrder.service.ModifyPurchaseOrder;
import com.reify.purchaseOrder.service.impl.ModifyApprovedPurchaseOrder;
import com.reify.purchaseOrder.service.impl.ModifyRejectedPurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ModifyPurchaseOrderFactory {

    @Autowired
    ApplicationContext context;

    public ModifyPurchaseOrder getInstanceName(boolean main, boolean stage) {

        if (main && !stage) {
            return context.getBean(ModifyApprovedPurchaseOrder.class);
        } else if (!main && stage) {
            return context.getBean(ModifyRejectedPurchaseOrder.class);
        } else if (main && stage) {
            return context.getBean(ModifyRejectedPurchaseOrder.class);
        }

        return null;

    }
}
