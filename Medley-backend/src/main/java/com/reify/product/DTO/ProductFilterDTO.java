package com.reify.product.DTO;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProductFilterDTO {

    private String productId;
    private String productName;
    private String packType;
    private String reviewStatus;
    private String ProductApprovalStatus;


}
