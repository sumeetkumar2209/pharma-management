package com.reify.customer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reify.common.DTO.*;
import com.reify.common.model.*;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"countryName","currencyName","initialAdditionDate",
        "lastUpdatedByUser","action","reviewStatus","workFlowId"})
public class CustomerDTO {

    private String customerId;
    private String workFlowId;
    private String customerStatus;
    private String customerName;
    private String contactName;
    private String contactNumber;
    private String contactEmail;
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String town;
    private String country;

    private String currency;
    private String postalCode;
    private String customerQualificationStatus;

    private Date validTillDate;

    private String approver;

    private String userId;

    private long initialAdditionDate;

    private String lastUpdatedBy;

    private long lastUpdatedTimeStamp;
    private String reviewStatus;

    private String action;

    private String comments;


}
