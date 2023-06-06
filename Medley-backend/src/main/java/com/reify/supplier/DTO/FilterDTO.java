package com.reify.supplier.DTO;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilterDTO {

    private String supplierId;
    private String companyName;
    private String supplierStatus;
    private String postalCode;
    private String reviewStatus;

}
