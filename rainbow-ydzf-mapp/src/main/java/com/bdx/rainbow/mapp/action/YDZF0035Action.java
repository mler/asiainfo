package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseEvidenceSample;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0035Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0035Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceSampleService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 *  样品新增
 * @author fox
 *
 */
@Service("ydzf0035")
@Action(bizcode="ydzf0035",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0035Action extends AbstractBaseActionHandler<YDZF0035Request,YDZF0035Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0035Action.class);
	@Autowired
	private IYDZFInspectCaseEvidenceSampleService ydzfInspectCaseEvidenceSampleService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
      	TYdzfInspectCaseEvidenceSample ydzfInspectCaseEvidenceSample= new TYdzfInspectCaseEvidenceSample();
      	BeanUtils.copyProperties(this.request.getYdzfInspectCaseEvidenceSampleMB(),ydzfInspectCaseEvidenceSample);
      	ydzfInspectCaseEvidenceSample.setCreateUserid(user.getUserId());
      	ydzfInspectCaseEvidenceSample.setCreateDate(DateUtil.getCurrent());
      	this.response.setInspectCaseEvidenceSampleId(ydzfInspectCaseEvidenceSampleService.addInspectCaseEvidenceSample(ydzfInspectCaseEvidenceSample));

    	
    }
   
}
