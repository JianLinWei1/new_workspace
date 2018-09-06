package com.jian.ssm.entity;

import java.io.Serializable;
public class SysRoles implements Serializable {
    private Integer roleid;

    private String rolename;

    private String roledescription;

    private Integer belongid;
    
    private Integer flag ;
    
    private String  hasModel ;
    
   

    private static final long serialVersionUID = 1L;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    public String getRoledescription() {
        return roledescription;
    }

    public void setRoledescription(String roledescription) {
        this.roledescription = roledescription == null ? null : roledescription.trim();
    }

    public Integer getBelongid() {
        return belongid;
    }

    public void setBelongid(Integer belongid) {
        this.belongid = belongid;
    }

	public String getHasModel() {
		return hasModel;
	}

	public void setHasModel(String hasModel) {
		this.hasModel = hasModel;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
    
    

}