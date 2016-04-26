package com.bdx.rainbow.spsy.service;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.basic.dubbo.bean.DubboSku;
import com.bdx.rainbow.basic.dubbo.bean.DubboSkuFood;

/**
 * 企业商品信息操作
 * @author tanglian 2016-02-01
 *
 */
public interface ISkuService {
	/**
	 * 根据关键字进行商品信息查询
	 * @param condition
	 * @param enterpriseId
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSkuList(String querycondition,Integer enterpriseId, Integer start, Integer limit)throws Exception;
	
	/**
	 * 根据产品id获取商品信息
	 * @param skuId
	 * @return
	 * @throws Exception
	 */
	public DubboSkuFood getSku(Integer skuId) throws Exception;
	
	/**
	 * 新增商品信息
	 * @param sku
	 * @param companyId
	 * @param userLoginName
	 * @throws Exception
	 */
	public void insertSkuFood(DubboSkuFood sku,Integer companyId,String userLoginName) throws Exception;
	
	/**
	 * 商品查询
	 * @param sku
	 * @param enterpriseId
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getAllSku(DubboSku sku,int enterpriseId,int start,int limit) throws Exception;
	
	/**
	 * 改变产品的状态
	 * @param skuIds
	 * @param status
	 * @param companyId
	 * @param userLoginName
	 * @throws Exception
	 */
	public void changeStatus(List<Integer> skuIds,String status,int companyId,String userLoginName) throws Exception;
	
	/**
	 * 根据条形码获取商品信息
	 * @param skuBarcode
	 * @return
	 * @throws Exception
	 */
	public DubboSkuFood getSkuFood(String skuBarcode) throws Exception;
	
	/**
	 * 批量导入产品数据
	 * @param skus
	 * @param userLoginName
	 * @param companyId
	 * @throws Exception
	 */
	public void insertBatchSkuFood(List<DubboSkuFood> skus,String userLoginName,int companyId) throws Exception;
}
