package com.reify.product.model;

import com.reify.common.model.CountryDO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.model.ReviewStatusDO;
import com.reify.supplier.model.SupplierDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "PRODUCT")
public class ProductDO {

    private String workFlowId;


    @Id
    @Column(length = 20)
    private String productId;

    @Column(length = 50)
    private String productName;
    @Column(length = 30)
    private String licenceNumber;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "productDosageCode")
    private ProductDosageFormDO productDosageForm;
    @Column(length = 10)
    private String strength;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "packTypeCode")
    private PackTypeDO packType;
    @Column(length = 6)
    private int packSize;
    @ManyToOne
    @JoinColumn(name = "countryCode")
    private CountryDO productManufactureCountry;


    @ManyToOne
    @JoinColumn(name = "productApprovalStatusCode")
    private ProductApprovalStatusDO productApprovalStatus;
    @ManyToOne
    @JoinColumn(name = "productApprovingAuthorityCode")
    private ProductApprovingAuthorityDO productApprovingAuthority;

    @Column(precision =10, scale = 2)
    private double pricePerPack;

    @ManyToOne
    @JoinColumn(name = "currencyCode")
    private CurrencyDO currency;
    @Column(precision =10, scale = 2)
    private double taxPercent;
    @ManyToOne
    @JoinColumn(name = "taxationTypeCode")
    private TaxationTypeDO taxationType;

    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] productPhoto;

    private String photoFileName;

    @Column(length = 50)
    private String userId;

    private long initialAdditionDate;
    @Column(length = 50)
    private String lastUpdatedBy;

    private long lastUpdatedTimeStamp;

    @ManyToOne
    @JoinColumn(name = "reviewCode")
    private ReviewStatusDO reviewStatus;
    @ManyToOne
    @JoinColumn(name = "productManufacturer")
    private SupplierDO productManufacturer;

    private String comments;

    @Column(length = 50)
    private String approver;
}
