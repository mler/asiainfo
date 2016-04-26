package com.bdx.rainbow.urs.service;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.*;
import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.List;
import java.util.Map;

public interface IUserService {
    /**
     * 1 有效 2 禁用 3 失效 4待确认
     * 用户类型：('0','平台管理员'),('1','加盟渠道老板/电商'),('2','平台普通员工'),('3','加盟渠道普通员工'),('4','自有渠道员工');
     * @param record
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    Integer insertUser(SysUser record) throws SystemException, BusinessException;
    /**
     * 1 有效 2 禁用 3 失效 4待确认
     * 用户类型：('0','平台管理员'),('1','加盟渠道老板/电商'),('2','平台普通员工'),('3','加盟渠道普通员工'),('4','自有渠道员工');
     * @param record
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    int updateUser(SysUser record) throws SystemException, BusinessException;

    /**
     * obj为null时，则条件为空
     * condition dept_id, user_name(like), user_login_name(like), email, user_type, user_status
     * @param obj
     * @param start
     * @param count
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    List<SysUser> getUsers(SysUser obj, Integer start, Integer count,SysUser userlogin) throws SystemException, BusinessException;

    /**
     * user primaryKey user_id ,return SYS_USER & List<Sys_user_ext> obj
     * @param userId
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    SysUserModel getUserAndExtById(Integer userId) throws SystemException, BusinessException;

    /**
     * user primaryKey user_id ,return SYS_USER
     * @param userId
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    SysUser getUserById(Integer userId) throws SystemException, BusinessException;

    /**
     * user primaryKey user_id ,return SYS_USER_EXT objs
     * @param userId
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    List<SysUserExt> getUserExtById(Integer userId) throws SystemException, BusinessException;

    /**
     *  use ID and key find sys_user_ext obj
     * @param userId
     * @param key
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    SysUserExt getUserExtByIdAndKey(Integer userId, String key) throws SystemException, BusinessException;

    /**
     * update sys_user's user_status
     * @param userId
     * @param userStatus  用户状态 1 有效 2 禁用 3 失效4待确认(对于邮箱注册用户的确认操作前的状态)
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    int updateUserStatusById(Integer userId, String userStatus, Integer updator) throws SystemException, BusinessException;

    /**
     * when modify pwd, 判断 the oldPWD ， userId's dbPWD 是否一致 : true 一致 false 不一致
     * @param userId
     * @param oldPWD
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    boolean checkUserPWDById(Integer userId, String oldPWD) throws SystemException, BusinessException;

    int countUser(SysUser record,SysUser userlogin) throws SystemException, BusinessException;

    int countUserExtByIdAndKey(Integer userId, String extKey) throws SystemException, BusinessException;

    /**
     * 验证登录名是否已经存在，true 存在， false 不存在
     * @param userLoginName
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    boolean isThisLoginNameExsit(String userLoginName) throws SystemException, BusinessException;

    /**
     * 验证移动号码是否已经存在，true 存在， false 不存在
     * @param mobilePhone
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    boolean checkUserByMobilePhone(String mobilePhone) throws SystemException, BusinessException;

    /**
     * 验证E-MAIL是否已经存在，true 存在， false 不存在
     * @param email
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    boolean checkUserByEmail(String email) throws SystemException, BusinessException;

    /**
     * create sys_user obj and more sys_user_ext objs
     * @param obj
     * @param objExts
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract Integer createUser(SysUser obj, List<SysUserExt> objExts) throws SystemException, BusinessException;
    /**
     *  * update sys_user、sys_user_ext message,
     * if sys_user_ext's key was existed ,then to update sys_user_ext obj.otherwise to insert sys_user_ext
     * @param obj
     * @param objExts
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract int updateUser(SysUser obj, List<SysUserExt> objExts) throws SystemException, BusinessException;

    /**
     * confirm user whether he would has the role
     * @param sysUserInfo
     * @param roleId
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract boolean checkRole(IUserInfo sysUserInfo, Integer roleId) throws SystemException, BusinessException;

    /**
     * confirm user whether he woudld has the menu
     * @param sysUserInfo
     * @param menuId
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract boolean checkMenu(IUserInfo sysUserInfo, Integer menuId) throws SystemException, BusinessException;

    /**
     * change user pwd, according to the item to select the way of circular.
     * for example， select email。
     * @param userId
     * @param newPwd
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract int changePWD(Integer userId, String newPwd) throws SystemException, BusinessException;

    /**
     * user login method 后台管理登录方法：只有平台管理员0， 加盟渠道老板 1才能登录
     * @param login_name
     * @param pwd
     * @param sysLogon 登录的信息：如IP，MAC 等!  包含key: serverIp  主机服务器地址，   logonIp 用户登录地址 ， logonMac 客户端Mac
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract IUserInfo login(String login_name, String pwd,Integer platId, Map<String, Object> sysLogon) throws SystemException, BusinessException;

    /**
     * 忘记密码时，通过邮箱找回随机密码
     * @param email
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract String forgotPWD(String email) throws SystemException, BusinessException;
    /**
     * 批量变更用户状态
     * @param userIds
     * @param userStatus
     * @throws SystemException
     * @throws BusinessException
     */
    void updateUserStatusByUserIds(List<Integer> userIds, String userStatus, Integer updator)
            throws SystemException, BusinessException;

    /**
     * 新增sys_user_ext
     * @param userExt
     * @throws SystemException
     * @throws BusinessException
     */
    void insertUserExt(SysUserExt userExt) throws SystemException, BusinessException;
    /**
     * 更新sys_user_ext
     * @param userExt
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    int updateUserExt(SysUserExt userExt) throws SystemException, BusinessException;
    /**
     * 更新组织机构扩展信息，如果objExts为null，则删除sys_user_ext中对应的userId的记录。否则规则如下：判断是否已经存在对应KEY的数据，如无，则新增，反之更新；
     * @param userId
     * @param objExts
     * @throws SystemException
     * @throws BusinessException
     */
    void updateUserExts(Integer userId, List<SysUserExt> objExts)
            throws SystemException, BusinessException;

    /**
     * 用户更新，不包含扩展信息
     * @param obj
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    int updateUserByO(SysUser obj) throws SystemException, BusinessException;

    /**
     * 平台商用户登录  ，唯有电商用户 1， 个人用户5 才能登录
     * @param login_name
     * @param pwd
     * @param sysLogon
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract IUserInfo businessLogin(String login_name, String pwd, Map<String, Object> sysLogon) throws SystemException, BusinessException;

    /**
     *
     * 手机终端用户登录  ，唯有加盟渠道管理员1、加盟渠道普通用户3
     * @param login_name
     * @param pwd
     * @param sysLogon
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    public abstract IUserInfo phoneLogin(String login_name, String pwd, Map<String, Object> sysLogon) throws SystemException, BusinessException;
    List<SysUser> getUsers(SysUser obj, Integer start, Integer count)

            throws SystemException, BusinessException;
    int countUser(SysUser obj) throws SystemException, BusinessException;
    IUserInfo login(String login_name, Map<String, Object> sysLogon)
            throws SystemException, BusinessException;

    List<SysUser2Plat> getUser2Plats(String userId) throws SystemException, BusinessException;
    void insertUser2Plat(String userId,String[] toBedeletedIds ,String[] toinsertedIds) throws SystemException, BusinessException;
    List<SysUser2Role> getUser2Roles(String userId)throws SystemException, BusinessException;
    void insertUser2Role(String userId,String[] oldroleIds,String[] newroleIds)throws SystemException, BusinessException;
//    int insertUser2Role(String userId,Str)
    List<SysUser >getUserByName(String userName)throws SystemException, BusinessException;
    IUserInfo getUserInfoById(Integer userId, Integer platId)throws SystemException, BusinessException;

}
