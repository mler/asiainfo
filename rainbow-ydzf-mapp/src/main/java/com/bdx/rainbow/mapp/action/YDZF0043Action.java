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
import com.bdx.rainbow.mapp.model.req.YDZF0043Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0043Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseReadyPunishService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 拟处罚更新
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0043")
@Action(bizcode="ydzf0043",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0043Action extends AbstractBaseActionHandler<YDZF0043Request,YDZF0043Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0043Action.class);
	@Autowired
	private IYDZFInspectCaseReadyPunishService ydzfInspectCaseReadyPunishService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseReadyPunish ydzfInspectCaseReadyPunish= new TYdzfInspectCaseReadyPunish();
    	TYdzfInspectCaseReadyPunishMB ydzfInspectCaseReadyPunishMB=this.request.getYdzfInspectCaseReadyPunishMB();
      	BeanUtils.copyProperties(ydzfInspectCaseReadyPunishMB,ydzfInspectCaseReadyPunish);
      	ydzfInspectCaseReadyPunish.setUpdateUserid(user.getUserId());
      	ydzfInspectCaseReadyPunish.setUpdateDate(DateUtil.getCurrent());
      	this.response.setInspectCaseReadyPunishId(ydzfInspectCaseReadyPunishService.updateInspectCaseReadyPunish(ydzfInspectCaseReadyPunish));
    }
   
}
