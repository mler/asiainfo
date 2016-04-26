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
import com.bdx.rainbow.mapp.model.req.YDZF0032Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0032Response;
import com.bdx.rainbow.service.ydzf.common.IYDZFCommonService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 * 上传附件
 * @author fox
 *
 */
@Service("ydzf0032")
@Action(bizcode="ydzf0032",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0032Action extends AbstractBaseActionHandler<YDZF0032Request,YDZF0032Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0032Action.class);
	@Autowired
	private IYDZFCommonService ydzfCommonService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
      	String fileId=ydzfCommonService.uploadFileDubbo(this.request.getFileBytes(), this.request.getExtName(), this.request.getFileName(), user.getUserId(), this.request.isTemp(), this.request.isNeedValidate());
      this.response.setFileId(fileId);
    	
    	
    }
   
}
