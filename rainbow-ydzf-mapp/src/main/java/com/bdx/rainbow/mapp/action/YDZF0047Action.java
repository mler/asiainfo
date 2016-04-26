package com.bdx.rainbow.mapp.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseAuditResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0047Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0047Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseAuditService;

/**
 * 案件审核查询
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0047")
@Action(bizcode="ydzf0047",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0047Action extends AbstractBaseActionHandler<YDZF0047Request,YDZF0047Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0047Action.class);
	@Autowired
	private IYDZFInspectCaseAuditService ydzfInspectCaseAuditService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
		// IUserInfo user = (IUserInfo)
		// MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfInspectCaseAuditResultVO vo= new YdzfInspectCaseAuditResultVO();
    	 Integer inspectCaseAuditId=this.request.getInspectCaseAuditId();
    	 Integer inspectCaseId=this.request.getInspectCaseId();
    	if(inspectCaseAuditId!=null&&inspectCaseAuditId>0)
    	{
    		 vo =ydzfInspectCaseAuditService.queryInspectCaseAuditInfo(inspectCaseAuditId);
    	}
    	else if(inspectCaseId!=null&&inspectCaseId>0)
    	{
    		TYdzfInspectCaseAudit ydzfInspectCaseAudit= new TYdzfInspectCaseAudit();
    		ydzfInspectCaseAudit.setInspectCaseId(inspectCaseId);
    		List<YdzfInspectCaseAuditResultVO> volist=ydzfInspectCaseAuditService.queryInspectCaseAuditInfoVOList(ydzfInspectCaseAudit);
    		if(CollectionUtils.isEmpty(volist))
    		{
    			log.error("查询纪录异常，未找到案件审核纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("未找到案件审核纪录"));
    		}
    		else if(volist.size()>1)
    		{
    			log.error("查询纪录异常，有多条案件审核纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("有多条案件审核纪录"));
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
