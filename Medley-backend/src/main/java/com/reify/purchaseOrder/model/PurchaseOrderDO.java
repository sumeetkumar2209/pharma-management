package com.reify.purchaseOrder.model;

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
import java.io.Serializable;
import java.util.List;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrderDO implements Serializable {

       @Id
        @Column(length = 7)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "po_seq")
        @GenericGenerator(name = "po_seq",
                strategy = "com.reify.common.helper.CustomIdGenerator",
                parameters = {
                        @org.hibernate.annotations.Parameter(name = "prefix", value = "PO"),
                        @org.hibernate.annotations.Parameter(name = "seqName", value = "po_seq"),
                        @org.hibernate.annotations.Parameter(name = "seqLength", value = "5")
                })
        private String purchaseOrderId;

        private String workFlowId;

        private String purchaseOrderNo;

        @ManyToOne
        @JoinColumn(name = "supplierId")
        private SupplierDO supplierId;

        private long purchaseOrderDate;

        private long validity;

        private double totalPoAmount;

        private long lastUpdatedDate;

        @Column(length = 50)
        private String updatedBy;

        private long createdTimeStamp;

        private long updatedTimeStamp;

        private String createdBy;
        @Column(length = 50)
        private String userId;

        private long initialAdditionDate;


        @OneToMany(cascade = CascadeType.ALL)
        @JoinColumn(name = "workFlowId" ,referencedColumnName ="workFlowId")
        private List<PurchaseOrderDetailsDO> purchaseOrderDetailsList;



        @ManyToOne
        @JoinColumn(name = "reviewCode")
        private ReviewStatusDO reviewStatus;

        @Column(length = 50)
        private String approver;

        private String comments;

}
