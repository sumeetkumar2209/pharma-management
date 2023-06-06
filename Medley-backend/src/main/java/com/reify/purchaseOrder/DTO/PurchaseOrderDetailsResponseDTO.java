package com.reify.purchaseOrder.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"purchaseOrderDetailId"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetailsResponseDTO {

    private String licenceNumber;

    private String productDosageForm;

    private String productName;

    private String packType;

    private String strength;

    private int packSize;

    private double pricePerPack;

    private int purchaseOrderQuantity;

    private double totalProductAmt;

    private int purchaseOrderAmt;

    private String reviewStatus;
}
