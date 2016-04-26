package com.bdx.rainbow.urs.entity;


import java.sql.Timestamp;
import java.util.List;

public interface IUserInfo extends IUser {
	/**
	 * 用户的扩展信息
	 * @return
	 */
	public List<SysUserExt> getUserExts();
	public void setUserExts(List<SysUserExt> userExts) ;
	
	/**
	 * 用户拥有的菜单
	 * @return
	 */
	public List<SysMenuTree> getuMenus();
	public void setuMenus(List<SysMenuTree> uMenus);
	
	/**
	 * 用户基础信息
	 * @return
	 */
	public SysUser getUser() ;
	public void setUser(SysUser user);
	
	/**
	 * 用户拥有的角色
	 * @return
	 */
	public List<SysRole> getuRoles() ;
	public void setuRoles(List<SysRole> uRoles);
	/**
	 * 是否是代理商（用户类型 1 加盟渠道管理员 2 加盟渠道员工 ）
	 * @return
	 */
	public boolean isAgent();
	
	/**
	 * 是否是代理商（渠道）的管理员（用户类型  1 加盟渠道管理员）
	 * @return
	 */
	public boolean isAgentAdmin();
	
	/**
	 * 用户归属省份
	 * @return
	 */
	public String getProvince();
	
	public void setProvince(String province);
	
	/**
	 * 用户归属地市
	 * @return
	 */
	public String getAreaid();
	
	public void setAreaid(String areaid);
	
	/**
	 * 用户归属区县
	 * @return
	 */
	public String getCountyid();
	
	public void setCountyid(String countyid);
    public String getDeptName();


    public void setDeptName(String deptName);
	
	/**
	 * 用户登录时间
	 */
	public Timestamp getLastLogonTime();
	
	public void setLastLogonTime(Timestamp lastLogonTime);

    /**
     * 用户密码
     */
    public void setLoginPwd(String loginPwd);
}
