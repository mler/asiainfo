package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.dto.ydzf.vo.YdzfBackLogListInfoVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfBackLogListInfoVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0011Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0011Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.backlog.IYDZFBacklogService;
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
@Service("ydzf0011")
@Action(bizcode="ydzf0011",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0011Action extends AbstractBaseActionHandler<YDZF0011Request,YDZF0011Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0011Action.class);
	@Autowired
	private IYDZFBacklogService ydzfBacklogService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	String saveStatus=this.request.getSaveStatus();
    	int total=0;
    	if(StringUtils.isBlank(saveStatus))
    	{
    		log.error("参数saveStatus异常.saveStatus:"+saveStatus);
			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("参数异常"));
    	}
    	else if(saveStatus.equals("1"))
    	{
    		total=ydzfBacklogService.querySaveCount(user);
    	}
    	else if(saveStatus.equals("9"))
    	{
    		total=ydzfBacklogService.querySubmitCount(user);
    	}
    	else
    	{
    		log.error("参数saveStatus异常.saveStatus:"+saveStatus);
			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("参数异常"));
    	}
    	PageInfo pageInfo=this.request.getPageInfo();
       	pageInfo.setTotalCount(total);
    	List<YdzfBackLogListInfoVO> ydzfBackLogListInfoVOlist=ydzfBacklogService.queryYdzfBackLogListInfo(user, saveStatus, pageInfo.getPageStart(), pageInfo.getPageCount());
    	this.response.setPageInfo(pageInfo);
    	if(!CollectionUtils.isEmpty(ydzfBackLogListInfoVOlist))
    	{
    	 	List<YdzfBackLogListInfoVOMB> ydzfBackLogListInfoVOMBList = new ArrayList<YdzfBackLogListInfoVOMB>(ydzfBackLogListInfoVOlist.size());
        	TransformMapperBeanUtil.DTOList2MBList(ydzfBackLogListInfoVOlist, ydzfBackLogListInfoVOMBList, YdzfBackLogListInfoVOMB.class);
        	this.response.setYdzfBackLogListInfoVOMBList(ydzfBackLogListInfoVOMBList);
    	}
   
    	
    }
   
}
