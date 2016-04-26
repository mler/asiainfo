package com.bdx.rainbow.mapper.sys;

import com.bdx.rainbow.entity.sys.SysUser2Role;
import com.bdx.rainbow.entity.sys.SysUser2RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUser2RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    int countByExample(SysUser2RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    int deleteByExample(SysUser2RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    int insert(SysUser2Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    int insertSelective(SysUser2Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    List<SysUser2Role> selectByExample(SysUser2RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    int updateByExampleSelective(@Param("record") SysUser2Role record, @Param("example") SysUser2RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    int updateByExample(@Param("record") SysUser2Role record, @Param("example") SysUser2RoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_2_role
     *
     * @mbggenerated Thu Jan 07 15:12:03 CST 2016
     */
    void insertBatch(List<SysUser2Role> recordLst);
}