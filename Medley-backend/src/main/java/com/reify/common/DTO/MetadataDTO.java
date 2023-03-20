package com.reify.common.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetadataDTO {
    private List<CountryDTO> countryList;

    private List<CurrencyDTO> currencyList;

    private List<QualificationStatusDTO> qualificationStatusList;

    private List<StatusDTO> statusList;

    private List<ReviewStatusDTO> reviewStatusList;

}
