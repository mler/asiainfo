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

import com.bdx.rainbow.dto.ydzf.form.YdzfEnterpriseHistoryQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryListInfoVO;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseHistoryListInfoVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0051Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0051Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.history.impl.YDZFHistoryService;


/**
 * 监管历史接口(列表详情)
 * @author fox
 *
 */
@Service("ydzf0051")
@Action(bizcode="ydzf0051",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0051Action extends AbstractBaseActionHandler<YDZF0051Request,YDZF0051Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0051Action.class);
	@Autowired
	private YDZFHistoryService ydzfHistoryService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	//IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	PageInfo pageInfo=this.request.getPageInfo();
    	YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo= new YdzfEnterpriseHistoryQueryInfo();
    	BeanUtils.copyProperties(this.request, ydzfEnterpriseHistoryQueryInfo);
    	List<YdzfEnterpriseHistoryListInfoVO> volist=ydzfHistoryService.queryEnterpriseHistoryListInfo(ydzfEnterpriseHistoryQueryInfo, pageInfo.getPageStart(), pageInfo.getPageCount());
    	int total=ydzfHistoryService.queryEnterpriseHistoryListInfoCount(ydzfEnterpriseHistoryQueryInfo);
    	pageInfo.setTotalCount(total);
    	this.response.setPageInfo(pageInfo);
    	if(!CollectionUtils.isEmpty(volist))
    	{
    		List<YdzfEnterpriseHistoryListInfoVOMB> ydzfEnterpriseHistoryListInfoVOMBList=new ArrayList<YdzfEnterpriseHistoryListInfoVOMB>(volist.size());
    		TransformMapperBeanUtil.DTOList2MBList(volist, ydzfEnterpriseHistoryListInfoVOMBList, YdzfEnterpriseHistoryListInfoVOMB.class);
    		this.response.setYdzfEnterpriseHistoryListInfoVOMBList(ydzfEnterpriseHistoryListInfoVOMBList);
    	}

    	
    	
    }
   
}
