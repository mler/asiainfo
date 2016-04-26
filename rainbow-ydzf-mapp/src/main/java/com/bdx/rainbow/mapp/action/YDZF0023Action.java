package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseExecutePunish;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseExecutePunishMB;
import com.bdx.rainbow.mapp.model.req.YDZF0023Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0023Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseExecutePunishService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 执行处罚新增修改
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0023")
@Action(bizcode="ydzf0023",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0023Action extends AbstractBaseActionHandler<YDZF0023Request,YDZF0023Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0023Action.class);
	@Autowired
	private IYDZFInspectCaseExecutePunishService ydzfInspectCaseExecutePunishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseExecutePunish ydzfInspectCaseExecutePunish= new TYdzfInspectCaseExecutePunish();
    	TYdzfInspectCaseExecutePunishMB ydzfInspectCaseExecutePunishMB=this.request.getYdzfInspectCaseExecutePunishMB();
    	BeanUtils.copyProperties(ydzfInspectCaseExecutePunishMB, ydzfInspectCaseExecutePunish);
    	this.response.setInspectCaseExecutePunishId(ydzfInspectCaseExecutePunishService.addOrUpdateExecutePunishInfo(ydzfInspectCaseExecutePunish, user));
    	
    }
   
}
