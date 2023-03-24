package com.reify.supplier.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reify.common.DTO.*;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Date;


@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"countryName","currencyName","initialAdditionDate",
"lastUpdatedBy","workFlowId","reviewStatus","action"})
public class SupplierDTO {

    private String workFlowId;

    private String supplierId;

    private String supplierStatus;
    private String companyName;
    private String contactName;
    private String contactNumber;
    private String contactEmail;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String town;
    private String country;
    private String postalCode;

    private String supplierQualificationStatus;
    private Date validTillDate;
    private String currency;
    private String approver;
    private String userId;
    private long initialAdditionDate;
    private String lastUpdatedBy;
    private long lastUpdatedTimeStamp;
    private String reviewStatus;

    private String comments;

    private String action;
}
