package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.Encrypt;
import com.bdx.rainbow.entity.urs.*;
import com.bdx.rainbow.mapper.urs.*;
import com.bdx.rainbow.urs.common.BeanCopyUtil;
import com.bdx.rainbow.urs.common.Constants;
import com.bdx.rainbow.urs.common.DateUtil;
import com.bdx.rainbow.urs.common.SequenceUtil;
import com.bdx.rainbow.urs.entity.IUserInfo;

import com.bdx.rainbow.urs.entity.SysUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends AbstractUserServiceImpl {
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
    @Autowired
    protected SysUser2PlatMapper sysUser2PlatMapper;
    @Autowired
    protected SysPlatfromMapper sysPlatfromMapper;
    @Autowired
    protected  SysUser2RoleMapper sysUser2RoleMapper;


    @Override
    public Integer createUser(SysUser obj, List<SysUserExt> objExts)
            throws SystemException, BusinessException {
        Integer userId = this.insertUser(obj);
        if (objExts != null) {
            for (SysUserExt ext : objExts) {
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
            for (SysUserExt ext : objExts) {
                ext.setUserId(obj.getUserId());
                if (this.countUserExtByIdAndKey(obj.getUserId(), ext.getExtKey()) == 0) {
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
            for (SysUserExt ext : objExts) {
                ext.setUserId(userId);
                if (this.countUserExtByIdAndKey(userId, ext.getExtKey()) == 0) {
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
        List<com.bdx.rainbow.urs.entity.SysRole> uRoles = userInfo.getuRoles();
        for (com.bdx.rainbow.urs.entity.SysRole role : uRoles) {
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
        List<com.bdx.rainbow.urs.entity.SysMenuTree> uMenus = userInfo.getuMenus();
        for (com.bdx.rainbow.urs.entity.SysMenuTree menu : uMenus) {
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


    @Override
    public IUserInfo getUserInfoById(Integer userId, Integer platId) throws SystemException, BusinessException {
      if(userId==null || StringUtils.isBlank(platId.toString())){
          throw new BusinessException(new DefaultExceptionCode("8888", " 用户ID不能为空"));
      }
          if (platId == null || StringUtils.isBlank(platId.toString())) {
              throw new BusinessException(new DefaultExceptionCode("8888", " 当前用户登陆平台ID为空，请传入当前用户登陆平台ID"));
          }

        IUserInfo sysUserInfo = new SysUserInfo();
        List<SysUser2Plat> result=this.getUser2Plats(userId.toString());
        boolean CheckTag=false;
        for (int i=0;i<result.size();i++){
           if(result.get(i).getPlatId()==platId){
               CheckTag=true;
           }
        }
        if(!CheckTag){
            throw new BusinessException(new DefaultExceptionCode("8888", " 输入的平台ID与该用户不匹配"));
        }
        SysUser sysUser=  sysUserMapper.selectByPrimaryKey(userId);
        com.bdx.rainbow.urs.entity.SysUser sysUser1 = new com.bdx.rainbow.urs.entity.SysUser();
        if (sysUser!=null) {
            if (!StringUtils.equals(sysUser.getUserStatus(),
                    Constants.USER_STATUS_NORMAL_1)) {
                throw new BusinessException(new DefaultExceptionCode(
                        Constants.ErrorType.USER_STATUS_ERROR.getCode(),
                        "用户状态不正常！"));
            }
            sysUserInfo.setUserName(sysUser.getUserName());
            SysDept sysDept= sysDeptMapper.selectByPrimaryKey(sysUser
                    .getDeptId());
            if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_NORMAL_1)) {
                throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
            }
            if (sysDept.getProvince() != null)
                sysUserInfo.setProvince(sysDept.getProvince());
            if (sysDept.getAreaid() != null)
                sysUserInfo.setAreaid(sysDept.getAreaid());
            if (sysDept.getCountyid() != null)
                sysUserInfo.setCountyid(sysDept.getCountyid());
            sysUserInfo.setDeptName(sysDept.getDeptName());
            sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
            sysUser.setLoginPwd(null);
            BeanCopyUtil.copyProperties(sysUser, sysUser1);
            sysUserInfo.setUser(sysUser1);


        }

    return sysUserInfo;
}
    @Override
    public IUserInfo login(String login_name, String pwd, Integer platId, Map<String, Object> sysLogon) throws SystemException,
            BusinessException {
        if (platId == null || StringUtils.isBlank(platId.toString())) {
            throw new BusinessException(new DefaultExceptionCode("8888", " 当前用户登陆平台ID为空，请传入当前用户登陆平台ID"));
        }
        if (sysPlatfromMapper.selectByPrimaryKey(platId) == null) {
            throw new BusinessException(new DefaultExceptionCode("8888", " 没有找到当前用户登陆平台ID对应的平台信息，请检查后重新登陆"));
        }
        IUserInfo sysUserInfo = new SysUserInfo();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userLoginName", login_name);
        SysUser sysUser = sysPowerCommMapper.getUserByLoginOrMPOrEmail(condition);
        com.bdx.rainbow.urs.entity.SysUser sysUser1 = new com.bdx.rainbow.urs.entity.SysUser();
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
//            SysUser2PlatExample user2PlatExample=new SysUser2PlatExample();
//            user2PlatExample.createCriteria().andUserIdEqualTo(sysUser.getUserId());
            condition.clear();
            condition.put("userId", sysUser.getUserId());
            condition.put("platStatus", Constants.PLAT_STATUS_NORMAL_1);
            List<SysPlatfrom> user2Plats = sysPowerCommMapper.getPlatsByUserId(condition); //sysUser2PlatMapper.selectByExample(user2PlatExample);

            boolean isPlant = false;
            for (int i = 0; i < user2Plats.size(); i++) {
                if (platId == user2Plats.get(i).getPlatId()) {
                    isPlant = true;
                    break;
                }
            }
            if (!isPlant) {
                throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
            }
            boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), Encrypt.encode(pwd, Encrypt.MD5, false));
            if (!pwdisright){
                pwdisright=StringUtils.equals(sysUser.getLoginPwd(), pwd);
            }
            if (pwdisright) {
                if (!StringUtils.equals(sysUser.getUserStatus(), Constants.USER_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
                }

//				if (StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES_ADMIN_9) ) {
//					log.debug("平台管理员、平台普通用户(指欧顺的支撑人员)登录验证...");
//				} else {
//					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
//				}
                SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
                if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
                }
                if (sysDept.getProvince() != null)
                    sysUserInfo.setProvince(sysDept.getProvince());
                if (sysDept.getAreaid() != null)
                    sysUserInfo.setAreaid(sysDept.getAreaid());
                if (sysDept.getCountyid() != null)
                    sysUserInfo.setCountyid(sysDept.getCountyid());
                sysUser.setLoginPwd(null);
                BeanCopyUtil.copyProperties(sysUser, sysUser1);
                sysUserInfo.setUser(sysUser1);
                sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
                condition.clear();
                condition.put("userId", sysUser.getUserId());
                condition.put("platId", platId);
                List<SysRole> uRoles = sysPowerCommMapper.getRolesByUserId(condition);
                if (uRoles.isEmpty())
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_POWER_NO_ROLE.getCode(), "用户未绑定角色，请联系平台管理员！"));
                List<com.bdx.rainbow.urs.entity.SysRole> roles = new ArrayList<>();
                for (int i = 0; i < uRoles.size(); i++) {
                    com.bdx.rainbow.urs.entity.SysRole role = new com.bdx.rainbow.urs.entity.SysRole();
                    BeanCopyUtil.copyProperties(uRoles.get(i), role);
                    roles.add(role);
                }
                sysUserInfo.setuRoles(roles);
                condition.put("menuStatus", "1");//菜单状态正常
                List<SysMenuTree> uMenus = sysPowerCommMapper.getMenusByUserId(condition);
                List<com.bdx.rainbow.urs.entity.SysMenuTree> menus = new ArrayList<>();
                for (int i = 0; i < uMenus.size(); i++) {
                    com.bdx.rainbow.urs.entity.SysMenuTree menu = new com.bdx.rainbow.urs.entity.SysMenuTree();
                    BeanCopyUtil.copyProperties(uMenus.get(i), menu);
                    menus.add(menu);
                }
                if (uMenus.isEmpty())
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.ROLE_NO_MENU.getCode(), "用户的角色未绑定有效的菜单，请联系平台管理员！"));
                sysUserInfo.setuMenus(menus);
            } else {
                throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.PWD_ERROR.getCode(), "登录用户或密码不正确！"));
            }

            /**
             * 登录日志操作
             */
            SysLogon sl = doLogonLog(login_name, sysUser, sysLogon);
            if (sl != null)
                sysUserInfo.setLastLogonTime(sl.getLogonTime());

        } else {
            throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_NOT_EXSIST.getCode(), "登录用户或密码不正确！"));
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
            throws SystemException, BusinessException {
		Map<String, Object> condition = new HashMap<String, Object>();
		String bussin = SequenceUtil.getSequence();
		condition.put("bussin", bussin);
		condition.put("bussin_type", "A");
        for (Integer userId : userIds) {
			condition.put("userId", userId);
			sysPowerCommMapper.insertUserHis(condition);
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
            boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), Encrypt.encode(pwd, Encrypt.MD5, false));
            if (pwdisright) {
                if (!StringUtils.equals(sysUser.getUserStatus(), Constants.USER_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
                }
//				if (StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES1) || StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES5)) {
//					log.debug("电商/个人登录验证...");
//				} else {
//					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
//				}
                SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
                if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
                }
                if (sysDept.getProvince() != null)
                    sysUserInfo.setProvince(sysDept.getProvince());
                if (sysDept.getAreaid() != null)
                    sysUserInfo.setAreaid(sysDept.getAreaid());
                if (sysDept.getCountyid() != null)
                    sysUserInfo.setCountyid(sysDept.getCountyid());
//				sysUserInfo.setUser(sysUser);
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
            boolean pwdisright = StringUtils.equals(sysUser.getLoginPwd(), pwd);
            if (pwdisright) {
                if (!StringUtils.equals(sysUser.getUserStatus(), Constants.USER_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_STATUS_ERROR.getCode(), "用户状态不正常！"));
                }

//				if (StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES1) || StringUtils.equals(sysUser.getUserType(), Constants.USERTYPES3)) {
//					log.debug("加盟渠道管理员、加盟渠道普通用户登录验证...");
//				} else {
//					throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_TYPE_ERROR.getCode(), "该用户不能登录该平台！"));
//				}
                SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser.getDeptId());
                if (!StringUtils.equals(sysDept.getDeptStatus(), Constants.DEPT_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_DEPT_ERROR.getCode(), Constants.ErrorType.USER_DEPT_ERROR.getMsg()));
                }
                if (sysDept.getProvince() != null)
                    sysUserInfo.setProvince(sysDept.getProvince());
                if (sysDept.getAreaid() != null)
                    sysUserInfo.setAreaid(sysDept.getAreaid());
                if (sysDept.getCountyid() != null)
                    sysUserInfo.setCountyid(sysDept.getCountyid());
//				sysUserInfo.setUser(sysUser);
                sysUserInfo.setUserExts(this.getUserExtById(sysUser.getUserId()));
                condition.clear();
                condition.put("userId", sysUser.getUserId());
                List<SysRole> uRoles = sysPowerCommMapper.getRolesByUserId(condition);
                if (uRoles.isEmpty())
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.USER_POWER_NO_ROLE.getCode(), "用户未绑定角色，请联系平台管理员！"));
//				sysUserInfo.setuRoles(uRoles);
                List<com.bdx.rainbow.entity.urs.SysMenuTree> uMenus = sysPowerCommMapper.getMenusByUserId(condition);
                if (uMenus.isEmpty())
                    throw new BusinessException(new DefaultExceptionCode(Constants.ErrorType.ROLE_NO_MENU.getCode(), "用户的角色未绑定有效的菜单，请联系平台管理员！"));
                List<com.bdx.rainbow.entity.urs.SysMenuTree> sysMenus = new ArrayList<com.bdx.rainbow.entity.urs.SysMenuTree>();
//				for (SysMenuTree smt:uMenus) {
//					if (smt.getMenuDoman() != null)
//					{
//						if (StringUtils.equals(Constants.P_MENUDOMAN, smt.getMenuDoman())) {
//							sysMenus.add(smt);
//						}
//					}
//				}
//				sysUserInfo.setuMenus(sysMenus);
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
//		SysLogonExample logonWhere = new SysLogonExample();
//		logonWhere.createCriteria().andLogonNameEqualTo(login_name);
//		logonWhere.setOrderByClause(" logon_time desc ");
//		logonWhere.setLimitClauseStart(0);
//		logonWhere.setLimitClauseCount(1);
//		List<SysLogon> logons = sysLogonMapper.selectByExample(logonWhere);

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
            logon.setLogonTime(DateUtil.getCurrent());

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
            return logon;
        } else {
            return null;
        }

//		if (logons != null && logons.size() > 0)
//			return logons.get(0);
//		else

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
                        Constants.USER_STATUS_NORMAL_1)) {
                    throw new BusinessException(new DefaultExceptionCode(
                            Constants.ErrorType.USER_STATUS_ERROR.getCode(),
                            "用户状态不正常！"));
                }

//				if (StringUtils.equals(sysUser.getUserType(),
//						Constants.USERTYPES0)
//						|| StringUtils.equals(sysUser.getUserType(),
//								Constants.USERTYPES2)
//						|| StringUtils.equals(sysUser.getUserType(),
//								Constants.USERTYPES4)) {
//					log.debug("欧顺平台管理员、欧顺平台普通用户(指欧顺的支撑人员)登录验证...");
//				} else {
//					throw new BusinessException(new DefaultExceptionCode(
//							Constants.ErrorType.USER_TYPE_ERROR.getCode(),
//							"该用户不能登录该平台！"));
//				}
                SysDept sysDept = sysDeptMapper.selectByPrimaryKey(sysUser
                        .getDeptId());
                if (!StringUtils.equals(sysDept.getDeptStatus(),
                        Constants.DEPT_STATUS_NORMAL_1)) {
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
//				sysUserInfo.setUser(sysUser1);
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
//				sysUserInfo.setuRoles(uRoles);
                List<com.bdx.rainbow.entity.urs.SysMenuTree> uMenus = sysPowerCommMapper
                        .getMenusByUserId(condition);
                if (uMenus.isEmpty())
                    throw new BusinessException(new DefaultExceptionCode(
                            Constants.ErrorType.ROLE_NO_MENU.getCode(),
                            "用户的角色未绑定有效的菜单，请联系平台管理员！"));
//				sysUserInfo.setuMenus(uMenus);
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

    @Override
    public List<SysUser2Plat> getUser2Plats(String userId) throws SystemException, BusinessException {

        SysUser2PlatExample example = new SysUser2PlatExample();
        SysUser2PlatExample.Criteria cr = example.createCriteria();
        cr.andUserIdEqualTo(Integer.parseInt(userId));
        example.setOrderByClause("USER_ID ASC");

        return sysUser2PlatMapper.selectByExample(example);
    }

    @Override
    public void insertUser2Plat(String userId, String[] toBedeletedIds, String[] toinsertedIds) throws SystemException, BusinessException {
        for (int i = 0; i < toBedeletedIds.length; i++) {
            SysUser2PlatExample example = new SysUser2PlatExample();
            SysUser2PlatExample.Criteria cr = example.createCriteria();
            cr.andUserIdEqualTo(Integer.parseInt(userId));
            if(toBedeletedIds[i]!=""){
                cr.andPlatIdEqualTo(Integer.parseInt(toBedeletedIds[i]));
                sysUser2PlatMapper.deleteByExample(example);
            }
        }

        for (int i = 0; i < toinsertedIds.length ; i++) {


            SysUser2Plat record = new SysUser2Plat();
            record.setUserId(Integer.parseInt(userId));
            if(toinsertedIds[i]!=""){
                record.setPlatId(Integer.parseInt(toinsertedIds[i]));
                sysUser2PlatMapper.insertSelective(record);
            }else {
                return;

            }

        }


    }

    @Override
    public List<SysUser2Role> getUser2Roles(String userId) throws SystemException, BusinessException {
        SysUser2RoleExample example=new SysUser2RoleExample();
        SysUser2RoleExample.Criteria cr=example.createCriteria();
        cr.andUserIdEqualTo(Integer.parseInt(userId));
        example.setOrderByClause("USER_ID ASC");


        return sysUser2RoleMapper.selectByExample(example);
    }

    @Override
    public void insertUser2Role(String userId, String[] oldroleIds,String[] newroleIds) throws SystemException, BusinessException {

        if( oldroleIds!=null){
            for(int i=0;i<oldroleIds.length;i++) {
                SysUser2RoleExample example = new SysUser2RoleExample();
                SysUser2RoleExample.Criteria cr = example.createCriteria();
                if (newroleIds[i] != "") {
                    cr.andRoleIdEqualTo(Integer.parseInt(oldroleIds[i]));
                    sysUser2RoleMapper.deleteByExample(example);
                }
            }
        }

        for (int i=0;i<newroleIds.length;i++){
            SysUser2Role record=new SysUser2Role();
            record.setUserId(Integer.parseInt(userId));
            if(newroleIds[i]!=""){
                record.setRoleId(Integer.parseInt(newroleIds[i]));
                sysUser2RoleMapper.insertSelective(record);
            }
        }
    }

    @Override
    public List<SysUser> getUserByName(String userName) throws SystemException, BusinessException {
       SysUserExample example=new  SysUserExample();
       SysUserExample.Criteria cr=example.createCriteria();
        cr.andUserNameEqualTo(userName);
        return sysUserMapper.selectByExample(example);
    }



    public static void main(String[] args) {
        System.out.println(Encrypt.encode("123456", Encrypt.MD5, false));
    }
}
