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
import com.bdx.rainbow.mapp.model.req.YDZF0026Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0026Response;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseWitnessesService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 听证新增
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0026")
@Action(bizcode="ydzf0026",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0026Action extends AbstractBaseActionHandler<YDZF0026Request,YDZF0026Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0026Action.class);
	@Autowired
	private IYDZFInspectCaseWitnessesService ydzfInspectCaseWitnessesService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses= new TYdzfInspectCaseWitnesses();
    	TYdzfInspectCaseWitnessesMB ydzfInspectCaseWitnessesMB=this.request.getYdzfInspectCaseWitnessesMB();
    	BeanUtils.copyProperties(ydzfInspectCaseWitnessesMB, ydzfInspectCaseWitnesses);
    	ydzfInspectCaseWitnesses.setCreateDate(DateUtil.getCurrent());
    	ydzfInspectCaseWitnesses.setCreateUserid(user.getUserId());
    	this.response.setInspectCaseWitnessesId(ydzfInspectCaseWitnessesService.addInspectCaseWitnesses(ydzfInspectCaseWitnesses));
    	
    }
   
}
