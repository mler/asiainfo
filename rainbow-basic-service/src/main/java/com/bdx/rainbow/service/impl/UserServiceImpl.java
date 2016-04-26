package com.bdx.rainbow.service.impl;

import java.sql.Timestamp;
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
import com.bdx.rainbow.common.util.Encrypt;
import com.bdx.rainbow.common.util.SequenceUtil;
import com.bdx.rainbow.entity.sys.IUserInfo;
import com.bdx.rainbow.entity.sys.SysDept;
import com.bdx.rainbow.entity.sys.SysLogon;
import com.bdx.rainbow.entity.sys.SysLogonExample;
import com.bdx.rainbow.entity.sys.SysMenuTree;
import com.bdx.rainbow.entity.sys.SysRole;
import com.bdx.rainbow.entity.sys.SysUser;
import com.bdx.rainbow.entity.sys.SysUserExample;
import com.bdx.rainbow.entity.sys.SysUserExt;
import com.bdx.rainbow.entity.sys.SysUserExtExample;
import com.bdx.rainbow.entity.sys.SysUserInfo;
import com.bdx.rainbow.mapper.sys.SysDeptMapper;
import com.bdx.rainbow.mapper.sys.SysLogonMapper;
import com.bdx.rainbow.mapper.sys.SysPowerCommMapper;
import com.bdx.rainbow.mapper.sys.SysUserExtMapper;
import com.bdx.rainbow.mapper.sys.SysUserMapper;

@Service("userService")
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl extends AbstractUserServiceImpl{
	@Autowired
	protected SysDeptMapper sysDeptMapper;
	@Autowired
	protected SysLogonMapper sysLogonMapper;
	@Autowired
	protected SysPowerCommMapper sysPowerCommMapper;
	@Autowired
	protected SysUserExtMapper sysUserExtMapper;
	@Autowired
	protected SysUserMapper sysUserMapper;
	

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
		
//		sysPowerCommMapper.insertUser2RoleByUserType(condition);

		return userId;
	}
	
	@Override
	public int updateUserByO(SysUser obj)
			throws SystemException, BusinessException {
		/*Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("userId", obj.getUserId());
		condition.put("bussin_type", "E");
		sysPowerCommMapper.insertUserHis(condition);*/
		obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		int count = this.updateUser(obj);
		return count;
	}

	@Override
	public int updateUser(SysUser obj, List<SysUserExt> objExts)
			throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
//		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", SequenceUtil.getSequence());
		condition.put("userId", obj.getUserId());
		condition.put("bussin_type", "E");
		sysPowerCommMapper.insertUserHis(condition);
		obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		int count = this.updateUser(obj);
		if (objExts != null) {
			for(SysUserExt ext : objExts) {
				ext.setUserId(obj.getUserId());
				if(this.countUserExtByIdAndKey(obj.getUserId(), ext.getExtKey()) == 0) {
					this.insertUserExt(ext);
				} else {
					condition.put("key", ext.getExtKey());
					sysPowerCommMapper.insertUserExtHis(condition);
					this.updateUserExt(ext);
				}
			}
		}
		else {
			sysPowerCommMapper.insertUserExtHis(condition);
			SysUserExtExample example = new SysUserExtExample();
			example.createCriteria().andUserIdEqualTo(obj.getUserId());
			sysUserExtMapper.deleteByExample(example);
		}
		return count;
	}

	@Override
	public void updateUserExts(Integer userId, List<SysUserExt> objExts) throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
//		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", SequenceUtil.getSequence());
		condition.put("userId", userId);
		condition.put("bussin_type", "E");
		if (objExts != null) {
			for(SysUserExt ext : objExts) {
				ext.setUserId(userId);
				if(this.countUserExtByIdAndKey(userId, ext.getExtKey()) == 0) {
					this.insertUserExt(ext);
				} else {
					condition.put("key", ext.getExtKey());
					sysPowerCommMapper.insertUserExtHis(condition);
					this.updateUserExt(ext);
				}
			}
		} else {
			sysPowerCommMapper.insertUserExtHis(condition);
			SysUserExtExample example = new SysUserExtExample();
			example.createCriteria().andUserIdEqualTo(userId);
			sysUserExtMapper.deleteByExample(example);
		}
	}
	
	@Override
	public boolean checkRole(IUserInfo sysUserInfo, Integer roleId) throws SystemException,
			BusinessException {
		SysUserInfo userInfo = (SysUserInfo) sysUserInfo;
		List<SysRole> uRoles = userInfo.getuRoles();
		for(SysRole role:uRoles) {
			if (roleId == role.getRoleId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkMenu(IUserInfo sysUserInfo, Integer menuId) throws SystemException,
			BusinessException {
		SysUserInfo userInfo = (SysUserInfo) sysUserInfo;
		List<SysMenuTree> uMenus = userInfo.getuMenus();
		for(SysMenuTree menu:uMenus) {
			if (menuId == menu.getMenuId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int changePWD(Integer userId, String newPwd) throws SystemException,
			BusinessException {
		SysUser record = new SysUser();
		record.setUserId(userId);
		record.setLoginPwd(newPwd);
		record.setChangeTime(new Timestamp(System.currentTimeMillis()));
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 登录支撑后台用户：欧顺平台管理员 0、欧顺平台普通用户(指欧顺的支撑人员),2平台普通员工,自有渠道员工4
	 * 返回IUserInfo接口对象，若返回为null时，代表用户不存在。
	 * SysUserInfo包含信息：密码是否正确，sys_user及sys_user_ext的信息,用户绑定的角色的信息，用户拥有的菜单信息
	 * 增加登录日志SYS_LOGON @MODIFY DAY 2013-05-30
	 */
	@Override
	public IUserInfo login(String login_name, String pwd, Map<String, Object> sysLogon) throws SystemException,
			BusinessException {
		IUserInfo sysUserInfo = new SysUserInfo();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userLoginName", login_name);
		SysUser sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		if(sysUser == null) {
			condition.remove("userLoginName");
			condition.put("email", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if(sysUser == null) {
			condition.remove("email");
			condition.put("mobile_phone", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if(sysUser != null) {
			boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), Encrypt.encode(pwd, Encrypt.MD5, false));	
			if (pwdisright) {
				if (!StringUtils.equals(sysUser.getUserStatus(), Constants.COMMON_STATUS_VALID)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
				}				
				
				if (StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES0) || StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES2) || StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES4)) {
					log.debug("欧顺平台管理员、欧顺平台普通用户(指欧顺的支撑人员)登录验证...");
				} else {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
				}
				SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
				if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_1)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
				}
				if (sysDept.getProvince() != null)
					sysUserInfo.setProvince(sysDept.getProvince());
				if (sysDept.getAreaid() != null)
					sysUserInfo.setAreaid(sysDept.getAreaid());
				if (sysDept.getCountyid() != null)
					sysUserInfo.setCountyid(sysDept.getCountyid());
				sysUserInfo.setUser(sysUser);
				sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
				condition.clear();
				condition.put("userId", sysUser.getUserId());
				List<SysRole> uRoles = sysPowerCommMapper.getRolesByUserId(condition);
				if(uRoles.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_POWER_NO_ROLE.getCode(), "用户未绑定角色，请联系平台管理员！"));
				sysUserInfo.setuRoles(uRoles);
				condition.put("menuStatus", "1");//菜单状态正常
				List<SysMenuTree> uMenus = sysPowerCommMapper.getMenusByUserId(condition);
				if(uMenus.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.ROLE_NO_MENU.getCode(), "用户的角色未绑定有效的菜单，请联系平台管理员！"));
				sysUserInfo.setuMenus(uMenus);
			} else {
				throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.PWD_ERROR.getCode(), "登录密码不正确！"));
			}
			
			/**
			 * 登录日志操作
			 */
				SysLogon sl = doLogonLog(login_name, sysUser, sysLogon);
				if (sl != null)
					sysUserInfo.setLastLogonTime(sl.getLogonTime());
			
		} else {
			throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户不存在！"));
		}
		return sysUserInfo;
	}

	@Override
	public String forgotPWD(String email) throws SystemException, BusinessException {
		SysUserExample where = new SysUserExample();
		where.createCriteria().andEmailEqualTo(email);
		List<SysUser> users = sysUserMapper.selectByExample(where);
		if (users.isEmpty()) {
			return "该邮箱不存在!";
		}
		if (users.size() == 1) {
			return "";	
		}  
		return "";
	}

	@Override
	public void updateUserStatusByUserIds(List<Integer> userIds, String userStatus, Integer updator) 
			throws SystemException, BusinessException 
	{
		/*Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("bussin_type", "A");*/
		for(Integer userId : userIds) 
		{
			/*condition.put("userId", userId);
			sysPowerCommMapper.insertUserHis(condition);*/
			this.updateUserStatusById(userId, userStatus, updator);
		}
	}

	@Override
	public IUserInfo businessLogin(String login_name, String pwd,
			Map<String, Object> sysLogon) throws SystemException,
			BusinessException {
		IUserInfo sysUserInfo = new SysUserInfo();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userLoginName", login_name);
		SysUser sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		if(sysUser == null) {
			condition.remove("userLoginName");
			condition.put("email", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if(sysUser == null) {
			condition.remove("email");
			condition.put("mobile_phone", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if(sysUser != null) {
			boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), Encrypt.encode(pwd, Encrypt.MD5, false));	
			if (pwdisright) {
				if (!StringUtils.equals(sysUser.getUserStatus(), Constants.COMMON_STATUS_VALID)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
				}	
				if (StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES1) || StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES5)) {
					log.debug("电商/个人登录验证...");
				} else {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
				}
				SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
				if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_1)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
				}
				if (sysDept.getProvince() != null)
					sysUserInfo.setProvince(sysDept.getProvince());
				if (sysDept.getAreaid() != null)
					sysUserInfo.setAreaid(sysDept.getAreaid());
				if (sysDept.getCountyid() != null)
					sysUserInfo.setCountyid(sysDept.getCountyid());
				sysUserInfo.setUser(sysUser);
				sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
			} else {
				throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.PWD_ERROR.getCode(), "登录密码不正确！"));
			}
			
			/**
			 * 登录日志操作
			 */
			SysLogon sl = doLogonLog(login_name, sysUser, sysLogon);
			if (sl != null)
				sysUserInfo.setLastLogonTime(sl.getLogonTime());
		} else {
			throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户不存在！"));
		}
		return sysUserInfo;
	}

	@Override
	public IUserInfo phoneLogin(String login_name, String pwd,
			Map<String, Object> sysLogon) throws SystemException,
			BusinessException {
		IUserInfo sysUserInfo = new SysUserInfo();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userLoginName", login_name);
		SysUser sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		if(sysUser == null) {
			condition.remove("userLoginName");
			condition.put("email", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if(sysUser == null) {
			condition.remove("email");
			condition.put("mobile_phone", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if(sysUser != null) {
			boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), pwd);	
			if (pwdisright) {
				if (!StringUtils.equals(sysUser.getUserStatus(), Constants.COMMON_STATUS_VALID)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
				}				
				
				if (StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES1) || StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES3)) {
					log.debug("加盟渠道管理员、加盟渠道普通用户登录验证...");
				} else {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
				}
				SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
				if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_1)) {
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
				}
				if (sysDept.getProvince() != null)
					sysUserInfo.setProvince(sysDept.getProvince());
				if (sysDept.getAreaid() != null)
					sysUserInfo.setAreaid(sysDept.getAreaid());
				if (sysDept.getCountyid() != null)
					sysUserInfo.setCountyid(sysDept.getCountyid());
				sysUserInfo.setUser(sysUser);
				sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
				condition.clear();
				condition.put("userId", sysUser.getUserId());
				List<SysRole> uRoles = sysPowerCommMapper.getRolesByUserId(condition);
				if(uRoles.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_POWER_NO_ROLE.getCode(), "用户未绑定角色，请联系平台管理员！"));
				sysUserInfo.setuRoles(uRoles);
				List<SysMenuTree> uMenus = sysPowerCommMapper.getMenusByUserId(condition);
				if(uMenus.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.ROLE_NO_MENU.getCode(), "用户的角色未绑定有效的菜单，请联系平台管理员！"));
				List<SysMenuTree> sysMenus = new ArrayList<SysMenuTree>();
				for (SysMenuTree smt:uMenus) {
					if (smt.getMenuDoman() != null)
					{
						if (StringUtils.equals(Constants.P_MENUDOMAN, smt.getMenuDoman())) {
							sysMenus.add(smt);
						}
					}
				}
				sysUserInfo.setuMenus(sysMenus);
			} else {
				throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.PWD_ERROR.getCode(), "登录密码不正确！"));
			}
			
			/**
			 * 登录日志操作
			 */
			SysLogon sl = doLogonLog(login_name, sysUser, sysLogon);
			if (sl != null)
				sysUserInfo.setLastLogonTime(sl.getLogonTime());
		} else {
			throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户不存在！"));
		}
		return sysUserInfo;
	}
	
	protected SysLogon doLogonLog(String login_name, SysUser sysUser, Map<String, Object> sysLogon) {
		/**
		 * 获取前次登录时间
		 */
		SysLogonExample logonWhere = new SysLogonExample();
		logonWhere.createCriteria().andLogonNameEqualTo(login_name);
		logonWhere.setOrderByClause(" logon_time desc ");
		logonWhere.setLimitClauseStart(0);
		logonWhere.setLimitClauseCount(1);
		List<SysLogon> logons = sysLogonMapper.selectByExample(logonWhere);
		
		/**
		 * 增加登录日志
		 */
		if (sysLogon != null) {
			SysLogon logon = new SysLogon();
			logon.setLogonName(login_name);
			logon.setDeptId(sysUser.getDeptId());
			logon.setUserType(sysUser.getUserType());
			logon.setLogonIp(sysLogon.get(Constants.SYS_LOGON_LOGONIP) != null ? sysLogon.get(Constants.SYS_LOGON_LOGONIP).toString() : "");
			logon.setLogonMac(sysLogon.get(Constants.SYS_LOGON_LOGONMAC) != null ? sysLogon.get(Constants.SYS_LOGON_LOGONMAC).toString() : "");
			logon.setServerIp(sysLogon.get(Constants.SYS_LOGON_SERVERIP) != null ? sysLogon.get(Constants.SYS_LOGON_SERVERIP).toString() : "");
			
			logon.setLogonversion(sysLogon.get(Constants.SYS_LOGON_VERSION) != null ? sysLogon.get(Constants.SYS_LOGON_VERSION).toString() : "");
			logon.setLogonmodel(sysLogon.get(Constants.SYS_LOGON_MODEL) != null ? sysLogon.get(Constants.SYS_LOGON_MODEL).toString() : "");
			logon.setLogonimei(sysLogon.get(Constants.SYS_LOGON_IMEI) != null ? sysLogon.get(Constants.SYS_LOGON_IMEI).toString() : "");
			logon.setLogonbrand(sysLogon.get(Constants.SYS_LOGON_BRAND) != null ? sysLogon.get(Constants.SYS_LOGON_BRAND).toString() : "");
			logon.setLogonos(sysLogon.get(Constants.SYS_LOGON_OS) != null ? sysLogon.get(Constants.SYS_LOGON_OS).toString() : "");
			//TODO mler session 处理
			
//			ISession session = RequestContext.getSession();
//			if (session != null) {
//				String sId = session.getId();
//				if (sId != null)
//					logon.setLogonSid(sId);
//			}
//			logon.setLogondevice(sysLogon.get(Constants.SYS_LOGON_LOGONDEVICE) != null ? sysLogon.get(Constants.SYS_LOGON_LOGONDEVICE).toString() : "");
//			logon.setLogonsys(sysLogon.get(Constants.SYS_LOGON_LOGONSYS) != null ? sysLogon.get(Constants.SYS_LOGON_LOGONSYS).toString() : "");
			
			sysLogonMapper.insert(logon);
		}
		
		if (logons != null && logons.size() > 0)
			return logons.get(0);
		else
			return null;
	}
	
	@Override
	public IUserInfo login(String login_name, Map<String, Object> sysLogon)
			throws SystemException, BusinessException {
		IUserInfo sysUserInfo = new SysUserInfo();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("userLoginName", login_name);
		SysUser sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		if (sysUser == null) {
			condition.remove("userLoginName");
			condition.put("email", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if (sysUser == null) {
			condition.remove("email");
			condition.put("mobile_phone", login_name);
			sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
		}
		if (sysUser != null) {
			// boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(),
			// Encrypt.encode(pwd, Encrypt.MD5, false));
			if (true) {
				if (!StringUtils.equals(sysUser.getUserStatus(),
						Constants.COMMON_STATUS_VALID)) {
					throw new BusinessException(new DefaultExceptionCode(
							Constants.ErrorType.USER_STATUS_ERROR.getCode(),
							"用户状态不正常！"));
				}

				if (StringUtils.equals(sysUser.getUserType(),
						Constants.USERTYPES0)
						|| StringUtils.equals(sysUser.getUserType(),
								Constants.USERTYPES2)
						|| StringUtils.equals(sysUser.getUserType(),
								Constants.USERTYPES4)) {
					log.debug("欧顺平台管理员、欧顺平台普通用户(指欧顺的支撑人员)登录验证...");
				} else {
					throw new BusinessException(new DefaultExceptionCode(
							Constants.ErrorType.USER_TYPE_ERROR.getCode(),
							"该用户不能登录该平台！"));
				}
				SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser
						.getDeptId());
				if (!StringUtils.equals(sysDept.getDeptStatus(),
						Constants.DEPT_STATUS_1)) {
					throw new BusinessException(new DefaultExceptionCode(
							Constants.ErrorType.USER_DEPT_ERROR.getCode(),
							Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
				}
				if (sysDept.getProvince() != null)
					sysUserInfo.setProvince(sysDept.getProvince());
				if (sysDept.getAreaid() != null)
					sysUserInfo.setAreaid(sysDept.getAreaid());
				if (sysDept.getCountyid() != null)
					sysUserInfo.setCountyid(sysDept.getCountyid());
				sysUserInfo.setUser(sysUser);
				sysUserInfo
						.setUserExts(this.getUserExtById(sysUser.getUserId()));
				condition.clear();
				condition.put("userId", sysUser.getUserId());
				List<SysRole> uRoles = sysPowerCommMapper
						.getRolesByUserId(condition);
				if (uRoles.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(
							Constants.ErrorType.USER_POWER_NO_ROLE.getCode(),
							"用户未绑定角色，请联系平台管理员！"));
				sysUserInfo.setuRoles(uRoles);
				List<SysMenuTree> uMenus = sysPowerCommMapper
						.getMenusByUserId(condition);
				if (uMenus.isEmpty())
					throw new BusinessException(new DefaultExceptionCode(
							Constants.ErrorType.ROLE_NO_MENU.getCode(),
							"用户的角色未绑定有效的菜单，请联系平台管理员！"));
				sysUserInfo.setuMenus(uMenus);
			} 
			/*else {
				throw new BusinessException(new DefaultExceptionCode(
						Constants.ErrorType.PWD_ERROR.getCode(), "登录密码不正确！"));
			}*/

			/**
			 * 登录日志操作
			 */
			SysLogon sl = doLogonLog(login_name, sysUser, sysLogon);
			if (sl != null)
				sysUserInfo.setLastLogonTime(sl.getLogonTime());

		} else {
			throw new BusinessException(new DefaultExceptionCode(
					Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户不存在！"));
		}
		return sysUserInfo;
	}

}
