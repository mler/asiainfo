package com.bdx.rainbow.mapp.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseExecutePunishResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0044Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0044Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseExecutePunishService;

/**
 * 执行处罚查询
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0044")
@Action(bizcode="ydzf0044",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0044Action extends AbstractBaseActionHandler<YDZF0044Request,YDZF0044Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0044Action.class);
	@Autowired
	private IYDZFInspectCaseExecutePunishService ydzfInspectCaseExecutePunishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
		// IUserInfo user = (IUserInfo)
		// MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfInspectCaseExecutePunishResultVO vo= new YdzfInspectCaseExecutePunishResultVO();
    	 Integer inspectCaseExecutePunishId=this.request.getInspectCaseExecutePunishId();
    	 Integer inspectCaseId=this.request.getInspectCaseId();
    	if(inspectCaseExecutePunishId!=null&&inspectCaseExecutePunishId>0)
    	{
    		 vo =ydzfInspectCaseExecutePunishService.queryExecutePunishInfo(inspectCaseExecutePunishId);
    	}
    	else if(inspectCaseId!=null&&inspectCaseId>0)
    	{
    		TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish= new TYdzfInspectCaseExecutePunish();
    		ydzfInspectCaseExecutePunish.setInspectCaseId(inspectCaseId);
    		List<YdzfInspectCaseExecutePunishResultVO> volist=ydzfInspectCaseExecutePunishService.queryExecutePunishInfoVOList(ydzfInspectCaseExecutePunish);
    		if(CollectionUtils.isEmpty(volist))
    		{
    			log.error("查询纪录异常，未找到执行处罚纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("未找到执行处罚纪录"));
    		}
    		else if(volist.size()>1)
    		{
    			log.error("查询纪录异常，有多条执行处罚纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("有多条执行处罚纪录"));
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
