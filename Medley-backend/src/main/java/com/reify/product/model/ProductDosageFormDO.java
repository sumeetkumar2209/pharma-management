package com.reify.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope( value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "PRODUCT_DOSAGE_FORM")
public class ProductDosageFormDO {

    @Id
    @Column(length = 3)
    private String productDosageCode;

    @Column(length = 15)
    private String getProductDosageName;
}
