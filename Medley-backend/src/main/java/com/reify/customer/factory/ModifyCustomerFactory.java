package com.reify.customer.factory;

import com.reify.customer.service.ModifyCustomer;
import com.reify.customer.service.impl.ModifyApprovedCustomer;
import com.reify.customer.service.impl.ModifyRejectedCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ModifyCustomerFactory {

    @Autowired
    ApplicationContext context;

    public ModifyCustomer getInstanceName(boolean main, boolean stage) {

        if (main && !stage) {
            return context.getBean(ModifyApprovedCustomer.class);
        } else if (!main && stage) {
            return context.getBean(ModifyRejectedCustomer.class);
        } else if (main && stage) {
            return context.getBean(ModifyRejectedCustomer.class);
        }

        return null;

    }


}
