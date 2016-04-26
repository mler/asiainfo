package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bdx.rainbow.dto.ydzf.vo.YdzfPunishAdviceHistoryVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfPunishAdviceHistoryVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0027Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0027Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.history.impl.YDZFHistoryService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 *  稽查案件参考列表接口
 * @author fox
 *
 */
@Service("ydzf0027")
@Action(bizcode="ydzf0027",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0027Action extends AbstractBaseActionHandler<YDZF0027Request,YDZF0027Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0027Action.class);
	@Autowired
	private YDZFHistoryService ydzfHistoryService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	PageInfo pageInfo=this.request.getPageinfo();
    	List<YdzfPunishAdviceHistoryVO>  volist=ydzfHistoryService.queryPunishAdviceHistoryListInfo(user, this.request.getInspectCaseId(), pageInfo.getPageStart(), pageInfo.getPageCount());
    	int total=ydzfHistoryService.queryPunishAdviceHistoryListInfoCount(user, this.request.getInspectCaseId());
    	pageInfo.setTotalCount(total);
    	this.response.setPageInfo(pageInfo);
    	if(!CollectionUtils.isEmpty(volist))
    	{
    		List<YdzfPunishAdviceHistoryVOMB> ydzfPunishAdviceHistoryVOMBList=new ArrayList<YdzfPunishAdviceHistoryVOMB>(volist.size());
    		TransformMapperBeanUtil.DTOList2MBList(volist, ydzfPunishAdviceHistoryVOMBList, YdzfPunishAdviceHistoryVOMB.class);
    		this.response.setYdzfPunishAdviceHistoryVOMBList(ydzfPunishAdviceHistoryVOMBList);
    	}

    	
    	
    }
   
}
