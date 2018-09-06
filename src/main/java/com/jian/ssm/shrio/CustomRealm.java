package com.jian.ssm.shrio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.jian.ssm.dao.SysAdminDao;
import com.jian.ssm.dao.SysMenusDao;
import com.jian.ssm.entity.SysAdmin;
import com.jian.ssm.entity.SysAdminExample;
import com.jian.ssm.entity.SysAdminExample.Criteria;
import com.jian.ssm.entity.SysMenus;

/**
 * 
 * @ClassName: CustomRealm
 * @Description:TODO
 * @author: jianlinwei
 * @date: 2018年7月16日 下午5:29:53
 *
 */
public class CustomRealm extends AuthorizingRealm {
	@Autowired
	SysAdminDao sad;
	@Autowired
	SysMenusDao   smd ;

	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SysAdmin   sysAdmin   =  (SysAdmin) principal.getPrimaryPrincipal();
        int role  = sysAdmin.getRoleid();
        List<String>   permlist  = new ArrayList<>();
        List<SysMenus>    menus =  smd.selectMenusByRoleId(role);
        for(SysMenus  sm : menus){
        	if(StringUtils.isNotEmpty(sm.getPermission())){
        		permlist.add(sm.getPermission());
			}
        }
        
        Set<String >  ss = new HashSet<>();
        for(String perms :  permlist){
        	if(StringUtils.isBlank(perms)){
				continue;
			}
        	ss.addAll(Arrays.asList(perms.trim().split(",")));
        }
		SimpleAuthorizationInfo   sai  = new SimpleAuthorizationInfo();
		sai.setStringPermissions(ss);
		return sai;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		String userName = (String) upt.getPrincipal();
		String Pwd = new String((char[])upt.getCredentials());

		SysAdminExample sae = new SysAdminExample();
		Criteria criteria = sae.createCriteria();
		criteria.andUsernameEqualTo(userName);
		List<SysAdmin> ls = sad.selectByExample(sae);
		if (ls == null || ls.size() <= 0) {
			throw new UnknownAccountException("账号不存在");
		}
		Pwd = new Md5Hash(Pwd,ls.get(0).getSalt()).toHex();
		
		// 密码错误
		if (!Pwd.equals(ls.get(0).getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确!");
		}

		// 账号未分配角色
		if (ls.get(0).getRoleid() == null ) {
			throw new UnknownAccountException("账号未分配角色!");
		}
		ByteSource bs = ByteSource.Util.bytes(ls.get(0).getSalt());
         SimpleAuthenticationInfo  simpleAuthenticationInfo  = new SimpleAuthenticationInfo(ls.get(0), Pwd,bs, this.getName());
		return simpleAuthenticationInfo;
	}

	/*
	 * public static void main(String[] args) { MD5Util md5 = new MD5Util();
	 * String s = md5.getSalt(); System.out.println(s); Object obj = new
	 * SimpleHash("MD5", "admin123", s, 1024); System.out.println(obj); }
	 */
}
