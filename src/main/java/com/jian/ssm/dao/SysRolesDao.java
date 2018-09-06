package com.jian.ssm.dao;

import com.jian.ssm.entity.SysRoles;
import com.jian.ssm.entity.SysRolesExample;
import com.jian.ssm.util.PageUtil;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysRolesDao {
    int countByExample(SysRolesExample example);

    int deleteByExample(SysRolesExample example);

    int deleteByPrimaryKey(Integer roleid);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    List<SysRoles> selectByExampleWithRowbounds(SysRolesExample example, RowBounds rowBounds);

    List<SysRoles> selectByExample(SysRolesExample example);

    SysRoles selectByPrimaryKey(Integer roleid);

    int updateByExampleSelective(@Param("record") SysRoles record, @Param("example") SysRolesExample example);

    int updateByExample(@Param("record") SysRoles record, @Param("example") SysRolesExample example);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);
    
    List<SysRoles>  selectRolesBybelongId(@Param("sr")SysRoles  sr ,@Param("pu")PageUtil  pu);
    
    List<SysRoles> selectByRoleNameAndBelongId(@Param("sr")SysRoles  sr );
}