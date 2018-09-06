package com.jian.ssm.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jian.ssm.annotation.SysLog;
import com.jian.ssm.entity.Menu;
import com.jian.ssm.entity.SysAdmin;
import com.jian.ssm.service.SysAdminService;
import com.jian.ssm.util.ResultUtil;

/**
 * 
 * @ClassName:  AdminController   
 * @Description:TODO   
 * @author: JianLinWei
 * @date:   2018年8月29日 上午9:51:19   
 *
 */
@Controller
@RequestMapping(value="/sys")
public class AdminController {
	@Autowired
	SysAdminService  sas ;
	
	
	
    @RequestMapping(value="/welcome")
	public  String welcome(){
		return "welcome";
	}
    @SysLog(value="登陆")
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest req , Model  model) {
		SysAdmin admin = (SysAdmin)SecurityUtils.getSubject().getPrincipal();
		req.setAttribute("admin", admin);
		return "index";
	}
	
	
	@RequestMapping(value="/refuse" ,method=RequestMethod.GET)
	public String refuse(){
		return "refuse";
	}
	
	/**
	 * 
	 * @Title: login   
	 * @Description: TODO   
	 * @param: @param request
	 * @param: @param userName
	 * @param: @param password
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: ResultUtil      
	 * @throws
	 */
	
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	@ResponseBody
	public  ResultUtil  login(HttpServletRequest request ,String userName ,String password){
		if(StringUtils.isEmpty(userName)||StringUtils.isEmpty(password)){
			return  ResultUtil.error("参数不能为空");
		}
		
		try{
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken  token = new UsernamePasswordToken(userName ,password);
		subject.login(token);
		}catch (UnknownAccountException e) {
			return ResultUtil.error("账户不存在");
		}catch (IncorrectCredentialsException e) {
			System.out.println(e.getMessage());
			return ResultUtil.error("密码错误");
		}catch (LockedAccountException e) {
			return ResultUtil.error("账号被锁定");
		}catch (AuthenticationException e) {
			return ResultUtil.error("账户验证失败");
		}
		return ResultUtil.ok();
	}
	
	
	/**
	 * 
	 * @Title: getMuens   
	 * @Description: 获取菜单  
	 * @param: @param request
	 * @param: @return 
	 * @author: jianlinwei     
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value="/getmenus" , method = RequestMethod.GET)
	@ResponseBody
	public  List<Menu>  getMuens(HttpServletRequest request ){
		SysAdmin  sysAdmin  = (SysAdmin) SecurityUtils.getSubject().getPrincipal();
		List<Menu>   lm = null;
		if(sysAdmin!=null){
			lm  = sas.getMenus(sysAdmin);
		}
			
		return lm;
	}
	/**
	 * 
	 * @Title: loginOut   
	 * @Description: TODO   
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: String      
	 * @throws
	 */
	@SysLog(value="登出")
	@RequestMapping(value="/out" ,method =RequestMethod.GET)
	public  String  loginOut(){
		SecurityUtils.getSubject().logout();
		return "redirect:login.jsp";
	}
	/**
	 * 
	 * @Title: changePw   
	 * @Description: TODO   
	 * @param: @return 
	 * @author: JianLinWei     
	 * @return: String      
	 * @throws
	 */
	@SysLog(value="修改密码")
	@RequestMapping(value="/changePwd" ,method = RequestMethod.POST)
	@ResponseBody
	public  String changePw(HttpServletRequest  request){
		SysAdmin   sa  = (SysAdmin) SecurityUtils.getSubject().getPrincipal();
	     String oldPwd = request.getParameter("pwd");
		JSONObject  json = new  JSONObject();
		 String newPwd  =  new Md5Hash(oldPwd,sa.getSalt()).toHex();
		 int  id  = sa.getId();
		 sa  = new SysAdmin();
		 sa.setId(id);
		 sa.setPassword(newPwd);
		int x = sas.updateByPrimaryKeySelective(sa);
		json.put("code", x);
		if(x==1){
			SecurityUtils.getSubject().logout();
		}
		return  json.toString(); 
	}
	
	
}
