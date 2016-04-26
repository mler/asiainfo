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
import com.bdx.rainbow.mapp.model.req.YDZF0046Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0046Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseFinishService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 结案更新
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0046")
@Action(bizcode="ydzf0046",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0046Action extends AbstractBaseActionHandler<YDZF0046Request,YDZF0046Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0046Action.class);
	@Autowired
	private IYDZFInspectCaseFinishService ydzfInspectCaseFinishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseFinish ydzfInspectCaseFinish= new TYdzfInspectCaseFinish();
    	TYdzfInspectCaseFinishMB ydzfInspectCaseFinishMB=this.request.getYdzfInspectCaseFinishMB();
    	BeanUtils.copyProperties(ydzfInspectCaseFinishMB, ydzfInspectCaseFinish);
    	ydzfInspectCaseFinish.setUpdateDate(DateUtil.getCurrent());
    	ydzfInspectCaseFinish.setUpdateUserid(user.getUserId());
    	this.response.setInspectCaseFinishId(ydzfInspectCaseFinishService.updateInspectCaseFinish(ydzfInspectCaseFinish));
    	
    }
   
}
