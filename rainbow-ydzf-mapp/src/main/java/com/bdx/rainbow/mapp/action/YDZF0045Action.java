package com.bdx.rainbow.mapp.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseFinishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0045Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0045Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;

/**
 * 结案查询
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0045")
@Action(bizcode="ydzf0045",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0045Action extends AbstractBaseActionHandler<YDZF0045Request,YDZF0045Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0045Action.class);
	@Autowired
	private IYDZFInspectCaseFinishService ydzfInspectCaseFinishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
		// IUserInfo user = (IUserInfo)
		// MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfInspectCaseFinishResultVO vo= new YdzfInspectCaseFinishResultVO();
    	 Integer inspectCaseFinishId=this.request.getInspectCaseFinishId();
    	 Integer inspectCaseId=this.request.getInspectCaseId();
    	if(inspectCaseFinishId!=null&&inspectCaseFinishId>0)
    	{
    		 vo =ydzfInspectCaseFinishService.queryInspectCaseFinishInfo(inspectCaseFinishId);
    	}
    	else if(inspectCaseId!=null&&inspectCaseId>0)
    	{
    		TYdzfInspectCaseFinish ydzfInspectCaseFinish= new TYdzfInspectCaseFinish();
    		ydzfInspectCaseFinish.setInspectCaseId(inspectCaseId);
    		List<YdzfInspectCaseFinishResultVO> volist=ydzfInspectCaseFinishService.queryInspectCaseFinishInfoVOList(ydzfInspectCaseFinish);
    		if(CollectionUtils.isEmpty(volist))
    		{
    			log.error("查询纪录异常，未找到案件结案纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("未找到案件结案纪录"));
    		}
    		else if(volist.size()>1)
    		{
    			log.error("查询纪录异常，有多条案件结案纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("有多条案件结案纪录"));
    		}
    		vo=volist.get(0);
    	}
    	else
    	{
    		log.error("查询参数为空");
			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("查询参数为空"));
    	}
		TransformMapperBeanUtil.DTO2MB(vo, this.response);	
    }
   
}
