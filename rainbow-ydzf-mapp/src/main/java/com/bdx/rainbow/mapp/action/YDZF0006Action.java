package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListInfoResultVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0006Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0006Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 * 日常任务列表详情接口
 * @author fox
 *
 */
@Service("ydzf0006")
@Action(bizcode="ydzf0006",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0006Action extends AbstractBaseActionHandler<YDZF0006Request,YDZF0006Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0006Action.class);
	@Autowired
	private IYDZFMonitorPlanService ydzfMonitorPlanService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	Integer monitorPlanId=this.request.getMonitorPlanId();
    	String checkPlanType= this.request.getCheckPlanType();
    	YdzfMonitorPlanListInfoResultVO vo=ydzfMonitorPlanService.queryMonitorPlanListInfo(monitorPlanId, checkPlanType);
    	TransformMapperBeanUtil.DTO2MB(vo, this.response);
    	
    }
   
}
