package com.bdx.rainbow.service;

import java.util.Map;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.basic.mysql.TMappUser;
import com.bdx.rainbow.entity.sys.IUserInfo;

public interface IPhoneUserService {
	/**
	 * mapp登陆 原系统oracle
	 * @param login_name
	 * @param pwd
	 * @param sysLogon
	 * @return
	 * @throws SystemException
	 * @throws BusinessException
	 */
	public IUserInfo mappLogin(String login_name, String pwd,Map<String, Object> sysLogon) throws SystemException,BusinessException;
	
	public TMappUser mappLogin1(String login_name, String pwd,Map<String, Object> sysLogon) throws SystemException,BusinessException;
	
	public TMappUser getMappUserByLoginName(String loginName)throws SystemException,BusinessException;
	
	public void insertMappLogin(TMappUser user,Map<String, Object> sysLogon)throws SystemException,BusinessException;
	
	public boolean ifloginNameExists(String loginName)throws SystemException,BusinessException;
	
	public void createMappUser(TMappUser user)throws SystemException,BusinessException;
	
}
