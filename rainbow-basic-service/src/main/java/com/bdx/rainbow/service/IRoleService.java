package com.bdx.rainbow.service;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.entity.sys.SysRole;
import com.bdx.rainbow.entity.sys.SysRole2Menu;
import com.bdx.rainbow.entity.sys.SysRoleTree;

public interface IRoleService {
	Integer insertRole(SysRole record) throws SystemException, BusinessException; 
	/**
	 * obj为null时，则条件为空
	 * use params :SysRole obj(roleId, roleName), start(page start), count(page size) to get SysRoles
	 * @param record
	 * @param start
	 * @param count
	 * @return
	 */
	List<SysRole> getRoles(SysRole record, Integer start, Integer count) throws SystemException, BusinessException;
	
	//根据roleId获取Role对象
	SysRole getRoleByKey(Integer roleId) throws SystemException, BusinessException;
	
	//获取SysRole记录数
	int countRole(SysRole record) throws SystemException,BusinessException;
	
	//更新SysRole信息
	int updateRole(SysRole record) throws SystemException, BusinessException;
	
	
	/**
	 * save sys_role_2_menu 
	 * @param record
	 * @param menuIds
	 */
	void doRole2Menu(SysRole2Menu record, List<Integer> menuIds) throws SystemException, BusinessException;
	
	void insertRole2Menu(SysRole2Menu record)  throws SystemException, BusinessException;
	
	//删除角色和菜单的关系
	void deleteRole2Menu(Integer menuId)  throws SystemException, BusinessException;
	
	//得到角色树
	DefaultTree<SysRoleTree> getSysRoleTree(Map<String, Object> condition) throws SystemException, BusinessException;
	
	//插入角色和菜单的关系
	void insertRoleMenuRe(String roleid,String menus) throws SystemException, BusinessException;
	
	List<SysRole2Menu> getRole2Menus(String roleid) throws SystemException, BusinessException;
	
	//得到用户的角色
	Map getUserRole(Integer userId) throws SystemException, BusinessException;
	
	//新增用户和角色的关系
	void insertUser2Role(Integer userId,String roleId) throws SystemException, BusinessException; 
	
	//修改用户和角色的关系
	void updateUser2Role(Integer userId,String roleId) throws SystemException, BusinessException; 
}
