package com.reify.customer.model;

import com.reify.common.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "CUSTOMER_AUDIT")
public class CustomerAuditDO implements Serializable {

    @Id
    @GeneratedValue
    private int customerAuditId;

    private String workFlowId;

    @Column(length = 9)
    private String customerId;

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
