package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseEvidenceSampleResultInfoVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0034Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0034Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseEvidenceSampleService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 *  样品查询
 * @author fox
 *
 */
@Service("ydzf0034")
@Action(bizcode="ydzf0034",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0034Action extends AbstractBaseActionHandler<YDZF0034Request,YDZF0034Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0034Action.class);
	@Autowired
	private IYDZFInspectCaseEvidenceSampleService ydzfInspectCaseEvidenceSampleService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
      	YdzfInspectCaseEvidenceSampleResultInfoVO vo=ydzfInspectCaseEvidenceSampleService.queryInspectCaseEvidenceSampleInfoById(this.request.getInspectCaseEvidenceSampleId());
    	TransformMapperBeanUtil.DTO2MB(vo, this.response);
    	
    	
    }
   
}
