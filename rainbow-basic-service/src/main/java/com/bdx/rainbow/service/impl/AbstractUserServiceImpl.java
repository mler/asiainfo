package com.bdx.rainbow.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.tree.DefaultTree;
import com.bdx.rainbow.common.util.Encrypt;
import com.bdx.rainbow.entity.sys.SysDeptTree;
import com.bdx.rainbow.entity.sys.SysUser;
import com.bdx.rainbow.entity.sys.SysUserExample;
import com.bdx.rainbow.entity.sys.SysUserExt;
import com.bdx.rainbow.entity.sys.SysUserExtExample;
import com.bdx.rainbow.entity.sys.SysUserModel;
import com.bdx.rainbow.mapper.sys.SysUser2RoleMapper;
import com.bdx.rainbow.mapper.sys.SysUserExtMapper;
import com.bdx.rainbow.mapper.sys.SysUserMapper;
import com.bdx.rainbow.service.IDeptService;
import com.bdx.rainbow.service.IUserService;

public abstract class AbstractUserServiceImpl implements IUserService {
	protected static final Logger log = LoggerFactory.getLogger(AbstractUserServiceImpl.class);
	@Autowired
	protected SysUserMapper sysUserMapper;
	@Autowired
	protected SysUserExtMapper sysUserExtMapper;
	@Autowired
	protected SysUser2RoleMapper sysUser2RoleMapper;
	@Autowired
	private IDeptService deptService;
	
	/**
	 * 用于注册判断，对登录名、移动号码、EMAIL的唯一验证，不存在返回false，否则返回true
	 * @param where
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	protected boolean checkSysUserExist(SysUserExample where) throws SystemException, BusinessException {
		int count = sysUserMapper.countByExample(where);
		return count == 0 ? false : true;
	}
	
	@Override
	public Integer insertUser(SysUser record) throws SystemException,
			BusinessException {
		record.setUserStatus(Constants.COMMON_STATUS_VALID);
		if (StringUtils.isEmpty(record.getLoginPwd())) {
			record.setLoginPwd(Encrypt.encode(Constants.LOGPWD, Encrypt.MD5, false));
		}
		return sysUserMapper.insertSelective(record);
	}

	@Override
	public int updateUser(SysUser record) throws SystemException,
			BusinessException {
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<SysUser> getUsers(SysUser obj, Integer start, Integer count,SysUser userlogin)
			throws SystemException, BusinessException {
		SysUserExample where = getCondition(obj);
		List<Integer> deptIds = getDeptIds(userlogin);
		if (deptIds != null) {
			where.getOredCriteria().get(0).andDeptIdIn(deptIds);
		}
		if(start != null) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(count);
		}
		return sysUserMapper.selectByExample(where);
	}
	
	@Override
	public List<SysUser> getUsers(SysUser obj, Integer start, Integer count)
			throws SystemException, BusinessException {
		SysUserExample where = getCondition(obj);
		if(start != null) {
			where.setLimitClauseStart(start);
			where.setLimitClauseCount(count);
		}
		return sysUserMapper.selectByExample(where);
	}

	public SysUserModel getUserAndExtById(Integer userId)
			throws SystemException, BusinessException {
		SysUser obj = getUserById(userId);
		if (obj != null) {
			SysUserModel sum = new SysUserModel();
			sum.setSu(obj);
			sum.setSue(getUserExtById(userId));
			return sum;
		}
		return null;
	}

	@Override
	public SysUser getUserById(Integer userId) throws SystemException,
			BusinessException {
		return sysUserMapper.selectByPrimaryKey(userId);
	}

	@Override
	public List<SysUserExt> getUserExtById(Integer userId)
			throws SystemException, BusinessException {
		SysUserExtExample example = new SysUserExtExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return sysUserExtMapper.selectByExample(example);
	}

	@Override
	public SysUserExt getUserExtByIdAndKey(Integer userId, String key)
			throws SystemException, BusinessException {
		SysUserExtExample example = new SysUserExtExample();
		example.createCriteria().andUserIdEqualTo(userId).andExtKeyEqualTo(key);
		List<SysUserExt> s = sysUserExtMapper.selectByExample(example);
		if (s.isEmpty())
			return null;
		return s.get(0);
	}

	@Override
	public int updateUserStatusById(Integer userId, String userStatus, Integer updator)
			throws SystemException, BusinessException {
		SysUser record = new SysUser();
		record.setUserId(userId);
		record.setUserStatus(userStatus);
		record.setUpdater(updator);
		record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return 	sysUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public boolean checkUserPWDById(Integer userId, String oldPWD)
			throws SystemException, BusinessException {
		SysUser obj = getUserById(userId);
		if (obj == null)
			return false;
		return StringUtils.equals(obj.getLoginPwd(), Encrypt.encode(oldPWD, Encrypt.MD5, false));
//		return StringUtils.equals(obj.getLoginPwd(), oldPWD);
	}
	@Override
	public void insertUserExt(SysUserExt userExt) throws SystemException,
			BusinessException {
		sysUserExtMapper.insert(userExt);
	}

	@Override
	public int updateUserExt(SysUserExt userExt) throws SystemException,
			BusinessException {
		SysUserExtExample example = new SysUserExtExample();
		example.createCriteria().andUserIdEqualTo(userExt.getUserId()).andExtKeyEqualTo(userExt.getExtKey());
		return sysUserExtMapper.updateByExample(userExt, example);
	}

	@Override
	public int countUser(SysUser obj,SysUser userlogin) throws SystemException,
			BusinessException {
		SysUserExample where = getCondition(obj);
		List<Integer> deptIds = getDeptIds(userlogin);
		if (deptIds != null) {
			where.getOredCriteria().get(0).andDeptIdIn(deptIds);
		}
		return sysUserMapper.countByExample(where);
	}
	
	@Override
	public int countUser(SysUser obj) throws SystemException,
		BusinessException {
		SysUserExample where = getCondition(obj);
		
		return sysUserMapper.countByExample(where);
	}

	@Override
	public int countUserExtByIdAndKey(Integer userId, String extKey)
			throws SystemException, BusinessException {
		SysUserExtExample where = new SysUserExtExample();
		where.createCriteria().andUserIdEqualTo(userId).andExtKeyEqualTo(extKey);
		return sysUserExtMapper.countByExample(where);
	}

	@Override
	public boolean isThisLoginNameExsit(String userLoginName)
			throws SystemException, BusinessException {
		SysUserExample where = new SysUserExample();
		where.createCriteria().andUserLoginNameEqualTo(userLoginName);
		if (this.checkSysUserExist(where))
			return true;
		else if (checkUserByMobilePhone(userLoginName))
			return true;
		else if (checkUserByEmail(userLoginName))
			return true;
		return false;
	}

	@Override
	public boolean checkUserByMobilePhone(String mobilePhone)
			throws SystemException, BusinessException {
		SysUserExample where = new SysUserExample();
		where.createCriteria().andMobilePhoneEqualTo(mobilePhone);
		return this.checkSysUserExist(where);
	}

	@Override
	public boolean checkUserByEmail(String email) throws SystemException,
			BusinessException {
		SysUserExample where = new SysUserExample();
		where.createCriteria().andEmailEqualTo(email);
		return this.checkSysUserExist(where);
	}
	
	private SysUserExample getCondition (SysUser obj) {
		SysUserExample where = new SysUserExample();
		if (obj != null) {
			SysUserExample.Criteria cr = where.createCriteria();
			//用户管理时，排除自己
			if (obj.getUserId() != null && StringUtils.isNotEmpty(obj.getUserId().toString())) {
				cr.andUserIdNotEqualTo(obj.getUserId());
			}
			if(obj.getDeptId() != null && StringUtils.isNotEmpty(obj.getDeptId().toString())) {
				cr.andDeptIdEqualTo(obj.getDeptId());
			}
			if(obj.getUserName() != null && StringUtils.isNotEmpty(obj.getUserName())) {
				cr.andUserNameLike("%" + obj.getUserName() + "%");
			}
			if(obj.getUserLoginName() != null && StringUtils.isNotEmpty(obj.getUserLoginName())) {
				cr.andUserLoginNameLike("%" + obj.getUserLoginName() + "%");
			}
			if(obj.getEmail() != null && StringUtils.isNotEmpty(obj.getEmail())) {
				cr.andEmailEqualTo(obj.getEmail());
			}
			if(obj.getUserType() != null && StringUtils.isNotEmpty(obj.getUserType())) {
				cr.andUserStatusEqualTo(obj.getUserType());
			}
			if(obj.getUserStatus() != null && StringUtils.isNotEmpty(obj.getUserStatus())) {
				cr.andUserStatusEqualTo(obj.getUserStatus());
			}
		}
		return where;
	}
	
	private List<Integer> getDeptIds(SysUser user) {
		//TODO 从session里面获取缓存内容
		
		//SysUser user = RequestContext.getUser().getUser();
		if (StringUtils.equals(Constants.USERTYPES0, user.getUserType())) {
			return null;
		}
		
		
//		if(RequestContext.getRequest().getSession().getAttribute("DEPTIDS") != null) {
//			return (List<Integer>) RequestContext.getRequest().getSession().getAttribute("DEPTIDS");
//		}
		
		Integer deptId = user.getDeptId();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("deptStatus", "1");
		DefaultTree<SysDeptTree> tree = null;
		List<Integer> deptIds = new ArrayList<Integer>();
		try {
			tree = deptService.getDeptTree(condition);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if (tree != null) {
			List<DefaultTree<SysDeptTree>> s = new ArrayList<DefaultTree<SysDeptTree>>();
			tree.getNodes(s, deptId.toString());
			for(DefaultTree<SysDeptTree> dt:s) {
				deptIds.add(dt.getObject().getDeptId());
			}
//			RequestContext.getRequest().getSession().setAttribute("DEPTIDS", deptIds);
			return deptIds;
		}
		return null;
	}

}
