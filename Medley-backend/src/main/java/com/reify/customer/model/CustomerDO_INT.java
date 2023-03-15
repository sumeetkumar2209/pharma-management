package com.reify.customer.model;


import com.reify.common.model.CountryDO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.model.ReviewStatusDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "INT_CUSTOMER")
public class CustomerDO_INT implements Serializable {

    @Id
    @Column(length = 8)
    private String customerId;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customerStatusCode")
    private CustomerStatusDO customerStatus;

    @Column(length = 100)
    private String customerName;

    @Column(length = 100)
    private String contactName;
    @Column(length = 15)
    private String contactNumber;

    @Column(length = 50)
    private String contactEmail;
    @Column(length = 50)
    private String addressLine1;
    @Column(length = 50)
    private String addressLine2;
    @Column(length = 50)
    private String addressLine3;
    @Column(length = 30)
    private String town;
    @ManyToOne
    @JoinColumn(name = "countryCode")
    private CountryDO countryDO;

    @ManyToOne
    @JoinColumn(name = "currencyCode")
    private CurrencyDO currencyDO;

    @Column(length = 15)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "customerQfCode")
    private CustomerQualificationStatusDO customerQualificationStatusDO;

    @Column(length = 10)
    private long validTill;
    @Column(length = 50)
    private String approvedBy;
    @Column(length = 50)
    private String userId;

    private long initialAdditionDate;
    @Column(length = 50)
    private String lastUpdatedBy;

    @ManyToOne
    @JoinColumn(name = "reviewCode")
    private ReviewStatusDO reviewStatus;

    private String option;



}
