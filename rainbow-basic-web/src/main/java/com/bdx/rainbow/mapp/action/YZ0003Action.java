package com.bdx.rainbow.mapp.action;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.entity.basic.mysql.TBasicSku;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.model.BasicMappUser;
import com.bdx.rainbow.mapp.model.req.YZ0003Request;
import com.bdx.rainbow.mapp.model.rsp.YZ0003Response;
import com.bdx.rainbow.service.basic.IBasicSkuService;
import com.bdx.rainbow.service.jc.ISkuService;

/**
 * 添加、修改商品
 * mapp demo 2014/11/19 
 *
 */
@Service("yz0003")
@Action(bizcode="yz0003",version="1.0",usercheck=true, ipcheck=false)
@Scope("prototype")
public class YZ0003Action extends AbstractMappAction<YZ0003Request, YZ0003Response>{

//	@Autowired
//	private ISkuService skuService;
	
	@Autowired
	private IBasicSkuService basicSkuService;
	
	@Override
	public void doAction(YZ0003Request request, YZ0003Response response) throws Exception {
		
//		IUserInfo userInfo = (IUserInfo) MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
		BasicMappUser userInfo = (BasicMappUser) MappContext.getContext().get(MappContext.MAPPCONTEXT_USER);
		/**
		 * 
		JcSku sku = new JcSku();
		sku.setSkuBarcode(request.getCode());
		sku.setSkuName(request.getName());
		sku.setSkuSpec(request.getSpec());
		sku.setSkuBrand(request.getBrand());
		sku.setpName(request.getpName());
		sku.setpAddress(request.getpAddress());
		sku.setpArea(request.getpArea());
		
		skuService.saveSkuAndUploadImage(sku, request.getIco(), request.getImgs(),userInfo == null?null:userInfo.getUserId(), userInfo == null?null:userInfo.getUser().getUserLoginName());
		 */
		
		TBasicSku sku = new TBasicSku();
		sku.setSkuBarcode(request.getCode());
		sku.setSkuName(request.getName());
		sku.setSpec(request.getSpec());
		sku.setBrand(request.getBrand());
		sku.setCompanyName(request.getpName());
		sku.setProductAddress(request.getpAddress());
		sku.setProductionArea(request.getpArea());
		basicSkuService.saveSkuAndUploadImage(sku, request.getIco(), request.getImgs(),userInfo == null?null:Integer.parseInt(userInfo.getUserId()), userInfo == null?null:userInfo.getUserInfo().getUser().getUserLoginName());
		
	}
	

}
