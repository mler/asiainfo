package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanTaskInfoResultVO;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0012Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0012Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorTaskService;


/**
 *  查询日常监督任务，任务详情
 * @author fox
 *
 */
@Service("ydzf0012")
@Action(bizcode="ydzf0012",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0012Action extends AbstractBaseActionHandler<YDZF0012Request,YDZF0012Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0012Action.class);
	@Autowired
	private IYDZFMonitorTaskService ydzfMonitorTaskService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	//IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfMonitorPlanTaskInfoResultVO vo=ydzfMonitorTaskService.queryMonitorPlanTaskInfo(this.request.getMonitorTaskId());
    	TransformMapperBeanUtil.DTO2MB(vo, this.response);
    	

    	
    	
    }
   
}
