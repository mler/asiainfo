package com.bdx.rainbow.spsy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdx.rainbow.basic.dubbo.bean.DubboSku;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuFood;
import com.bdx.rainbow.basic.dubbo.service.ISkuDubboService;
import com.bdx.rainbow.spsy.bean.SkuInfo;
import com.bdx.rainbow.spsy.common.SpsyConstants;
import com.bdx.rainbow.spsy.service.IIdentificationCodeService;
import com.bdx.rainbow.spsy.service.ISkuService;

public class SkuServiceImpl implements ISkuService {

	@Autowired
	private ISkuDubboService skuDubboService;
	
	@Autowired
	private IIdentificationCodeService identificationCodeService;
		
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSkuList(String queryCondition, Integer enterpriseId,Integer start,Integer limit) throws Exception {		
		Map<String,Object> skumap = skuDubboService.getDubboSku(queryCondition,enterpriseId,start, limit);
		List<DubboSku> skus = (List<DubboSku>) skumap.get("list");
		List<SkuInfo> newskus = new ArrayList<SkuInfo>();
		if(skus!=null && skus.size()>0){
			for(int i=0;i<skus.size();i++){
				DubboSku sku = skus.get(i);
				SkuInfo skuinfo = new SkuInfo();
				BeanUtils.copyProperties(sku, skuinfo);
				String flag = identificationCodeService.isDefinitionCode(sku.getSkuId());
				skuinfo.setFlag(flag);
				newskus.add(skuinfo);
			}
		}
		skumap.put("list", newskus);
        return skumap;
	}

	@Override
	public DubboSkuFood getSku(Integer skuId) throws Exception {
		DubboSkuFood sku = new DubboSkuFood();
		try{
		   DubboSku skuf = (DubboSku) skuDubboService.getDubboSkuDetailById(skuId);
		   BeanUtils.copyProperties(skuf, sku);
		}catch(Exception e){
			e.printStackTrace();
		}
		return sku;
	}

	@Override
	public void insertSkuFood(DubboSkuFood sku,Integer companyId,String userLoginName) throws Exception {
		if(sku.getSkuId()==null){
			DubboSkuFood skufood = this.getSkuFood(sku.getSkuBarcode());
			if(skufood!=null){
				sku.setSkuId(skufood.getSkuId());
			}
		}
		sku.setStatus(SpsyConstants.STATUS_ACTIVE);
		skuDubboService.saveDubboFood(sku,null,companyId,userLoginName,null);
	}

	@Override
	public Map<String, Object> getAllSku(DubboSku sku, int enterpriseId,int start, int limit) throws Exception {
	   Map<String,Object> skumap = skuDubboService.getDubboSkuList(sku, enterpriseId, -1, 0);
       return skumap;
	}

	@Override
	public void changeStatus(List<Integer> skuIds, String status,int companyId,String userLoginName) throws Exception {
		for(int i=0;i<skuIds.size();i++){
			DubboSkuFood skuFood = new DubboSkuFood();
			skuFood.setSkuId(skuIds.get(i));
			skuFood.setStatus(status);
			skuDubboService.saveDubboFood(skuFood,null,companyId,userLoginName,null);
		}
	}

	@Override
	public DubboSkuFood getSkuFood(String skuBarcode) throws Exception {
		DubboSkuFood skufood = null;
		DubboSku sku = skuDubboService.getDubboSkuByBarcode(skuBarcode);
		if(sku!=null){
			skufood = new DubboSkuFood();
			BeanUtils.copyProperties(sku, skufood);
		}
		return skufood;
	}

	@Override
	public void insertBatchSkuFood(List<DubboSkuFood> skus,String userLoginName, int companyId) throws Exception {
		for(int i=0;i<skus.size();i++){
			DubboSkuFood food = skus.get(i);
			food.setStatus(SpsyConstants.STATUS_ACTIVE);
			this.insertSkuFood(food, companyId, userLoginName);
		}		
	}

}
