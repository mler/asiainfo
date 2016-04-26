package com.bdx.rainbow.service.ydzf.system;

import java.util.Map;

import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.exception.BusinessException;
import com.bdx.rainbow.urs.exception.SystemException;


public interface IYDZFLoginService {
	
    /**
     * 用户登陆
     * @param login_name
     * @param pwd
     * @param sysLogon
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
   public IUserInfo login(String login_name, String pwd,Map<String, Object> sysLogon) throws com.bdx.rainbow.urs.exception.BusinessException,com.bdx.rainbow.urs.exception.SystemException;
	
	
	
	

}
