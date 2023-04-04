package com.reify.product.DTO;

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
@JsonIgnoreProperties(value = {"productApprovalStatusName"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductApprovalStatusDTO {

    private String productApprovalStatusCode;

    private String productApprovalStatusName;
}
