package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseListResultVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.YdzfInspectCaseListResultVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0020Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0020Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 查询稽查案件详情
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0020")
@Action(bizcode="ydzf0020",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0020Action extends AbstractBaseActionHandler<YDZF0020Request,YDZF0020Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0020Action.class);
	@Autowired
	private IYDZFInspectCaseService ydzfInspectCaseService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfInspectCaseListResultVO vo=ydzfInspectCaseService.queryInspectCaseInfoAndRel(this.request.getInspectCaseId());
    	if(vo!=null)
    	{
    		YdzfInspectCaseListResultVOMB ydzfInspectCaseListResultVOMB= new YdzfInspectCaseListResultVOMB();
    		TransformMapperBeanUtil.DTO2MB(vo, ydzfInspectCaseListResultVOMB);
    		this.response.setYdzfInspectCaseListResultVOMB(ydzfInspectCaseListResultVOMB);
    	}
    	
    	
    }
   
}
