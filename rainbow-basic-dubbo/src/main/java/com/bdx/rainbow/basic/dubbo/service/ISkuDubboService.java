package com.bdx.rainbow.basic.dubbo.service;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.basic.dubbo.bean.DubboSku;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuFood;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuImag;

public interface ISkuDubboService {
	
	/**
	 * 分页获得SKU信息--dubbo
	 * @param condition
	 * @param start
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDubboSkuList(DubboSku condition,Integer enterpriseId,Integer start, Integer limit)throws Exception;
	
	
	/**
	 * 根据 条形码 获得商品信息
	 * @param barcode
	 * @return
	 * @throws Exception
	 */
	public DubboSku getDubboSkuByBarcode(String barcode) throws Exception;
	
	/**
	 * 根据 ID 获得具体商品信息
	 * @param barcode
	 * @return
	 * @throws Exception
	 */
	public DubboSku getDubboSkuDetailById(Integer skuId) throws Exception;
	
	/**
	 * 根据 商名条形码或名称等 获得商品信息
	 * @param barcode
	 * @return Map.list  Map.total
	 * @throws Exception
	 */
	public Map<String, Object> getDubboSku(String queryCondition,Integer enterpriseId,Integer start, Integer limit) throws Exception;
	
	
	/**
	 * 获得SKU图片信息
	 * @param skuId
	 * @param fileType 图片类型
	 * @return
	 * @throws Exception
	 */
	public List<DubboSkuImag> getDubboSkuImagsBySkuId(Integer skuId,String fileType)throws Exception;
	
	/**
	 * 保存食品数据 图片只做插入动作
	 * @param food
	 * @throws Exception
	 */
	public void saveDubboFood(DubboSkuFood food,List<byte[]> images,Integer enterpriseId,String userLoginName,Integer userId)throws Exception;
	
	
}
