package com.bdx.rainbow.urs.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.bdx.rainbow.entity.urs.SysMenu;
import com.bdx.rainbow.entity.urs.SysMenuExample;
import com.bdx.rainbow.entity.urs.SysRole2Menu;
import com.bdx.rainbow.entity.urs.SysRole2MenuExample;
import com.bdx.rainbow.mapper.urs.SysMenuMapper;
import com.bdx.rainbow.mapper.urs.SysRole2MenuMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.urs.common.SequenceUtil;
import com.bdx.rainbow.mapper.urs.SysPowerCommMapper;
import com.bdx.rainbow.urs.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService {
	
	@Autowired
	protected SysMenuMapper sysMenuMapper;
	@Autowired
	protected SysRole2MenuMapper sysRole2MenuMapper;
	@Autowired
	protected SysPowerCommMapper sysPowerCommMapper;
		
	@Override
	public Integer insertMenu(SysMenu record) throws SystemException, BusinessException {
		Integer menu_id = sysMenuMapper.insert(record);
		return menu_id;
	}

	@Override
	public int updateMenu(SysMenu record) throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("menuId", record.getMenuId());
		condition.put("bussin_type", "E");
		sysPowerCommMapper.insertMenuHis(condition);
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		int count = sysMenuMapper.updateByPrimaryKeySelective(record);
/*		if (count != 0) {
			throw new SystemException("9999", "数据更新异常，被操作记录数" + count);
		}*/
		return count;
	}

	@Override
	public List<SysMenu> getMenus(SysMenu obj, Integer start, Integer count) throws SystemException, BusinessException {
		SysMenuExample where = getCondition(obj);
		if(start != null) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(count);
		}
		List<SysMenu> sysMenus = sysMenuMapper.selectByExample(where);
		return sysMenus;
	}

	@Override
	public SysMenu getMenuByKey(Integer menuId) throws SystemException, BusinessException {
		return sysMenuMapper.selectByPrimaryKey(menuId);
	}

	@Override
	public DefaultTree<com.bdx.rainbow.entity.urs.SysMenuTree> getSysMenuTree(Map<String, Object> condition)
			throws SystemException, BusinessException {
		List<com.bdx.rainbow.entity.urs.SysMenuTree> menus = sysPowerCommMapper.getMenuTree(condition);
		DefaultTree<com.bdx.rainbow.entity.urs.SysMenuTree> tree = new DefaultTree<com.bdx.rainbow.entity.urs.SysMenuTree>("-1", menus);
		return	tree;
		
	}
	
	@Override
	public DefaultTree<com.bdx.rainbow.entity.urs.SysMenuTree> getSysMenuTree(List<com.bdx.rainbow.entity.urs.SysMenuTree> menus, String nodeId)
			throws SystemException, BusinessException {
		DefaultTree<com.bdx.rainbow.entity.urs.SysMenuTree> tree = new DefaultTree<com.bdx.rainbow.entity.urs.SysMenuTree>(nodeId, menus);
		return	tree;
	}

	@Override
	public int countMenu(SysMenu record) throws SystemException,
			BusinessException {
		SysMenuExample where = getCondition(record);
		return sysMenuMapper.countByExample(where);
	}
	
	@Override
	public void updateMenuStatusByMenuIds(List<Integer> menuIds, String menuStatus, Integer updator) 
			throws SystemException, BusinessException 
	{
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", Integer.valueOf(bussin));
		condition.put("bussin_type", "A");
		for(Integer menuId : menuIds) 
		{
			condition.put("menuId", menuId);
			sysPowerCommMapper.insertMenuHis(condition);
			this.updateMenuStatusById(menuId, menuStatus, updator);
		}
	}
	
	@Override
	public int updateMenuStatusById(Integer menuId, String menuStatus, Integer updator)
			throws SystemException, BusinessException {
		SysMenu record = new SysMenu();
		record.setMenuId(menuId);
		record.setMenuStatus(menuStatus);
		record.setUpdater(updator);
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return 	sysMenuMapper.updateByPrimaryKeySelective(record);
	}
	
	private SysMenuExample getCondition(SysMenu record) {
		SysMenuExample where = new SysMenuExample();
		if (record != null) {
			SysMenuExample.Criteria cr = where.createCriteria();

			if(record.getMenuName() != null && !record.getMenuName().equals("") && StringUtils.isNotEmpty(record.getMenuName())) {
				cr.andMenuNameLike("%" + record.getMenuName() + "%");
			}
			if(record.getMenuType() != null && StringUtils.isNotEmpty(record.getMenuType())) {
				cr.andMenuTypeEqualTo(record.getMenuType());
			}
			if(record.getMenuPid() != null) {
				cr.andMenuPidEqualTo(record.getMenuPid());
			}
			if(record.getMenuStatus() != null && StringUtils.isNotEmpty(record.getMenuStatus())) {
				cr.andMenuStatusEqualTo(record.getMenuStatus());
			}
			if(record.getMenuUrl() != null && StringUtils.isNotEmpty(record.getMenuUrl())) {
				cr.andMenuUrlEqualTo(record.getMenuUrl());
			}
			if(record.getoTarget() != null && StringUtils.isNotEmpty(record.getoTarget())) {
				cr.andOTargetEqualTo(record.getoTarget());
			}
            if (record.getPlatId()!=null&&StringUtils.isNotEmpty(record.getPlatId().toString())){
                cr.andPlatIdEqualTo(record.getPlatId());
            }
		}
		return where;
	}
	
	@Override
	public void deleteByPrimaryKey(Integer menuId) throws SystemException, BusinessException {
		sysMenuMapper.deleteByPrimaryKey(menuId);
	}
	
	@Override
	public List<SysMenu> getMenusNoPid(SysMenu obj,List<Integer> menupids) throws SystemException, BusinessException {
		SysMenuExample where = new SysMenuExample();
		if (obj != null) {
			SysMenuExample.Criteria cr = where.createCriteria();
			if(obj.getMenuPid() != null) {
			  cr.andMenuPidNotEqualTo(obj.getMenuPid());
			}
			if(obj.getMenuName() != null && StringUtils.isNotEmpty(obj.getMenuName())) {
			  cr.andMenuNameLike("%" + obj.getMenuName() + "%");
			}
			if(menupids.size()>0){
			  cr.andMenuPidNotIn(menupids);
			}
			if(obj.getMenuStatus() != null && StringUtils.isNotEmpty(obj.getMenuStatus())) {
				cr.andMenuStatusEqualTo(obj.getMenuStatus());
			}
		}
		where.setOrderByClause("menu_pid asc");
		List<SysMenu> sysMenus = sysMenuMapper.selectByExample(where);
		return sysMenus;
	}
    //查询角色所属的菜单
	@Override
	public List<SysMenu> getMenus(List<Integer> roleIds, String device_type) throws SystemException, BusinessException {
		List<SysMenu> sysmenus = new ArrayList<SysMenu>();
		if(roleIds!=null&& roleIds.size()>0){
			SysRole2MenuExample example = new SysRole2MenuExample();
			example.createCriteria().andRoleIdIn(roleIds);
			example.setDistinct(true);
			List<SysRole2Menu> rolemenus = sysRole2MenuMapper.selectByExample(example);
			List<Integer> menuids = new ArrayList<Integer>();
			if(rolemenus!=null && rolemenus.size()>0){
				for(int i=0;i<rolemenus.size();i++){
					SysRole2Menu rolemenu = rolemenus.get(i);
                    menuids.add(rolemenu.getMenuId());
				}
				SysMenuExample example2 = new SysMenuExample();
				SysMenuExample.Criteria cr  = example2.createCriteria();
				cr.andMenuIdIn(menuids);
//				cr.andSysIdEqualTo(device_type);
                example2.setDistinct(true);
				sysmenus = sysMenuMapper.selectByExample(example2);
			}
		}
		return sysmenus;
	}
}
