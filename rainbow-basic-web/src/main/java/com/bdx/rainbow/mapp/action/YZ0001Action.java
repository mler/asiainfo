package com.bdx.rainbow.mapp.action;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.common.SystemException;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.entity.basic.mysql.TMappUser;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.model.BasicMappUser;
import com.bdx.rainbow.mapp.model.req.YZ0001Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0001Response;
//import com.bdx.rainbow.service.jc.IProductAnalyzeService;
import com.bdx.rainbow.service.basic.ISkuAnalyzeService;

/**
 * 
 * mapp demo 2014/11/19 
 *
 */
@Service("yz0001")
@Action(bizcode="yz0001",version="1.0",usercheck=true, ipcheck=false)
@Scope("prototype")
public class YZ0001Action extends AbstractMappAction<YZ0001Request, YZ0001Response>{
	
//	@Autowired
//	private IProductAnalyzeService productAnalyzeService;
	
	@Autowired
	private ISkuAnalyzeService skuAnalyzeService;
	
	@Override
	public void doAction(YZ0001Request request, YZ0001Response response) throws Exception {
		
		if(StringUtils.isBlank(request.getBarcode()) == false)
		{
			if(StringUtil.isBlank(request.getBarcode()))
				throw new Exception("条形码信息不正确");
//			TMappUser userInfo = (TMappUser) MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
			BasicMappUser userInfo = (BasicMappUser) MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
//			productAnalyzeService.gdsAnalyze(request.getBarcode(), request.getContentMap(),userInfo.getUserId().toString());

			skuAnalyzeService.gdsAnalyze(request.getBarcode(), request.getContentMap(),userInfo.getUserInfo().getUser().getUserLoginName());
		}
		
	}
	

}
