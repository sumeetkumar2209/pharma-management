package com.reify.customer.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reify.common.model.CountryDO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.model.ReviewStatusDO;
import com.reify.customer.model.CustomerQualificationStatusDO;
import com.reify.customer.model.CustomerStatusDO;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"countryName","currencyName","initialAdditionDate",
        "lastUpdatedByUser"})
public class CustomerDTO {

    private String customerId;

    private CustomerStatusDO customerStatus;
    private String customerName;
    private String contactName;
    private String contactNumber;
    private String contactEmail;
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String town;
    private CountryDO countryDO;

    private CurrencyDO currencyDO;
    private String postalCode;
    private CustomerQualificationStatusDO customerQualificationStatusDO;

    private long validTill;

    private String approvedBy;

    private String userId;

    private long initialAdditionDate;

    private String lastUpdatedBy;
    private ReviewStatusDO reviewStatus;

}
