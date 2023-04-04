package com.reify.product.model;

import com.reify.common.model.CountryDO;
import com.reify.common.model.CurrencyDO;
import com.reify.common.model.ReviewStatusDO;
import com.reify.supplier.model.SupplierDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "INT_PRODUCT")
public class ProductDO_INT {

    @Id
    @Column(length = 8)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_wf_seq")
    @GenericGenerator(name = "product_wf_seq",
            strategy = "com.reify.common.helper.CustomIdGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "prefix", value = "WFP"),
                    @org.hibernate.annotations.Parameter(name = "seqName", value = "product_wf_seq"),
                    @org.hibernate.annotations.Parameter(name = "seqLength", value = "5")
            })
    private String workFlowId;

    @Column(length = 20)
    private String productId;

    @Column(length = 50)
    private String userId;

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
