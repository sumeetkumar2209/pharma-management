package com.reify.login.constant;

public enum UserEnum {

    USERID("userId"),
    EMAILID("emailId"),
    FIRSTNAME("firstName"),
    MIDDLENAME("middleName"),
    LASTNAME("lastName"),
    CONTACTNO("contactNo"),
    SUPERVISORID("supervisorId"),
    LASTLOGINTIME("lastLoginTime"),
    ROLEID("roleId"),
    ROLENAME("roleName"),
    MENU_LIST("menuList"),

    APPROVER_MAP("approverMap");


    private String name;

    private UserEnum(String name){

        this.name = name;
    }

    public String getName(){
        return name;
    }

}
