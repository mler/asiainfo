package com.bdx.rainbow.mapp.action;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.form.YdzfEnterpriseHistoryQueryInfo;
import com.bdx.rainbow.dto.ydzf.vo.YdzfEnterpriseHistoryCountVO;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.bean.YdzfEnterpriseInfoVOMB;
import com.bdx.rainbow.mapp.model.req.YDZF0013Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0013Response;
import com.bdx.rainbow.service.ydzf.history.impl.YDZFHistoryService;


/**
 * 监管历史接口
 * @author fox
 *
 */
@Service("ydzf0013")
@Action(bizcode="ydzf0013",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0013Action extends AbstractBaseActionHandler<YDZF0013Request,YDZF0013Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0013Action.class);
	@Autowired
	private YDZFHistoryService ydzfHistoryService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	//IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	int  manageEnterpriseId=this.request.getManageEnterpriseId();
    	Timestamp startDate=this.request.getStartDate();
    	Timestamp endDate=this.request.getEndDate();
    	YdzfEnterpriseHistoryQueryInfo ydzfEnterpriseHistoryQueryInfo= new YdzfEnterpriseHistoryQueryInfo();
    	ydzfEnterpriseHistoryQueryInfo.setStartDate(startDate);
    	ydzfEnterpriseHistoryQueryInfo.setEndDate(endDate);
    	ydzfEnterpriseHistoryQueryInfo.setManageEnterpriseId(manageEnterpriseId);
    	YdzfEnterpriseHistoryCountVO vo=ydzfHistoryService.queryEnterpriseHistoryCount(ydzfEnterpriseHistoryQueryInfo);
    	YdzfEnterpriseInfoVOMB ydzfEnterpriseInfoVOMB=new YdzfEnterpriseInfoVOMB();
    	if(vo!=null&&vo.getYdzfEnterpriseInfoVO()!=null)
    	{
    		BeanUtils.copyProperties(vo.getYdzfEnterpriseInfoVO(), ydzfEnterpriseInfoVOMB);
        	this.response.setYdzfEnterpriseInfoVOMB(ydzfEnterpriseInfoVOMB);
        	this.response.setCheckJsonInfo(vo.getCheckJsonInfo());
    	}

    }
   
}
