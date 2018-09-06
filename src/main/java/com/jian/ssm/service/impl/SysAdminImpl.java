package com.jian.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.jian.ssm.dao.SysAdminDao;
import com.jian.ssm.dao.SysMenusDao;
import com.jian.ssm.dao.SysRoleMenusDao;
import com.jian.ssm.dao.SysRolesDao;
import com.jian.ssm.entity.Menu;
import com.jian.ssm.entity.SysAdmin;
import com.jian.ssm.entity.SysMenus;
import com.jian.ssm.entity.SysMenusExample;
import com.jian.ssm.entity.SysRoleMenusExample;
import com.jian.ssm.entity.SysRoleMenusExample.Criteria;
import com.jian.ssm.entity.SysRoleMenusKey;
import com.jian.ssm.entity.SysRoles;
import com.jian.ssm.entity.SysRolesExample;
import com.jian.ssm.entity.XtreeData;
import com.jian.ssm.service.SysAdminService;
import com.jian.ssm.util.PageUtil;
import com.jian.ssm.util.ResultUtil;

/**
 * 
 * @ClassName: SysAdminImpl
 * @Description:TODO
 * @author: jianlinwei
 * @date: 2018年7月27日 下午4:05:57
 *
 */
@Service
public class SysAdminImpl implements SysAdminService {
	@Resource
	SysMenusDao smd;
	@Resource
	SysRoleMenusDao srmd;
	@Resource
	SysAdminDao sad;
	@Resource
	SysRolesDao srd;

	@Override
	public List<Menu> getMenus(SysAdmin sysAdmin) {
		List<Menu> lm = new ArrayList<>();
		int roleId = sysAdmin.getRoleid();
		SysRoleMenusExample sysRoleMenusExample = new SysRoleMenusExample();
		Criteria criteria = sysRoleMenusExample.createCriteria();
		criteria.andRoleidEqualTo(roleId);
		List<SysRoleMenusKey> ls = srmd.selectByExample(sysRoleMenusExample);
		if (ls != null && ls.size() > 0) {
			List<SysMenus> lsm = smd.selectMenusByRoleId(roleId);
			for (int i = 0; i < lsm.size(); i++) {
				if (lsm.get(i).getParentid() == 0) {
					Menu menu = new Menu();
					menu.setTitle(lsm.get(i).getTiltle());
					menu.setIcon(lsm.get(i).getIcon());
					menu.setHref(lsm.get(i).getHref());
					menu.setSpread(lsm.get(i).getSpread());
					List<Menu> lm2 = new ArrayList<>();
					for (int j = 0; j < lsm.size(); j++) {
						if (lsm.get(j).getParentid() == lsm.get(i).getMenuid()) {
							Menu menu2 = new Menu();
							menu2.setTitle(lsm.get(j).getTiltle());
							menu2.setIcon(lsm.get(j).getIcon());
							menu2.setHref(lsm.get(j).getHref());
							menu2.setSpread(lsm.get(j).getSpread());
							lm2.add(menu2);
						}
					}
					menu.setChildren(lm2);
					lm.add(menu);

				}
			}
		}
		return lm;
	}

	@Override
	public int updateByPrimaryKeySelective(SysAdmin record) {

		return sad.updateByPrimaryKeySelective(record);
	}


	@Override
	public ResultUtil selectRolesBybelongId(SysRoles sr, PageUtil pu) {
		SysRolesExample  sysRolesExample  = new SysRolesExample();
		com.jian.ssm.entity.SysRolesExample.Criteria  criteria_1 = sysRolesExample.createCriteria();
		criteria_1.andBelongidEqualTo(sr.getBelongid());
		 int  count  = srd.countByExample(sysRolesExample);
		   List<SysRoles>   ls  = srd.selectRolesBybelongId(sr, pu);
		   for(SysRoles  sRoles : ls){
			   SysRoleMenusExample  sRoleMenusExample   = new SysRoleMenusExample();
			   Criteria criteria  = sRoleMenusExample.createCriteria();
			   criteria.andRoleidEqualTo(sRoles.getRoleid());
			   List<SysRoleMenusKey>  lsKeys  = srmd.selectByExample(sRoleMenusExample);
			   String  hasModel = "";
			   for(SysRoleMenusKey sKey : lsKeys){
				  SysMenus  sysMenus = smd.selectByPrimaryKey(sKey.getMenuid());
				  hasModel += ( ":|:"+ sysMenus.getTiltle() );
			   }
			   sRoles.setHasModel(hasModel);
		   }
		   ResultUtil  rUtil  = new ResultUtil();
		   rUtil.setCode(0);
		   rUtil.setCount(count);
		   rUtil.setMsg("cc");
		   rUtil.setData(ls);
		return rUtil;
	}

	
	@Override
	public List<XtreeData> getXtreeDataBybelongId(int roleId, int belongId) {
		List<XtreeData>  xtreeDatas  = new ArrayList<>();
		SysMenusExample   example  = new SysMenusExample();
		/*com.jian.ssm.entity.SysMenusExample.Criteria criteria  = example.createCriteria();*/
		//所有模块菜单
		List<SysMenus>  lSysMenus = smd.selectByExample(example);
		//角色模块菜单
		List<SysMenus>  lSysMenus2 = smd.selectMenusByRoleId(roleId);
		for(SysMenus m : lSysMenus){
			if(m.getParentid() == 0){
				XtreeData  x  = new XtreeData();
				x.setTitle(m.getTiltle());
				x.setValue(m.getMenuid() +"");
				List<XtreeData> xtreeDatas2  = new ArrayList<>();
				for(SysMenus m2 : lSysMenus){
					if(m2.getParentid() == m.getMenuid()){
						XtreeData  x2 = new XtreeData();
						x2.setTitle(m2.getTiltle());
						x2.setValue(m2.getMenuid()+"");
						List<XtreeData>  xtreeDatas3  = new ArrayList<>();
						for(SysMenus m3 : lSysMenus){
							if(m3.getParentid() == m2.getMenuid()){
								XtreeData  x3 = new XtreeData();
								x3.setTitle(m3.getTiltle());
								x3.setValue(m3.getMenuid()+"");
								// 是否拥有权限
								x3.setChecked(false);
								for (SysMenus mh : lSysMenus2) {
									if (mh.getMenuid() == m3.getMenuid()) {
										x3.setChecked(true);
										break;
									}
								}
								List<XtreeData>  l = new ArrayList<>();
								x3.setData(l);
								xtreeDatas3.add(x3);
							}
						}
						x2.setData(xtreeDatas3);
						xtreeDatas2.add(x2);
					}
					
				}
				x.setData(xtreeDatas2);
				xtreeDatas.add(x);
			}
		}
	/*	// 拥有没有子节点的节点，设置选中
				for (XtreeData xd : xtreeDatas) {
					if (xd.getData() == null || xd.getData().size() == 0) {
						for (SysMenus tbMenus : lSysMenus) {
							if (tbMenus.getMenuid() == Long.parseLong(xd.getValue())) {
								xd.setChecked(true);
							}
						}
					}
				}*/
		return xtreeDatas;
	}

	@Override
	public int roleNameOnly(SysRoles  sr) {
	    int count =	srd.selectByRoleNameAndBelongId(sr).size();
		return count;
	}

	@Override
	public int addRole(SysRoles sysRoles, String menu) {
		int  x = 0;
		  srd.insert(sysRoles);
		if(menu !=null && !menu.equals("")){
			String[]  menu_arry = menu.split(",");
			List<String>  menu_list = new ArrayList<>();
			boolean f ;
			for(String s1 : menu_arry){
				f =false ;
				for(String s2 : menu_list){
					if(s1.equals(s2)){
						f =true;
						break;
					}
				}
				if(!f)
				menu_list.add(s1);
			}
			
			if(menu_list.size() >0){
				for(int i = 0 ; i < menu_list.size() ; i++){
					SysRoleMenusKey sKey  = new SysRoleMenusKey();
					sKey.setRoleid(sysRoles.getRoleid());
					sKey.setMenuid(Integer.valueOf(menu_list.get(i)));
				 x =	srmd.insert(sKey);
				}
			}
		}
		
		return x;
	}

	@Override
	public int editRole(SysRoles sysRoles, String menu) {
		SysRolesExample  example = new SysRolesExample();
		com.jian.ssm.entity.SysRolesExample.Criteria  criteria = example.createCriteria();
		criteria.andRoleidEqualTo(sysRoles.getRoleid());
		int x  = srd.updateByExample(sysRoles, example);
		SysRoleMenusExample  sExample  = new SysRoleMenusExample();
		Criteria  criteria2  = sExample.createCriteria();
		criteria2.andRoleidEqualTo(sysRoles.getRoleid());
		srmd.deleteByExample(sExample);
		
		
		
		return x;
	}

}
