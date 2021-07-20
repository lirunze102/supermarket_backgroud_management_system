package com.wzu.lrz.pojo;

import java.util.Date;

public class UserRole {
    private Integer id;
    private String roleName;
    private String roleCode;
    private Integer createBy;
    private Date creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
