package com.reify.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "USER")
public class UserDO {

    @Id
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