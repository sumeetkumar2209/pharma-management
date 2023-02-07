package com.reify.login.DTO;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class UserDTO {

    private String userId;
    private String emailId;
    private String password;
    private Timestamp creationTimestamp;
    private String createdBy;
    private Timestamp modifiedTimestamp;
    private String modifiedBy;

    private String firstName;
    private String middleName;
    private String lastName;

    private String contactNo;

    private String supervisorId;

    private Timestamp lastLoginTime;

}
