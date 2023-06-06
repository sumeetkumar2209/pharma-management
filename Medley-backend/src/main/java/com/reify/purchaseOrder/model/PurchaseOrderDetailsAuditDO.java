package com.reify.purchaseOrder.model;

import com.reify.common.model.ReviewStatusDO;
import com.reify.product.model.PackTypeDO;
import com.reify.product.model.ProductDosageFormDO;
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
@Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "PURCHASE_ORDER_DETAILS_AUDIT")
public class PurchaseOrderDetailsAuditDO implements Serializable {

    @Id
    @GeneratedValue
    private int purchaseOrderDetailId;

    @Column(length = 30)
    private String licenceNumber;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "productDosageCode")
    private ProductDosageFormDO productDosageForm;

    @Column(length = 50)
    private String productName;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "packTypeCode")
    private PackTypeDO packType;

    @Column(length = 10)
    private String strength;

    @Column(length = 6)
    private int packSize;

    @Column(precision =10, scale = 2)
    private double pricePerPack;

    private int purchaseOrderQuantity;

    private double totalProductAmt;

    private int purchaseOrderAmt;

}
