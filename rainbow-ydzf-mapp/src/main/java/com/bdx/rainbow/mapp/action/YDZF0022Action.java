package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseReadyPunish;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseReadyPunishMB;
import com.bdx.rainbow.mapp.model.req.YDZF0022Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0022Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
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
@Service("ydzf0022")
@Action(bizcode="ydzf0022",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0022Action extends AbstractBaseActionHandler<YDZF0022Request,YDZF0022Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0022Action.class);
	@Autowired
	private IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish= new TYdzfInspectCaseReadyPunish();
    	TYdzfInspectCaseReadyPunishMB ydzfInspectCaseReadyPunishMB=this.request.getYdzfInspectCaseReadyPunishMB();
    	ydzfInspectCaseReadyPunishMB.setCreateDate(DateUtil.getCurrent());
    	ydzfInspectCaseReadyPunishMB.setCreateUserid(user.getUserId());
    	BeanUtils.copyProperties(ydzfInspectCaseReadyPunishMB, ydzfInspectCaseReadyPunish);
    	this.response.setInspectCaseReadyPunishId(ydzfInspectCaseReadyPunishService.addInspectCaseReadyPunish(ydzfInspectCaseReadyPunish));
    	
    }
   
}
