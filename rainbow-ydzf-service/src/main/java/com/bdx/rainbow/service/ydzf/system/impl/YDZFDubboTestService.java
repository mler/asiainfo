package com.bdx.rainbow.service.ydzf.system.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.service.ydzf.core.BaseDubboService;
import com.bdx.rainbow.ydzf.dubbo.IDubboService;
public class YDZFDubboTestService extends BaseDubboService implements IDubboService {

	
	
	@Override
	public String dubboTest(String content)  throws BusinessException,
	SystemException{
		throw new BusinessException(com.bdx.rainbow.common.exception.ExceptionCode.EX_CORE_UNKNOW);
	}

	@Override
	public String dubboTest2(String content) throws Exception {
		throw new NullPointerException("11111");
	}

	@Override
	public Object $invoke(String method, String[] parameterTypes, Object[] args)
			throws GenericException {
		// TODO Auto-generated method stub
		return null;
	}



}
