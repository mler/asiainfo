package com.bdx.rainbow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.basic.mysql.TMappLogon;
import com.bdx.rainbow.entity.basic.mysql.TMappUser;
import com.bdx.rainbow.entity.basic.mysql.TMappUserExample;
import com.bdx.rainbow.entity.sys.IUserInfo;
import com.bdx.rainbow.entity.sys.SysLogon;
import com.bdx.rainbow.entity.sys.SysMenuTree;
import com.bdx.rainbow.entity.sys.SysRole;
import com.bdx.rainbow.entity.sys.SysUser;
import com.bdx.rainbow.entity.sys.SysUserExt;
import com.bdx.rainbow.entity.sys.SysUserInfo;
import com.bdx.rainbow.mapper.basic.mysql.TMappLogonMapper;
import com.bdx.rainbow.mapper.basic.mysql.TMappUserMapper;
import com.bdx.rainbow.mapper.sys.SysPowerCommMapper;
import com.bdx.rainbow.service.IPhoneUserService;

@Service("phoneUserService")
@Transactional
public class PhoneUserServiceImpl extends UserServiceImpl implements IPhoneUserService{
	
	@Autowired
	private TMappUserMapper tMappUserMapper;
	
	@Autowired
	private TMappLogonMapper tMappLogonMapper;
	
	/**
	 * app登录：usertype: 2修理厂主账户,21修理厂子账户  ,3地推管理,31地推人员
	 */
	@Override
	public IUserInfo mappLogin(String login_name, String pwd,
			Map<String, Object> sysLogon) throws SystemException,
			BusinessException{
		IUserInfo sysUserInfo = new SysUserInfo();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userLoginName", login_name);
		SysUser sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
	
		if(sysUser != null) {
			boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), pwd);	
			if (pwdisright) {
				if (!StringUtils.equals(sysUser.getUserStatus(), Constants.COMMON_STATUS_VALID)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
				}				
				
				sysUserInfo.setUser(sysUser);
				sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
				condition.clear();
				/**
				condition.put("userId", sysUser.getUserId());
				List<SysRole> uRoles = sysPowerCommMapper.getRolesByUserId(condition);
				if(uRoles.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_POWER_NO_ROLE.getCode(), "用户未绑定角色，请联系平台管理员！"));
				sysUserInfo.setuRoles(uRoles);
				condition.put("menuStatus", Constants.COMMON_STATUS_VALID);
				List<SysMenuTree> uMenus = sysPowerCommMapper.getMenusByUserId(condition);
				if(uMenus.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.ROLE_NO_MENU.getCode(), "用户的角色未绑定有效的菜单，请联系平台管理员！"));
				List<SysMenuTree> sysMenus = new ArrayList<SysMenuTree>();
				for (SysMenuTree smt:uMenus) {
					if (StringUtils.isNotEmpty(smt.getSysId()))
					{
						if (smt.getSysId().startsWith("phone_")) {
							sysMenus.add(smt);
						}
					}
				}
				sysUserInfo.setuMenus(sysMenus);**/
			} else {
				throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.PWD_ERROR.getCode(), "登录密码不正确！"));
			}
			
			/**
			 * 登录日志操作
			 */
			SysLogon sl = super.doLogonLog(login_name, sysUser, sysLogon);
			if (sl != null)
				sysUserInfo.setLastLogonTime(sl.getLogonTime());
		} else {
			throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户不存在！"));
		}
		return sysUserInfo;
	}
	
	
	@Override
	public Integer createUser(SysUser obj, List<SysUserExt> objExts)
			throws SystemException, BusinessException {
		Integer userId = this.insertUser(obj);
		if (objExts != null) {
			for(SysUserExt ext : objExts) {
				ext.setUserId(userId);
				this.insertUserExt(ext);
			}
		}
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userId", obj.getUserId());
		condition.put("userType", obj.getUserType());
		sysPowerCommMapper.insertUser2RoleByUserType(condition);

		return userId;
	}


	@Override
	public TMappUser mappLogin1(String login_name, String pwd,
			Map<String, Object> sysLogon) throws SystemException,
			BusinessException {
		TMappUser user = getMappUserByLoginName(login_name);
		if(user != null) {
			boolean pwdisright = StringUtils.equals(user.getLoginPwd(), pwd);	
			if (pwdisright) {
				if (!StringUtils.equals(user.getUserStatus(), Constants.COMMON_STATUS_VALID)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
				}				
				
			} else {
				throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.PWD_ERROR.getCode(), "登录密码不正确！"));
			}
			
			/**
			 * 登录日志操作
			 */
			insertMappLogin(user, sysLogon);
		} else {
			throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户不存在！"));
		}
		return user;
		
	}
	
	private TMappUserExample converCondition(TMappUser user){
		TMappUserExample example = new TMappUserExample();
		TMappUserExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(user.getUserLoginName())) {
			criteria.andUserLoginNameEqualTo(user.getUserLoginName());
		}
		if (StringUtils.isNotEmpty(user.getEmail())) {
			criteria.andEmailEqualTo(user.getEmail());
		}
		if (StringUtils.isNotEmpty(user.getUserName())) {
			criteria.andUserNameEqualTo(user.getUserName());
		}
		if (StringUtils.isNotEmpty(user.getMobilePhone())) {
			criteria.andMobilePhoneEqualTo(user.getMobilePhone());
		}
		if (StringUtils.isNotEmpty(user.getUserStatus())) {
			criteria.andUserStatusEqualTo(user.getUserStatus());
		}
		if (StringUtils.isNotEmpty(user.getUserType())) {
			criteria.andUserTypeEqualTo(user.getUserType());
		}
		return example;
	}


	@Override
	public TMappUser getMappUserByLoginName(String loginName)
			throws SystemException, BusinessException {
		TMappUser condition = new TMappUser();
		condition.setUserLoginName(loginName);
//		condition.setUserStatus(Constants.COMMON_STATUS_VALID);
		
		TMappUserExample example = converCondition(condition);
		List<TMappUser> users = tMappUserMapper.selectByExample(example);
		if (users == null || users.size() == 0) {
			return null;
		}
		
		return users.get(0);
	}


	@Override
	public void insertMappLogin(TMappUser user,Map<String, Object> sysLogon) throws SystemException,
			BusinessException {
		TMappLogon logon = new TMappLogon();
		logon.setLogonName(user.getUserLoginName());
		logon.setDeptId(user.getDeptId());
		logon.setUserType(user.getUserType());
		logon.setLogonTime(DateUtil.getCurrent());
		logon.setLogonIp(sysLogon.get(Constants.SYS_LOGON_LOGONIP) != null ? sysLogon.get(Constants.SYS_LOGON_LOGONIP).toString() : "");
		logon.setLogonMac(sysLogon.get(Constants.SYS_LOGON_LOGONMAC) != null ? sysLogon.get(Constants.SYS_LOGON_LOGONMAC).toString() : "");
		logon.setServerIp(sysLogon.get(Constants.SYS_LOGON_SERVERIP) != null ? sysLogon.get(Constants.SYS_LOGON_SERVERIP).toString() : "");
		
		logon.setLogonversion(sysLogon.get(Constants.SYS_LOGON_VERSION) != null ? sysLogon.get(Constants.SYS_LOGON_VERSION).toString() : "");
		logon.setLogonmodel(sysLogon.get(Constants.SYS_LOGON_MODEL) != null ? sysLogon.get(Constants.SYS_LOGON_MODEL).toString() : "");
		logon.setLogonimei(sysLogon.get(Constants.SYS_LOGON_IMEI) != null ? sysLogon.get(Constants.SYS_LOGON_IMEI).toString() : "");
		logon.setLogonbrand(sysLogon.get(Constants.SYS_LOGON_BRAND) != null ? sysLogon.get(Constants.SYS_LOGON_BRAND).toString() : "");
		logon.setLogonos(sysLogon.get(Constants.SYS_LOGON_OS) != null ? sysLogon.get(Constants.SYS_LOGON_OS).toString() : "");
		tMappLogonMapper.insert(logon);
	}


	@Override
	public boolean ifloginNameExists(String loginName) throws SystemException,
			BusinessException {
		TMappUser user = getMappUserByLoginName(loginName);
		boolean flag = user == null?false:true;
		return flag;
	}


	@Override
	public void createMappUser(TMappUser user) throws SystemException,
			BusinessException {
		if (StringUtils.isEmpty(user.getUserStatus())) {
			user.setUserStatus(Constants.COMMON_STATUS_VALID);
		}
		user.setCreateTime(DateUtil.getCurrent());
		tMappUserMapper.insertSelective(user);
	}
	
	
}
