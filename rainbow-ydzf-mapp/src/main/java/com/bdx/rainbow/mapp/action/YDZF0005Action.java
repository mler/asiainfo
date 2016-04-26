package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorPlanListResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfMonitorPlan;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.TYdzfMonitorPlanMB;
import com.bdx.rainbow.mapp.model.bean.YdzfMonitorPlanListResultVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0005Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0005Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.monitor.IYDZFMonitorPlanService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 查询日常监督任务列表
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0005")
@Action(bizcode="ydzf0005",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0005Action extends AbstractBaseActionHandler<YDZF0005Request,YDZF0005Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0005Action.class);
	@Autowired
	private IYDZFMonitorPlanService ydzfMonitorPlanService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfMonitorPlanMB ydzfMonitorPlanMB=this.request.getYdzfMonitorPlanMB();
    	PageInfo pageInfo=this.request.getPageInfo();
    	//组织id 要自己设置吗？
    	if(ydzfMonitorPlanMB.getCheckPlanCreatDepId()==null||ydzfMonitorPlanMB.getCheckPlanCreatDepId()==0)
    	{
    		ydzfMonitorPlanMB.setCheckExecuteDepId(user.getUser().getDeptId());
    	//	ydzfMonitorPlanMB.setCheckPlanCreatDepId(user.getUser().getDeptId());
    	}
    	TYdzfMonitorPlan ydzfMonitorPlan= new TYdzfMonitorPlan();
    	BeanUtils.copyProperties(ydzfMonitorPlanMB,ydzfMonitorPlan);
    	List<YdzfMonitorPlanListResultVO> ydzfMonitorPlanListResultVOList=ydzfMonitorPlanService.queryMonitorPlanList(ydzfMonitorPlan, pageInfo.getPageStart(), pageInfo.getPageCount());
    	int total=ydzfMonitorPlanService.countMonitorPlanList(ydzfMonitorPlan);
    	pageInfo.setTotalCount(total);
    	this.response.setPageInfo(pageInfo);
    	if(!CollectionUtils.isEmpty(ydzfMonitorPlanListResultVOList))
    	{
        	List<YdzfMonitorPlanListResultVOMB> ydzfMonitorPlanListResultVOMBList = new ArrayList<YdzfMonitorPlanListResultVOMB>(ydzfMonitorPlanListResultVOList.size());
        	TransformMapperBeanUtil.DTOList2MBList(ydzfMonitorPlanListResultVOList, ydzfMonitorPlanListResultVOMBList, YdzfMonitorPlanListResultVOMB.class);
        	this.response.setYdzfMonitorPlanListResultVOMBList(ydzfMonitorPlanListResultVOMBList);
     
    	}
   	
    }
   
}
