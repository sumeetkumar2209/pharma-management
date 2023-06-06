package com.reify.product.factory;

import com.reify.product.service.ModifyProduct;
import com.reify.product.service.impl.ModifyApprovedProduct;
import com.reify.product.service.impl.ModifyRejectedProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ModifyProductFactory {

    @Autowired
    ApplicationContext context;

    public ModifyProduct getInstanceName(boolean main, boolean stage) {

        if (main && !stage) {
            return context.getBean(ModifyApprovedProduct.class);
        } else if (!main && stage) {
            return context.getBean(ModifyRejectedProduct.class);
        } else if (main && stage) {
            return context.getBean(ModifyRejectedProduct.class);
        }

        return null;

    }
}
