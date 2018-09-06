package com.jian.ssm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jian.ssm.annotation.SysLog;
import com.jian.ssm.entity.SysAdmin;
import com.jian.ssm.entity.SysRoles;
import com.jian.ssm.entity.XtreeData;
import com.jian.ssm.service.SysAdminService;
import com.jian.ssm.util.PageUtil;
import com.jian.ssm.util.ResultUtil;

@Controller
@RequestMapping(value = "/role")
public class RoleListContorller {
	@Autowired
	SysAdminService sas;

	/**
	 * 
	 * @Title: RoleList @Description: 获取角色页 @param: @param
	 * request @param: @return @author: JianLinWei @return: String @throws
	 */
	@RequestMapping(value = "/rolelist", method = RequestMethod.GET)
	@RequiresPermissions("sys:role:list")
	public String RoleList(HttpServletRequest request ,Model model) {
		SysAdmin  sysAdmin = (SysAdmin) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("admin", sysAdmin);
		return "page/admin/rolelist";
	}

	/**
	 * 
	 * @Title: GetRoleList @Description: 获取改服务管理下的角色数据 @param: @param
	 * request @param: @return @author: JianLinWei @return: String @throws
	 */
	@RequestMapping(value = "/getrolelist", method = RequestMethod.GET)
	@ResponseBody
	public ResultUtil GetRoleList(HttpServletRequest request, String page, String limit) {
		SysAdmin sysAdmin = (SysAdmin) SecurityUtils.getSubject().getPrincipal();
		SysRoles sr = new SysRoles();
		sr.setBelongid(sysAdmin.getBelongid());
		ResultUtil resultUtil = sas.selectRolesBybelongId(sr, PageUtil.getRowBounds(page, limit));
		return resultUtil;
	}
	
	/**
	 * 
	 * @Title: xtree   
	 * @Description: 获取权限角色树  
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: List<XtreeData>      
	 * @throws
	 */
	@RequestMapping(value="/xtree" , method = RequestMethod.GET)
	@ResponseBody
	public List<XtreeData>   xtree(@RequestParam(value="roleId" ,defaultValue="-1") int roleId){
		SysAdmin  sysAdmin  = (SysAdmin) SecurityUtils.getSubject().getPrincipal();
		if(roleId == -1)
			roleId = sysAdmin.getRoleid();
	    List<XtreeData>  lx =	sas.getXtreeDataBybelongId(roleId, sysAdmin.getBelongid());
		return lx;
	}
	/**
	 * 
	 * @Title: editRole   
	 * @Description: 角色编辑  
	 * @param: @param reqeust
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/editRole" , method = RequestMethod.GET)
	@RequiresPermissions("sys:role:edit")
	public  String  editRole(HttpServletRequest reqeust ,Model  model){
		return "page/admin/editRole";
	}
	
	
	@SysLog(value="编辑角色")
	@RequestMapping(value="/editRole" , method = RequestMethod.POST)
	@RequiresPermissions("sys:role:edit")
	public ResultUtil editRole(HttpServletRequest request , String data ,String menu){
		JSONObject  json = new JSONObject(data);
		SysRoles  sysRoles  = new SysRoles();
		sas.editRole(sysRoles, menu);
		return null;
	}
	/**
	 * 
	 * @Title: addRole   
	 * @Description: 添加角色
	 * @param: @param request
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/addRole" , method =RequestMethod.GET)
	@RequiresPermissions("sys:role:add")
	public  String addRole(HttpServletRequest request){
		
		return "page/admin/addRole";
	}
	
	@RequestMapping(value="/roleOnly/{rolename}",method = RequestMethod.GET)
	@ResponseBody
	public ResultUtil roleOnly(HttpServletRequest request ,@PathVariable String rolename){
		SysAdmin  sysadmin = (SysAdmin) SecurityUtils.getSubject().getPrincipal();
		SysRoles  sr = new SysRoles();
		sr.setBelongid(sysadmin.getBelongid());
		sr.setRolename(rolename);
		int count = sas.roleNameOnly(sr);
		if(count > 0){
			return new ResultUtil(1, "角色名已经存在");
		}
		return ResultUtil.ok();
	}
	
	@SysLog(value="添加角色")
	@RequestMapping(value="/addRole" , method = RequestMethod.POST)
	@RequiresPermissions("sys:role:add")
	@ResponseBody
	public  ResultUtil  addRole(HttpServletRequest reqeust , String data ,String menu){
		
	    SysAdmin  sysAdmin =	(SysAdmin) SecurityUtils.getSubject().getPrincipal();
		JSONObject  json = new JSONObject(data);
		SysRoles sysRoles = new SysRoles();
		sysRoles.setBelongid(sysAdmin.getBelongid());
		sysRoles.setRolename(json.getString("rolename"));
		sysRoles.setRoledescription(json.getString("roledescription"));
		sysRoles.setFlag(Integer.valueOf(json.getString("flag")));
	    sas.addRole(sysRoles, menu);
	    
		return ResultUtil.ok();
		
	}
	
}
