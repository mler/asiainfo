package com.bdx.rainbow.mapp.model.rsp;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 用户登陆
 *
 * @author luli
 */
public class YDZF0001Response extends BDXBody {
    /**
     * sessionId
     */
    private String sessionId;
    /**
     * 用户类型
     */
    private String userType;

    /**
     * 头像
     */
    private String headImageUrl;

    /**
     * 登陆用户名
     */
    private String loginName;

    /**
     *  用户名
     */

    private String userName;

    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 职位
     */

    private String position;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门ID
     */
    private Integer deptId;
 
	public String getSessionId() {
		return sessionId;
	}
	public String getUserType() {
		return userType;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public String getLoginName() {
		return loginName;
	}
	public String getUserName() {
		return userName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public String getPosition() {
		return position;
	}
	public Integer getUserId() {
		return userId;
	}
	public String getDeptName() {
		return deptName;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
