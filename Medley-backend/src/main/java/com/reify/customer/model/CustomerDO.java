package com.reify.customer.model;

import com.reify.common.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "CUSTOMER")
public class CustomerDO {
    @Id
    @Column(length = 9)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @GenericGenerator(name = "customer_seq",
            strategy = "com.reify.common.helper.CustomIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefix", value = "CUST"),
                    @org.hibernate.annotations.Parameter(name = "seqName", value = "customer_seq"),
                    @org.hibernate.annotations.Parameter(name = "seqLength", value = "5")
            })
    private String customerId;

    private String workFlowId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "customerStatusCode")
    private StatusDO customerStatus;

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
    private CountryDO country;

    @ManyToOne
    @JoinColumn(name = "currencyCode")
    private CurrencyDO currency;

    @Column(length = 15)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "customerQfCode")
    private QualificationStatusDO customerQualificationStatus;

    @Column(length = 10)
    private long validTill;
    @Column(length = 50)
    private String approver;
    @Column(length = 50)
    private String userId;

    private long initialAdditionDate;
    @Column(length = 50)
    private String lastUpdatedBy;

    private long lastUpdatedTimeStamp;

    @ManyToOne
    @JoinColumn(name = "reviewCode")
    private ReviewStatusDO reviewStatus;

    private String comments;
}
