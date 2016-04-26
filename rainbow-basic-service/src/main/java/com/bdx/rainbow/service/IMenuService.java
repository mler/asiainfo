package com.bdx.rainbow.service;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.entity.sys.SysMenu;
import com.bdx.rainbow.entity.sys.SysMenuTree;

public interface IMenuService {
	Integer insertMenu(SysMenu obj) throws SystemException, BusinessException; 
	
	/**
	 * obj为null时，则条件为空
	 * 提供  menu_name like 方式, menu_pid, menu_type, menu_status的分页查询
	 * @param obj
	 * @param start
	 * @param count
	 * @return
	 */
	List<SysMenu> getMenus(SysMenu obj, Integer start, Integer count) throws SystemException, BusinessException;
	
	/**
	 * condition is menu_id, use to get sysMenu obj
	 * @param menuId
	 * @return
	 */
	SysMenu getMenuByKey(Integer menuId) throws SystemException, BusinessException;
	
	/**
	 * 更新菜单信息
	 * @param record
	 * @return
	 */
	int updateMenu(SysMenu record) throws SystemException, BusinessException;
	
	//批量更新菜单状态
	void updateMenuStatusByMenuIds(List<Integer> menuIds, String menuStatus, Integer updator) throws SystemException, BusinessException;
	
	//更新菜单状态
	 int updateMenuStatusById(Integer menuId, String menuStatus, Integer updator) throws SystemException, BusinessException;

	//获取菜单记录数
	int countMenu(SysMenu record) throws SystemException,BusinessException;
	
	/**
	 * 获取MENU 树结构信息
	 * @param condition
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	DefaultTree<SysMenuTree> getSysMenuTree(Map<String, Object> condition)
			throws SystemException, BusinessException;
		
	/**
	 * 根据menu列表， 节点获取树形结构，主要用于用户登录后，后台构建用户权限菜单树
	 * @param menus
	 * @param nodeId
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	DefaultTree<SysMenuTree> getSysMenuTree(List<SysMenuTree> menus,
			String nodeId) throws SystemException, BusinessException;
	
	//删除菜单
	void deleteByPrimaryKey(Integer menuId) throws SystemException, BusinessException;
	
	//查找不是根节点的菜单
	public List<SysMenu> getMenusNoPid(SysMenu obj,List<Integer> menupids) throws SystemException, BusinessException;
	
	//查询角色所属的菜单
	public List<SysMenu> getMenus(List<Integer> roleIds, String device_type) throws SystemException, BusinessException;
}
