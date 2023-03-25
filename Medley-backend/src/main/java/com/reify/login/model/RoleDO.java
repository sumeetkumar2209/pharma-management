package com.reify.login.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Table(name = "ROLE")
public class RoleDO {

    @Id
    @Column(name = "ROLE_ID")
    private int roleId;

    @Column(name = "ROLENAME")
    private String roleName;

    @ManyToMany
    @JoinTable(
            name = "Role_Menu",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "MENU_ID"),
            uniqueConstraints = {@UniqueConstraint(name= "uniqueRoleAndMenu", columnNames = {"ROLE_ID","MENU_ID"})}
    )
    private List<MenuDO> menuList;
}
