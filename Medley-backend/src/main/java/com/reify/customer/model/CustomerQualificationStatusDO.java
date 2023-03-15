package com.reify.customer.model;

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
import java.io.Serializable;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "CUSTOMER_QUALIFICATION_STATUS")
public class CustomerQualificationStatusDO implements Serializable {

    @Id
    @Column(length = 2)
    private String customerQfCode;

    @Column(length = 15)
    private String customerQfName;
}
