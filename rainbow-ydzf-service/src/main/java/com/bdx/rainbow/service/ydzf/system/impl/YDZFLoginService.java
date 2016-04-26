package com.bdx.rainbow.service.ydzf.system.impl;

import java.util.Map;

import com.bdx.rainbow.urs.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.bdx.rainbow.service.ydzf.system.IYDZFLoginService;
import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.IUserInfo;

@Service("ydzfSystemService")
public class YDZFLoginService extends BaseService  implements IYDZFLoginService {

	private static final Logger log = LoggerFactory
			.getLogger(YDZFLoginService.class);
	@Autowired
	private IDubUserService dubUserService;

	@Override
	public IUserInfo login(String loginName, String pwd,
			Map<String, Object> sysLogon) throws BusinessException,
			com.bdx.rainbow.urs.exception.SystemException {
		IUserInfo userInfo;
		try {
			userInfo = dubUserService.login(loginName, pwd,
					YDZFConstants.PLATFORM.YDZF_PLAT_ID, null);
		}
		catch (com.bdx.rainbow.urs.exception.BusinessException e)
		{
			throw e;
		}
		catch (Exception e) {
		log.error("调用dubbo登录接口错误",e);
		throw new com.bdx.rainbow.urs.exception.SystemException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("调用dubbo接口错误"),e);
		}
		return userInfo;
	}

}
