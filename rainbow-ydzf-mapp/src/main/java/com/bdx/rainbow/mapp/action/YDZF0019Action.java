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

import com.bdx.rainbow.dto.ydzf.form.YdzfInspectCaseListQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseListResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.TYdzfInspectCaseMB;
import com.bdx.rainbow.mapp.model.bean.YdzfInspectCaseListResultVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0019Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0019Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseService;
import com.bdx.rainbow.urs.entity.IUserInfo;

/**
 * 查询稽查案件列表
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0019")
@Action(bizcode="ydzf0019",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0019Action extends AbstractBaseActionHandler<YDZF0019Request,YDZF0019Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0019Action.class);
	@Autowired
	private IYDZFInspectCaseService ydzfInspectCaseService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	TYdzfInspectCaseMB ydzfInspectCaseMB=this.request.getYdzfInspectCaseMB();
    	PageInfo pageInfo=this.request.getPageInfo();
    	if(ydzfInspectCaseMB==null)
    	{
    		ydzfInspectCaseMB= new TYdzfInspectCaseMB();
    	}
    	//组织id 要自己设置吗？
    	if(ydzfInspectCaseMB.getInspectCaseCreatDepId()==null||ydzfInspectCaseMB.getInspectCaseCreatDepId()==0)
    	{
    		ydzfInspectCaseMB.setInspectCaseCreatDepId(user.getUser().getDeptId());
    	}
    	if(ydzfInspectCaseMB.getInspectCaseMainUserId()==null||ydzfInspectCaseMB.getInspectCaseMainUserId()==0)
    	{
    		ydzfInspectCaseMB.setInspectCaseMainUserId(user.getUserId());
    	}
    	TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
    	BeanUtils.copyProperties(ydzfInspectCaseMB,ydzfInspectCase);
    	YdzfInspectCaseListQueryInfo ydzfInspectCaseListQueryInfo= new YdzfInspectCaseListQueryInfo();
    	ydzfInspectCaseListQueryInfo.setYdzfInspectCase(ydzfInspectCase);
    	List<YdzfInspectCaseListResultVO> resultvoList=ydzfInspectCaseService.queryInspectCaseList(ydzfInspectCaseListQueryInfo, pageInfo.getPageStart(), pageInfo.getPageCount());
    	int total=ydzfInspectCaseService.countInspectCaseList(ydzfInspectCaseListQueryInfo);
    	pageInfo.setTotalCount(total);
    	this.response.setPageInfo(pageInfo);
    	if(!CollectionUtils.isEmpty(resultvoList))
    	{
    	List<YdzfInspectCaseListResultVOMB> ydzfInspectCaseListResultVOMBList=new ArrayList<YdzfInspectCaseListResultVOMB>(resultvoList.size());
    	TransformMapperBeanUtil.DTOList2MBList(resultvoList, ydzfInspectCaseListResultVOMBList, YdzfInspectCaseListResultVOMB.class);
    	this.response.setYdzfInspectCaseListResultVOMBList(ydzfInspectCaseListResultVOMBList);
    	}
    }
   
}
