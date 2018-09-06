package com.jian.ssm.service;

import java.util.List;
import com.jian.ssm.entity.Menu;
import com.jian.ssm.entity.SysAdmin;
import com.jian.ssm.entity.SysRoles;
import com.jian.ssm.entity.XtreeData;
import com.jian.ssm.util.PageUtil;
import com.jian.ssm.util.ResultUtil;

public interface SysAdminService {
          //获取菜单
	List<Menu>  getMenus(SysAdmin  sysAdmin);
	
	int updateByPrimaryKeySelective(SysAdmin record);
	
	ResultUtil    selectRolesBybelongId(SysRoles  sr ,PageUtil  pu);
	 
	List<XtreeData>  getXtreeDataBybelongId(int roleId , int belongId);
	
	int  roleNameOnly(SysRoles sr);
	
	int  addRole(SysRoles sysRoles , String menu);
	
	int editRole(SysRoles sysRoles , String menu);
	
}
