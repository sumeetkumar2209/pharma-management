package com.reify.common.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MetadataDTO {

    private CountryDTO country;

    private CurrencyDTO currency;

    private QualificationStatusDTO qualificationStatus;

    private StatusDTO status;

    private ReviewStatusDTO reviewStatus;



}
