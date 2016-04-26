package com.bdx.rainbow.mapp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0015Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0015Response;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 法律条文标题组合查询接口(根据id 返回所有titles)
 * @author fox
 *
 */
@Service("ydzf0015")
@Action(bizcode="ydzf0015",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0015Action extends AbstractBaseActionHandler<YDZF0015Request,YDZF0015Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0015Action.class);
	@Autowired
	private IYDZFLawService ydzfLawService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	List<Integer> lawIdList=this.request.getLawIdList();
		List<Map<String,Object>> lawInfoList= new ArrayList<Map<String,Object>>();
    	for(Integer lawId:lawIdList)
    	{
    		String titles=ydzfLawService.getTitlesByIdDubbo(lawId);
    		Map<String,Object> lawInfoMap= new HashMap<String,Object>();
    		lawInfoMap.put("lawId", lawId);
    		lawInfoMap.put("titles", titles);
    		lawInfoList.add(lawInfoMap);
    	}
    	ObjectMapper mapper = new ObjectMapper();
		  try
	        {
			  this.response.setLawTitlesJson(mapper.writeValueAsString(lawInfoList));
	        } catch (IOException e)
	        {
	        	log.error("组成checkJsonInfo异常",e);
	        }
    	
    	
    }
   
}
