package com.reify.customer.DTO;



import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerSearchDTO {
    private CustomerFilterDTO customerFilter;

    private int startIndex;

    private int endIndex;
}
