package com.bdx.rainbow.mapp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseTempletAndContentVO;
import com.bdx.rainbow.mapp.core.MappContext;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0038Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0038Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.templet.IYDZFInspectCaseTempletContentService;
import com.bdx.rainbow.urs.entity.IUserInfo;


/**
 * 日常任务项目查询接口(查询监管文书模版)
 * @author fox
 *
 */
@Service("ydzf0038")
@Action(bizcode="ydzf0038",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0038Action extends AbstractBaseActionHandler<YDZF0038Request,YDZF0038Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0038Action.class);
	@Autowired
	private IYDZFInspectCaseTempletContentService ydzfInspectCaseTempletContentService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
    	IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	Integer caseId=this.request.getInspectCaseId();
    	YdzfInspectCaseTempletAndContentVO vo=ydzfInspectCaseTempletContentService.queryInspectCaseTempleteAndContentInfoByCaseId(caseId);
    	TransformMapperBeanUtil.DTO2MB(vo, this.response);
    }
   
}
