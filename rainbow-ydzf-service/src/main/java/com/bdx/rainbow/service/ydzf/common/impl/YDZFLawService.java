package com.bdx.rainbow.service.ydzf.common.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.basic.dubbo.service.ILawDubboService;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.ExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;
import com.bdx.rainbow.service.ydzf.core.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("ydzfLawService")
public class YDZFLawService extends BaseService implements
		IYDZFLawService {
	private static final Logger log = LoggerFactory
			.getLogger(YDZFLawService.class);
	@Autowired
	protected ILawDubboService lawDubboService;


	@Override
	public Map<String, Object> getNodeListDubbo(DubboLaw condition,
			Timestamp startTime, Timestamp endTime, int start, int limit)
			throws BusinessException, SystemException {
//		if (condition == null ) {
//			throw new SystemException(new DefaultExceptionCode("condition参数为空"));
//		}
		try {
			return lawDubboService.getNodeList(condition, startTime, endTime, start, limit);
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}
	}

	@Override
	public DubboLaw getLawInfoDubbo(Integer lawId) throws BusinessException,
			SystemException {
		if (lawId == null ||lawId<0) {
			throw new SystemException(new DefaultExceptionCode("lawId参数为空"));
		}
		try {
			return lawDubboService.getLawInfo(lawId);
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}
	}

	@Override
	public String getTitlesByIdDubbo(Integer lawId) throws BusinessException,
			SystemException {
		if (lawId == null ||lawId<0) {
			throw new SystemException(new DefaultExceptionCode("lawId参数为空"));
		}
		try {
			return lawDubboService.getTitlesById(lawId);
		} catch (Exception e) {
			log.error("调用dubbo登录接口错误", e);
			throw new SystemException(ExceptionCode.EX_CORE_DUBBOERROR, e);
		}
	}

	@Override
	public String getLawJsonDubbo(String lawIds) throws BusinessException,
			SystemException {
		String  lawJsonInfo="";
		if(StringUtils.isBlank(lawIds))
		{
			return lawJsonInfo;
		}
		//{"law_info": [{"id": "1", "title": "标题1"},{"id": "2", "title": "标题2"},]}
		String[] lawIdArr=lawIds.split(",");
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(String lawId:lawIdArr)
		{
			String titles=this.getTitlesByIdDubbo(Integer.parseInt(lawId));
			Map<String,Object> lawMap= new HashMap<String,Object>();
			lawMap.put("id", lawId);
			lawMap.put("title", titles);
			list.add(lawMap);
		}
		resultMap.put("law_info", list);
		ObjectMapper mapper = new ObjectMapper();
		  try
	        {
			  lawJsonInfo=mapper.writeValueAsString(resultMap);
	        } catch (IOException e)
	        {
	        	log.error("组成lawJsonInfo异常",e);
	        }
		  return lawJsonInfo;
			
	}

}
