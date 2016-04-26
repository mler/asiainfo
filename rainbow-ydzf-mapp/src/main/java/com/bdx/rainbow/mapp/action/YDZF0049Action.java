package com.bdx.rainbow.mapp.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.bdx.rainbow.dto.ydzf.vo.YdzfInspectCaseWitnessesResultVO;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCaseWitnesses;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.req.YDZF0049Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0049Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.inspect.IYDZFInspectCaseWitnessesService;

/**
 * 听证查询
 * @param ydzfMonitorPlan
 * @param start
 * @param limit
 * @return
 * @throws BusinessException
 * @throws SystemException
 */
@Service("ydzf0049")
@Action(bizcode="ydzf0049",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0049Action extends AbstractBaseActionHandler<YDZF0049Request,YDZF0049Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0049Action.class);
	@Autowired
	private IYDZFInspectCaseWitnessesService ydzfInspectCaseWitnessesService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
		// IUserInfo user = (IUserInfo)
		// MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	YdzfInspectCaseWitnessesResultVO vo= new YdzfInspectCaseWitnessesResultVO();
    	 Integer inspectCaseWitnessesId=this.request.getInspectCaseWitnessesId();
    	 Integer inspectCaseId=this.request.getInspectCaseId();
    	if(inspectCaseWitnessesId!=null&&inspectCaseWitnessesId>0)
    	{
    		 vo =ydzfInspectCaseWitnessesService.queryInspectCaseWitnessesInfo(inspectCaseWitnessesId);
    	}
    	else if(inspectCaseId!=null&&inspectCaseId>0)
    	{
    		TYdzfInspectCaseWitnesses ydzfInspectCaseWitnesses= new TYdzfInspectCaseWitnesses();
    		ydzfInspectCaseWitnesses.setInspectCaseId(inspectCaseId);
    		List<YdzfInspectCaseWitnessesResultVO> volist=ydzfInspectCaseWitnessesService.queryInspectCaseWitnessesInfoVOList(ydzfInspectCaseWitnesses);
    		if(CollectionUtils.isEmpty(volist))
    		{
    			log.error("查询纪录异常，未找到听证纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("未找到听证纪录"));
    		}
    		else if(volist.size()>1)
    		{
    			log.error("查询纪录异常，有多条听证纪录");
    			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("有多条听证纪录"));
    		}
    		vo=volist.get(0);
    	}
    	else
    	{
    		log.error("查询参数为空");
			throw new  com.bdx.rainbow.common.exception.SystemException(new com.bdx.rainbow.common.exception.DefaultExceptionCode("查询参数为空"));
    	}
		TransformMapperBeanUtil.DTO2MB(vo, this.response);	
    }
   
}
