package com.bdx.rainbow.mapper.sys;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.entity.sys.SysDeptTree;
import com.bdx.rainbow.entity.sys.SysMenuTree;
import com.bdx.rainbow.entity.sys.SysRole;
import com.bdx.rainbow.entity.sys.SysRoleTree;
import com.bdx.rainbow.entity.sys.SysUser;

public interface SysPowerCommMapper {
	/**
	 * sys_user 历史表，Map 参数如下：
	 * bussin， bussin_type，userId或userIds
	 * @param condition
	 */
	void insertUserHis(Map<String, Object> condition);
	
	void insertUserExtHis(Map<String, Object> condition);
	
	/**
	 * sys_dept 历史表，Map 参数如下：
	 * bussin， bussin_type，deptId或deptIds
	 * @param condition
	 */
	void insertDeptHis(Map<String, Object> condition);
	void insertDeptExtHis(Map<String, Object> condition);

	/**
	 * sys_role 历史表，Map 参数如下：
	 * bussin， bussin_type，roleId或roleIds
	 * @param condition
	 */
	void insertRoleHis(Map<String, Object> condition);
	
	/**
	 * sys_menu 历史表，Map 参数如下：
	 * bussin， bussin_type，menuId或menuIds
	 * @param condition
	 */
	void insertMenuHis(Map<String, Object> condition);
	
	/**
	 * 根据user_login_name, email, mobile_phone返回用户信息
	 * @param condition
	 * @return
	 */
	SysUser getUserByLoginOrMPOrEmail(Map<String, Object> condition);
	
	/**
	 * 根据用户ID获取用户拥有的角色
	 * @param condition
	 * @return
	 */
	List<SysRole> getRolesByUserId(Map<String, Object> condition);
	
	/**
	 * 根据用户ID获取用户拥有的菜单
	 * @param condition
	 * @return
	 */
	List<SysMenuTree> getMenusByUserId(Map<String, Object> condition);
	
	/**
	 * 根据user_type获取角色
	 * @param condition
	 */
	void insertUser2RoleByUserType(Map<String, Object> condition);

	/**
	 * 构造用户树
	 * @param condition
	 * @return
	 */
	List<SysDeptTree> getDeptTree(Map<String, Object> condition);
	
	/**
	 * 构造菜单树
	 * @param condition
	 * @return
	 */
	List<SysMenuTree> getMenuTree(Map<String, Object> condition);
	
	/**
	 * 构造权限树
	 * @param condition
	 * @return
	 */
	List<SysRoleTree> getRoleTree(Map<String, Object> condition);

}
