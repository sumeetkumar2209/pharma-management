package com.reify.login.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Component
@Data
@Entity
@Table(name = "MENU")
public class MenuDO implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "MENU_ID")
    private int menuId;

    private String label;
    private String urlMapping;
    private int level;
    private int sortOrder;

}
