package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0036Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0036Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceSampleService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 *  样品删除
 * @author fox
 *
 */
@Service("ydzf0036")
@Action(bizcode="ydzf0036",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0036Action extends AbstractBaseActionHandler<YDZF0036Request,YDZF0036Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0036Action.class);
	@Autowired
	private IYDZFInspectCaseEvidenceSampleService ydzfInspectCaseEvidenceSampleService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
      	this.response.setInspectCaseEvidenceSampleId(	ydzfInspectCaseEvidenceSampleService.delInspectCaseEvidenceSample(this.request.getInspectCaseEvidenceSampleId(), user));
     
    }
   
}
