package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0031Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0031Response;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;


/**
 * 查询附件
 * @author fox
 *
 */
@Service("ydzf0031")
@Action(bizcode="ydzf0031",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0031Action extends AbstractBaseActionHandler<YDZF0031Request,YDZF0031Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0031Action.class);
	@Autowired
	private IYDZFCommonService ydzfCommonService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
//      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
      	this.response.setHttpUrl(ydzfCommonService.getFileHttpUrlDubbo(this.request.getFileId()));
    	
    	
    }
   
}
