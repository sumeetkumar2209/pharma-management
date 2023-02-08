package com.reify.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "USER")
public class UserDO {

    @Id
    @GeneratedValue
    @Column(name = "userId")
    private UUID userId;

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

    @ManyToOne
    @JoinColumn(name = "roleId")
    private RoleDO roleDO;

}