package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseWitnessesMB;
import com.bdx.rainbow.mapp.model.req.YDZF0050Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0050Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseWitnessesService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 听证更新
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0050")
@Action(bizcode="ydzf0050",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0050Action extends AbstractBaseActionHandler<YDZF0050Request,YDZF0050Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0050Action.class);
	@Autowired
	private IYDZFInspectCaseWitnessesService ydzfInspectCaseWitnessesService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses= new TYdzfInspectCaseWitnesses();
    	TYdzfInspectCaseWitnessesMB ydzfInspectCaseWitnessesMB=this.request.getYdzfInspectCaseWitnessesMB();
    	BeanUtils.copyProperties(ydzfInspectCaseWitnessesMB, ydzfInspectCaseWitnesses);
    	ydzfInspectCaseWitnesses.setUpdateDate(DateUtil.getCurrent());
    	ydzfInspectCaseWitnesses.setUpdateUserid(user.getUserId());
    	this.response.setInspectCaseWitnessesId(ydzfInspectCaseWitnessesService.updateInspectCaseWitnesses(ydzfInspectCaseWitnesses));
    	
    }
   
}
