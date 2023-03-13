package com.reify.supplier.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@JsonIgnoreProperties(value = {"reviewName"})
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReviewStatusDTO {

    private String reviewCode;
    private String reviewName;

}
