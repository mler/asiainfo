package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfMonitorTempletAndContentVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0007Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0007Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.templet.IYDZFMonitorTempletContentService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 * 日常任务项目查询接口(查询监管文书模版)
 * @author fox
 *
 */
@Service("ydzf0007")
@Action(bizcode="ydzf0007",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0007Action extends AbstractBaseActionHandler<YDZF0007Request,YDZF0007Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0007Action.class);
	@Autowired
	private IYDZFMonitorTempletContentService ydzfMonitorTempletContentService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	Integer monitorPlanId=this.request.getMonitorPlanId();
    	YdzfMonitorTempletAndContentVO vo=ydzfMonitorTempletContentService.queryMonitorTempleteAndContentInfoByPlanId(monitorPlanId);
    	TransformMapperBeanUtil.DTO2MB(vo, this.response);
    }
   
}
