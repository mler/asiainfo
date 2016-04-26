package com.bdx.rainbow.entity.sys;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class SysUserInfo implements IUserInfo, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4972734765911602897L;
	
	private SysUser user;
	
	private List<SysUserExt> userExts;

	private List<SysRole> uRoles;
	
	private List<SysMenuTree> uMenus;
	
    private String province;
    
    private String areaid;
    
    private String countyid;
    
    private Timestamp lastLogonTime;
    
	
//	private boolean pwdisright;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getCountyid() {
		return countyid;
	}

	public void setCountyid(String countyid) {
		this.countyid = countyid;
	}

	public List<SysUserExt> getUserExts() {
		return userExts;
	}

	public void setUserExts(List<SysUserExt> userExts) {
		this.userExts = userExts;
	}

//	public boolean isPwdisright() {
//		return pwdisright;
//	}
//
//	public void setPwdisright(boolean pwdisright) {
//		this.pwdisright = pwdisright;
//	}

	public List<SysMenuTree> getuMenus() {
		return uMenus;
	}

	public void setuMenus(List<SysMenuTree> uMenus) {
		this.uMenus = uMenus;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public List<SysRole> getuRoles() {
		return uRoles;
	}

	public void setuRoles(List<SysRole> uRoles) {
		this.uRoles = uRoles;
	}

	@Override
	public void setUserId(Integer userId) {
		
	}

	@Override
	public Integer getUserId() {
		return this.getUser().getUserId();
	}

	@Override
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserName() {
		return this.getUser().getUserLoginName();
	}

	@Override
	public String getNickName() {
		return this.getUser().getUserName();
	}

	@Override
	public String getMailBox() {
		return this.getUser().getEmail();
	}

	@Override
	public Integer getLogoId() {
		return null;
	}

	@Override
	public String getUserSetting(String groupName, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserSetting(String groupname, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAgent() {
		return ("1".equals(this.getUser().getUserType()) || "2".equals(this.getUser().getUserType())) ? true :false;
	}

	@Override
	public boolean isAgentAdmin() {
		return ("1".equals(this.getUser().getUserType())) ? true :false;
	}
	@Override
	public Timestamp getLastLogonTime() {
		return this.lastLogonTime;
	}

	@Override
	public void setLastLogonTime(Timestamp lastLogonTime) {
		this.lastLogonTime = lastLogonTime;
	}


}