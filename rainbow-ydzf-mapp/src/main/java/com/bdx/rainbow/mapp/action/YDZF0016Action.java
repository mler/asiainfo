package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.DubboLawMB;
import com.bdx.rainbow.mapp.model.req.YDZF0016Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0016Response;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 * 法律法规详情查询
 * @author fox
 *
 */
@Service("ydzf0016")
@Action(bizcode="ydzf0016",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0016Action extends AbstractBaseActionHandler<YDZF0016Request,YDZF0016Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0016Action.class);
	@Autowired
	private IYDZFLawService ydzfLawService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	DubboLaw dubboLaw=ydzfLawService.getLawInfoDubbo(this.request.getLawId());
    	if(dubboLaw!=null)
    	{
    		DubboLawMB dubboLawMB= new DubboLawMB();
    		BeanUtils.copyProperties(dubboLaw, dubboLawMB);
    		this.response.setDubboLawMB(dubboLawMB);
    	}
    	
    }
   
}
