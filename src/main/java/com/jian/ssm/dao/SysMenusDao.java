package com.jian.ssm.dao;

import com.jian.ssm.entity.SysMenus;
import com.jian.ssm.entity.SysMenusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysMenusDao {
    int countByExample(SysMenusExample example);

    int deleteByExample(SysMenusExample example);

    int deleteByPrimaryKey(Integer menuid);

    int insert(SysMenus record);

    int insertSelective(SysMenus record);

    List<SysMenus> selectByExampleWithRowbounds(SysMenusExample example, RowBounds rowBounds);

    List<SysMenus> selectByExample(SysMenusExample example);

    SysMenus selectByPrimaryKey(Integer menuid);

    int updateByExampleSelective(@Param("record") SysMenus record, @Param("example") SysMenusExample example);

    int updateByExample(@Param("record") SysMenus record, @Param("example") SysMenusExample example);

    int updateByPrimaryKeySelective(SysMenus record);

    int updateByPrimaryKey(SysMenus record);
    
    List<SysMenus>    selectMenusByRoleId(int roleid);
    
}