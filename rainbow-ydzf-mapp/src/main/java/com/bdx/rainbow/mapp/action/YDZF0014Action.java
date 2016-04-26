package com.bdx.rainbow.mapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.basic.dubbo.bean.DubboLaw;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.exception.BusinessException;
import com.bdx.rainbow.mapp.core.exception.SystemException;
import com.bdx.rainbow.mapp.model.PageInfo;
import com.bdx.rainbow.mapp.model.bean.DubboLawMB;
import com.bdx.rainbow.mapp.model.req.YDZF0014Request;
import com.bdx.rainbow.mapp.model.rsp.YDZF0014Response;
import com.bdx.rainbow.mapp.util.TransformMapperBeanUtil;
import com.bdx.rainbow.service.ydzf.common.IYDZFLawService;


/**
 * 法律法规目录查询
 * @author fox
 *
 */
@Service("ydzf0014")
@Action(bizcode="ydzf0014",userCheck=true, ipCheck=false)
@Scope("prototype")
public class YDZF0014Action extends AbstractBaseActionHandler<YDZF0014Request,YDZF0014Response> {
	private static final Logger log = LoggerFactory
			.getLogger(YDZF0014Action.class);
	@Autowired
	private IYDZFLawService ydzfLawService;
    @Override
    protected void doAction() throws BusinessException, SystemException, Exception {
      	//IUserInfo user = (IUserInfo) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);
    	PageInfo pageInfo=this.request.getPageinfo();
    	DubboLawMB dubboLawMB=this.request.getDubboLawMB();
    	DubboLaw dubboLaw= new DubboLaw();
    	if(dubboLawMB!=null)
    	{
    		BeanUtils.copyProperties(dubboLawMB, dubboLaw);
    	}
    	Map<String, Object> resultMap=ydzfLawService.getNodeListDubbo(dubboLaw, this.request.getStartTime(), this.request.getEndTime(), pageInfo.getPageStart(), pageInfo.getPageCount());
    	if(StringUtil.isNotEmptyObject(resultMap.get("total")))
    	{
    		pageInfo.setTotalCount(Integer.parseInt(resultMap.get("total").toString()));
    		this.response.setPageInfo(pageInfo);
    	}
    	if(resultMap.get("list")!=null)
    	{
    		List<DubboLaw> list=(List<DubboLaw>)resultMap.get("list");
    		List<DubboLawMB> mbList= new ArrayList<DubboLawMB>();
        	TransformMapperBeanUtil.DTOList2MBList(list, mbList, DubboLawMB.class);
        	this.response.setDubboLawMBList(mbList);
    	}
    	
    	
    	

    	
    	
    }
   
}
