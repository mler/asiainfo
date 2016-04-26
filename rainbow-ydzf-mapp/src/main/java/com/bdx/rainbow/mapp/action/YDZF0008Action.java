package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.form.YdzfMonitorPlanTaskInfoAddForm;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorTaskInfo;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorTaskInfoMB;
import com.bdx.rainbow.mapp.model.req.YDZF0008Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0008Response;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorTaskService;


/**
 * 新增或者更新任务详情（根据是否有id判断）
 * @author fox
 *
 */
@Service("ydzf0008")
@Action(bizcode="ydzf0008",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0008Action extends AbstractBaseActionHandler<YDZF0008Request,YDZF0008Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0008Action.class);
	@Autowired
	private IYDZFMonitorTaskService ydzfMonitorTaskService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	//IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
      	YdzfMonitorPlanTaskInfoAddForm taskInfoAddForm= new YdzfMonitorPlanTaskInfoAddForm();
    	TYdzfMonitorTaskInfoMB ydzfMonitorTaskInfoMB=this.request.getYdzfMonitorTaskInfoMB();
    	TYdzfMonitorTaskInfo   ydzfMonitorTaskInfo= new TYdzfMonitorTaskInfo();
    	BeanUtils.copyProperties(ydzfMonitorTaskInfoMB, ydzfMonitorTaskInfo);
    	taskInfoAddForm.setTaskInfo(ydzfMonitorTaskInfo);
    	this.response.setMonitorTaskId(ydzfMonitorTaskService.updateMonitorPlanTaskDetailInfo(taskInfoAddForm));
    }
   
}
