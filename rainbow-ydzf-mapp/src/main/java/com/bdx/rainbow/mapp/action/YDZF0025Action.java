package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseAudit;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseAuditMB;
import com.bdx.rainbow.mapp.model.req.YDZF0025Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0025Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseAuditService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 案件审核新增
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0025")
@Action(bizcode="ydzf0025",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0025Action extends AbstractBaseActionHandler<YDZF0025Request,YDZF0025Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0025Action.class);
	@Autowired
	private IYDZFInspectCaseAuditService ydzfInspectCaseAuditService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseAudit ydzfInspectCaseAudit= new TYdzfInspectCaseAudit();
    	TYdzfInspectCaseAuditMB ydzfInspectCaseAuditMB=this.request.getYdzfInspectCaseAuditMB();
    	BeanUtils.copyProperties(ydzfInspectCaseAuditMB, ydzfInspectCaseAudit);
    	ydzfInspectCaseAudit.setCreateDate(DateUtil.getCurrent());
    	ydzfInspectCaseAudit.setCreateUserid(user.getUserId());
    	this.response.setInspectCaseAuditId(ydzfInspectCaseAuditService.addInspectCaseAudit(ydzfInspectCaseAudit));
    	
    }
   
}
