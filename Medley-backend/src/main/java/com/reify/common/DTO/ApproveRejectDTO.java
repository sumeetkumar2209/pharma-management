package com.reify.common.DTO;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ApproveRejectDTO {

    private String workFlowId;

    private String decision;

    private String comments;

}
