package com.jian.ssm.dao;

import com.jian.ssm.entity.SysRoleMenusExample;
import com.jian.ssm.entity.SysRoleMenusKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SysRoleMenusDao {
    int countByExample(SysRoleMenusExample example);

    int deleteByExample(SysRoleMenusExample example);

    int deleteByPrimaryKey(SysRoleMenusKey key);

    int insert(SysRoleMenusKey record);

    int insertSelective(SysRoleMenusKey record);

    List<SysRoleMenusKey> selectByExampleWithRowbounds(SysRoleMenusExample example, RowBounds rowBounds);

    List<SysRoleMenusKey> selectByExample(SysRoleMenusExample example);

    int updateByExampleSelective(@Param("record") SysRoleMenusKey record, @Param("example") SysRoleMenusExample example);

    int updateByExample(@Param("record") SysRoleMenusKey record, @Param("example") SysRoleMenusExample example);
}