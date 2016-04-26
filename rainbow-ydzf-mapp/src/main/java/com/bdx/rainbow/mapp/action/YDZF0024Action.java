package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseFinish;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseFinishMB;
import com.bdx.rainbow.mapp.model.req.YDZF0024Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0024Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 结案新增
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0024")
@Action(bizcode="ydzf0024",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0024Action extends AbstractBaseActionHandler<YDZF0024Request,YDZF0024Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0024Action.class);
	@Autowired
	private IYDZFInspectCaseFinishService ydzfInspectCaseFinishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseFinish ydzfInspectCaseFinish= new TYdzfInspectCaseFinish();
    	TYdzfInspectCaseFinishMB ydzfInspectCaseFinishMB=this.request.getYdzfInspectCaseFinishMB();
    	BeanUtils.copyProperties(ydzfInspectCaseFinishMB, ydzfInspectCaseFinish);
    	ydzfInspectCaseFinish.setCreateDate(DateUtil.getCurrent());
    	ydzfInspectCaseFinish.setCreateUserid(user.getUserId());
    	this.response.setInspectCaseFinishId(ydzfInspectCaseFinishService.addInspectCaseFinish(ydzfInspectCaseFinish));
    	
    }
   
}
