package com.reify.common.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonIgnoreProperties(value = {"qualificationName"})
public class QualificationStatusDTO {

    private String qualificationCode;
    private String qualificationName;

}
