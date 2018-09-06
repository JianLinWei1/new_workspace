package com.jian.ssm.entity;

import java.io.Serializable;

public class SysRoleMenusKey implements Serializable {
    private Integer roleid;

    private Integer menuid;

    private static final long serialVersionUID = 1L;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getMenuid() {
        return menuid;
    }

    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }
}