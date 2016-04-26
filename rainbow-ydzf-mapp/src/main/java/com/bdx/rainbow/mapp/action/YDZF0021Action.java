package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidence;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseEvidenceMB;
import com.bdx.rainbow.mapp.model.req.YDZF0021Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0021Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 调查取证新增
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0021")
@Action(bizcode="ydzf0021",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0021Action extends AbstractBaseActionHandler<YDZF0021Request,YDZF0021Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0021Action.class);
	@Autowired
	private IYDZFInspectCaseEvidenceService ydzfInspectCaseEvidenceService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseEvidence ydzfInspectCaseEvidence= new TYdzfInspectCaseEvidence();
    	TYdzfInspectCaseEvidenceMB ydzfInspectCaseEvidenceMB=this.request.getYdzfInspectCaseEvidenceMB();
    	ydzfInspectCaseEvidenceMB.setCreateDate(DateUtil.getCurrent());
    	ydzfInspectCaseEvidenceMB.setCreateUserid(user.getUserId());
    	BeanUtils.copyProperties(ydzfInspectCaseEvidenceMB, ydzfInspectCaseEvidence);
    	this.response.setInspectCaseEvidenceId(ydzfInspectCaseEvidenceService.addInspectCaseEvidence(ydzfInspectCaseEvidence));
    	
    }
   
}
