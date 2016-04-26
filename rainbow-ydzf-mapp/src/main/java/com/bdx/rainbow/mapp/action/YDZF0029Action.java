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

import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceFinishHistoryVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfPunishAdviceFinishHistoryVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0029Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0029Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.history.impl.YDZFHistoryService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 *  稽查案件参考列表接口
 * @author fox
 *
 */
@Service("ydzf0029")
@Action(bizcode="ydzf0029",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0029Action extends AbstractBaseActionHandler<YDZF0029Request,YDZF0029Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0029Action.class);
	@Autowired
	private YDZFHistoryService ydzfHistoryService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	PageInfo pageInfo=this.request.getPageinfo();
    	String inspectCaseIds=this.request.getInspectCaseIds();
    	int total=0;
    	if(StringUtils.isBlank(inspectCaseIds))
    	{
    		pageInfo.setTotalCount(total);
        	this.response.setPageInfo(pageInfo);
    	}
    	else
    	{
    		total=inspectCaseIds.split(",").length;
    		pageInfo.setTotalCount(total);
        	this.response.setPageInfo(pageInfo);
    		List<YdzfPunishAdviceFinishHistoryVO>  volist=ydzfHistoryService.queryPunishAdviceFinishHistoryListInfo(user, inspectCaseIds, pageInfo.getPageStart(), pageInfo.getPageCount());
    		if(!CollectionUtils.isEmpty(volist))
        	{
        		List<YdzfPunishAdviceFinishHistoryVOMB> ydzfPunishAdviceFinishHistoryVOMBList=new ArrayList<YdzfPunishAdviceFinishHistoryVOMB>(volist.size());
        		TransformMapperBeanUtil.DTOList2MBList(volist, ydzfPunishAdviceFinishHistoryVOMBList, YdzfPunishAdviceFinishHistoryVOMB.class);
        		this.response.setYdzfPunishAdviceFinishHistoryVOMBList(ydzfPunishAdviceFinishHistoryVOMBList);
        	}
    	}
    }
   
}
