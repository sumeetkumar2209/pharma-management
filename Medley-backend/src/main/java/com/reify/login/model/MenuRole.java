package com.reify.login.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class MenuRole implements Serializable {

    private int roleId;
    private int menuId;
}
