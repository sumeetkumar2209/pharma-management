package com.reify.supplier.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;


@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"countryName","currencyName","initialAdditionDate",
"lastUpdatedBy"})
public class SupplierDTO {

    private String supplierId;

    private SupplierStatusDTO supplierStatus;
    private String companyName;
    private String contactName;
    private String contactNumber;
    private String contactEmail;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String town;
    private CountryDTO country;
    private String postalCode;

    private SupplierQualificationStatusDTO supplierQualificationStatus;
    private Date validTillDate;
    private CurrencyDTO currency;
    private String approvedBy;
    private String userId;
    private long initialAdditionDate;
    private String lastUpdatedBy;
    private long lastUpdatedTimeStamp;
    private ReviewStatusDTO reviewStatus;

    private String option;


}
