package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceResultVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0040Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0040Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 调查取证查询
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0040")
@Action(bizcode="ydzf0040",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0040Action extends AbstractBaseActionHandler<YDZF0040Request,YDZF0040Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0040Action.class);
	@Autowired
	private IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfInspectCaseEvidenceResultVO vo=ydzfInspectCaseEvidenceService.queryInspectCaseEvidenceInfo(this.request.getInspectCaseEvidenceId());
    	TransformMapperBeanUtil.DTO2MB(vo, this.response);
    }
   
}
