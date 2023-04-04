package com.reify.supplier.factory;

import com.reify.supplier.service.ModifySupplier;
import com.reify.supplier.service.impl.ModifyApprovedSupplier;
import com.reify.supplier.service.impl.ModifyRejectedSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/*
* main table status: AP && stage table no record :=> T F
* main table no record && stage table status:RE :=> F T
* main table status: AP && stage table status:RE :=> T T
*
* */

@Component
public class ModifySupplierFactory {

    @Autowired
    ApplicationContext context;

    public ModifySupplier getInstanceName(boolean main, boolean stage) {

        if (main && !stage) {
            return context.getBean(ModifyApprovedSupplier.class);
        } else if (!main && stage) {
            return context.getBean(ModifyRejectedSupplier.class);
        } else if (main && stage) {
            return context.getBean(ModifyRejectedSupplier.class);
        }

        return null;

    }
}
