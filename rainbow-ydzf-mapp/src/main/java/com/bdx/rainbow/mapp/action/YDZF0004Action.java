package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListNumResultVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0004Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0004Response;
import com.bdx.rainbow.mapp.model.rsp.YDZF0004Response.ExecuteStatus;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 首页列表查询
 * @author fox
 *
 */
@Service("ydzf0004")
@Action(bizcode="ydzf0004",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0004Action extends AbstractBaseActionHandler<YDZF0004Request,YDZF0004Response> {
	@Autowired
	private IYDZFMonitorPlanService ydzfMonitorPlanService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	int deptId=this.request.getDeptId()>0?this.request.getDeptId():user.getUser().getDeptId();
    	List<YdzfMonitorPlanListNumResultVO> numList=ydzfMonitorPlanService.queryPlanListNum(deptId, this.request.getCheckPlanType(), this.request.getCheckPlanStatus());
    	List<ExecuteStatus> executeStatusList= new ArrayList<ExecuteStatus>();
    	for(YdzfMonitorPlanListNumResultVO ydzfPlanListNumResultDTO:numList)
    	{
    		ExecuteStatus executeStatus= new ExecuteStatus();
    		for(ExecuteStatus obj:executeStatusList)
    		{
    			if(obj.getCheckPlanType().equals(ydzfPlanListNumResultDTO.getCheckPlanType()))
    			{
    				executeStatus=obj;
    				break;
    			}
    		}
			if(executeStatus.getCheckPlanType()==null)
    		{
				executeStatus.setCheckPlanType(ydzfPlanListNumResultDTO.getCheckPlanType());
				executeStatusList.add(executeStatus);
    		}
    		if(ydzfPlanListNumResultDTO.getCheckPlanStatus().equals(YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_ING))
    		{
    			executeStatus.setUnCompleteCount(ydzfPlanListNumResultDTO.getNum());
    		}
    		else if(ydzfPlanListNumResultDTO.getCheckPlanStatus().equals(YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_ING))
    		{
    			executeStatus.setIsCompletingCount(ydzfPlanListNumResultDTO.getNum());
    		}
    		else if(ydzfPlanListNumResultDTO.getCheckPlanStatus().equals(YDZFConstants.MONITOR.PLAN_STATUS_EXECUTE_FINISH))
    		{
    			executeStatus.setCompleteCount(ydzfPlanListNumResultDTO.getNum());
    		}
    	}
    	  this.response.setExecuteStatusList(executeStatusList);
    }
   
}
