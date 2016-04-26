package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.entity.urs.*;
import com.bdx.rainbow.mapper.urs.SysPowerCommMapper;
import com.bdx.rainbow.mapper.urs.SysRole2MenuMapper;
import com.bdx.rainbow.mapper.urs.SysRoleMapper;
import com.bdx.rainbow.mapper.urs.SysUser2RoleMapper;
import com.bdx.rainbow.urs.common.DateUtil;
import com.bdx.rainbow.urs.common.SequenceUtil;
import com.bdx.rainbow.urs.service.IRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRole2MenuMapper sysRole2MenuMapper;
	@Autowired
	protected SysUser2RoleMapper sysUser2RoleMapper;
	@Autowired
	protected SysPowerCommMapper sysPowerCommMapper;

	@Override
	public Integer insertRole(SysRole record) throws SystemException, BusinessException {
		Integer roleId = sysRoleMapper.insert(record);
		return roleId;
	}

	@Override
	public int updateRole(SysRole record) throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
        String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("roleId", record.getRoleId());
		condition.put("bussin_type", "E");
		sysPowerCommMapper.insertRoleHis(condition);
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		int count = sysRoleMapper.updateByPrimaryKeySelective(record);
		return count;
	}
	
	@Override
	public List<SysRole> getRoles(SysRole record, Integer start, Integer count) throws SystemException, BusinessException {
		SysRoleExample where = getCondition(record);
		if(start != null) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(count);
		}
		return sysRoleMapper.selectByExample(where);
	}
	
	@Override
	public int countRole(SysRole record) throws SystemException,
			BusinessException {
		SysRoleExample where = getCondition(record);
		return sysRoleMapper.countByExample(where);
	}
	
	@Override
	public SysRole getRoleByKey(Integer roleId) {
		return sysRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	public void doRole2Menu(SysRole2Menu record, List<Integer> menuIds) throws SystemException, BusinessException{
		for(Integer menuId : menuIds) {
			record.setRoleId(menuId);
			sysRole2MenuMapper.insert(record);
		}
		
	}
	
	private SysRoleExample getCondition(SysRole record) {
		SysRoleExample where = new SysRoleExample();
		if (record != null) {
			SysRoleExample.Criteria cr = where.createCriteria();

			if(record.getRoleName() != null && StringUtils.isNotEmpty(record.getRoleName())) {
				cr.andRoleNameLike("%" + record.getRoleName() + "%");
			}
			if(record.getRolePid() != null && StringUtils.isNotEmpty(record.getRolePid().toString())) {
				cr.andRolePidEqualTo(record.getRolePid());
			}
            if(record.getPlatId() != null && StringUtils.isNotEmpty(record.getPlatId().toString())) {
                cr.andPlatIdEqualTo(record.getPlatId());
            }
		}
		return where;
	}
	
	@Override
	public void insertRole2Menu(SysRole2Menu record)  throws SystemException, BusinessException{
		sysRole2MenuMapper.insert(record);
	}
    
	@Override
	public void deleteRole2Menu(Integer menuId)  throws SystemException, BusinessException {	
		SysRole2MenuExample example = new SysRole2MenuExample();
		SysRole2MenuExample.Criteria cr = example.createCriteria();
		cr.andMenuIdEqualTo(menuId);
		sysRole2MenuMapper.deleteByExample(example);
	}
	
	@Override
	public DefaultTree<SysRoleTree> getSysRoleTree(Map<String, Object> condition)
			throws SystemException, BusinessException {
		List<SysRoleTree> roles = sysPowerCommMapper.getRoleTree(condition);
		DefaultTree<SysRoleTree> tree = new DefaultTree<SysRoleTree>("0", roles);
		return	tree;
	}
	
	@Override
	public void insertRoleMenuRe(String roleid,String menus) throws SystemException, BusinessException{
		String[] menu = menus.split(",");
		SysRole2Menu role2menu = new SysRole2Menu();
	    role2menu.setRoleId(Integer.parseInt(roleid));
	    Timestamp aeadTime = DateUtil.getTimestamp("2043-10-10"); 
	    role2menu.setAeadTime(aeadTime);
	    Timestamp effectTime = DateUtil.getTimestamp(DateUtil.getDateString("yyyy-MM-dd"));
	    role2menu.setEffectTime(effectTime);
	    //查询已经存在的
	    List<SysRole2Menu> existMenu = this.getRole2Menus(roleid);
	    String menuids = "";
	    SysRole2MenuExample example = null;
	    for(int j=0;j<existMenu.size();j++){
	    	SysRole2Menu menulist = existMenu.get(j);
	    	menuids = menuids+"&"+menulist.getMenuId()+"&"+",";
	    	String menu2 = menus.substring(0, menus.length()-1);
	    	String menuexist = "&"+menu2.replaceAll(",", "&,&")+"&";
	    	if(menuexist.indexOf("&"+menulist.getMenuId()+"&")<0){
	    		example = new SysRole2MenuExample();
	    		SysRole2MenuExample.Criteria cr = example.createCriteria();
	    		cr.andMenuIdEqualTo(menulist.getMenuId());
	    		cr.andRoleIdEqualTo(Integer.parseInt(roleid));
	    		sysRole2MenuMapper.deleteByExample(example);
	    	}
	    }
		for(int i=0;i<menu.length;i++){
			if(!menuids.equals("") && menuids.indexOf("&"+menu[i]+"&")<0){
				role2menu.setMenuId(Integer.parseInt(menu[i]));
				sysRole2MenuMapper.insert(role2menu);	
			}else if(menuids.equals("")){
			  role2menu.setMenuId(Integer.parseInt(menu[i]));
			  sysRole2MenuMapper.insert(role2menu);

			}
		}		
	}
	
	@Override
	public List<SysRole2Menu> getRole2Menus(String roleid) throws SystemException, BusinessException{
		SysRole2MenuExample example = new SysRole2MenuExample();
		SysRole2MenuExample.Criteria cr = example.createCriteria();
		cr.andRoleIdEqualTo(Integer.parseInt(roleid));
		String datenow = DateUtil.getDateString("yyyy-MM-dd");
		cr.andAeadTimeGreaterThan(DateUtil.getTimestamp(datenow));
		example.setOrderByClause("MENU_ID ASC");
		return sysRole2MenuMapper.selectByExample(example);
	}
	
	@Override
	public Map getUserRole(Integer userId) throws SystemException, BusinessException{
		Map userrole = null;
		SysUser2RoleExample example = new SysUser2RoleExample();
		example.setDistinct(true);
		example.createCriteria().andUserIdEqualTo(userId);
		List<SysUser2Role> userroles = sysUser2RoleMapper.selectByExample(example);
		String roleids = "";
		String roletrename ="";
		List<Integer> roles = new ArrayList<Integer>();
		if(userroles!=null && userroles.size()>0){
			for(int i=0;i<userroles.size();i++){
				SysUser2Role role = userroles.get(i);
                roleids = roleids + role.getRoleId()+";";
                roles.add(role.getRoleId());
			}
		}
		if(!roleids.equals("")){
			SysRoleExample example2 = new SysRoleExample();
			example2.createCriteria().andRoleIdIn(roles);
			List<SysRole> ronames = sysRoleMapper.selectByExample(example2);
			if(ronames!=null && ronames.size()>0){
				for(int j=0;j<ronames.size();j++){
					SysRole obj = ronames.get(j);
					roletrename = roletrename+obj.getRoleName()+";";
				}
			}			
		}
		if(!roleids.equals("")){
		  roleids = roleids.substring(0, roleids.length()-1);
		  userrole = new HashMap();
		  userrole.put("roleids", roleids);
		}
		if(!roletrename.equals("")){
		  roletrename = roletrename.substring(0, roletrename.length()-1);
		  userrole.put("rolenames", roletrename);
		}
	
		return userrole;
	}
	
	@Override
	public void insertUser2Role(Integer userId,String roleId) throws SystemException, BusinessException{
		SysUser2Role userrole = new SysUser2Role();
		userrole.setUserId(userId);
		String[] roles = roleId.split(";");
		for(int i=0;i<roles.length;i++){
			userrole.setRoleId(Integer.parseInt(roles[i]));
			sysUser2RoleMapper.insertSelective(userrole);
		}
	}
	
	@Override
	public void updateUser2Role(Integer userId,String roleId) throws SystemException, BusinessException{
		//先删除原有的
		SysUser2RoleExample example = new SysUser2RoleExample();
		example.createCriteria().andUserIdEqualTo(userId);
		sysUser2RoleMapper.deleteByExample(example);
		
		//添加新的
		SysUser2Role userrole = new SysUser2Role();
		userrole.setUserId(userId);
		String[] roles = roleId.split(";");
		for(int i=0;i<roles.length;i++){
			userrole.setRoleId(Integer.parseInt(roles[i]));
			sysUser2RoleMapper.insertSelective(userrole);
		}
	}
}
