package com.bdx.rainbow.spsy.service;

import java.util.List;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.bean.GoodStockInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginGoodsStock;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginMerchantStockOut;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginSkuAgencyRelation;

/**
 * 经销商和零售商库存操作
 * @author tanglian 2016-02-23
 *
 */
public interface IGoodsStockInfoService {  
	/**
     * 查询符合条件的零售商出库信息
     * @param condition
     * @param start
     * @param limit
     * @return
     * @throws BusinessException
     */
	public List<TOriginMerchantStockOut> getMerchantStockList(TOriginMerchantStockOut condition,int start,int limit) throws BusinessException;
	
	/**
	 * 符合条件的零售商出库信息记录
	 * @param condition
	 * @return
	 * @throws BusinessException
	 */
	public Integer countMerchantStock(TOriginMerchantStockOut condition) throws BusinessException;
	
	/**
	 * 新增经销商出库信息
	 * @param stockOut
	 * @throws BusinessException
	 */
	public void insertMerchantStockOut(TOriginMerchantStockOut stockOut) throws BusinessException;
	
	/**
	 * 修改经销商出库信息
	 * @param stockOut
	 * @throws BusinessException
	 */
	public void updateMerchantStockOut(TOriginMerchantStockOut stockOut) throws BusinessException;
	
	/**
	 * 根据主键查询零售商出库信息
	 * @param operationOutId
	 * @return
	 * @throws BusinessException
	 */
	public TOriginMerchantStockOut getMerchantStock(int operationOutId) throws BusinessException;
	
	/**
	 * 查询符合条件的商品库存信息
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 */
	public List<TOriginGoodsStock> getGoodsStockList(TOriginGoodsStock condition,int start,int limit) throws BusinessException;
	
	/**
	 * 查询符合条件的商品库存信息记录数
	 * @param condition
	 * @return
	 * @throws BusinessException
	 */
	public Integer countGoodsStock(TOriginGoodsStock condition) throws BusinessException;
	
	/**
	 * 新增商品库存信息
	 * @param stock
	 * @throws BusinessException
	 */
	public void insertGoodsStock(TOriginGoodsStock stock) throws BusinessException;
	
	/**
	 * 修改商品库存信息
	 * @param stock
	 * @throws BusinessException
	 */
	public void updateGoodsStock(TOriginGoodsStock stock) throws BusinessException;
	
	/**
	 * 根据主键获取商品库存信息
	 * @param operationId
	 * @return
	 * @throws BusinessException
	 */
	public TOriginGoodsStock getGoodsStock(int operationId) throws BusinessException;
	
	/**
	 * 获取经销商能经营的产品
	 * @param condition
	 * @return
	 * @throws BusinessException
	 */
	public List<TOriginSkuAgencyRelation> getSkuList(TOriginSkuAgencyRelation condition) throws BusinessException;
	
	/**
	 * 从入库信息导入
	 * @param stock
	 * @throws BusinessException
	 */
	public void insertGoodsStockFromIn(GoodStockInfo stock,Integer enterpriseId) throws BusinessException;
	
	/**
	 * 修改出库信息
	 * @param stock
	 * @param enterpriseId
	 * @throws BusinessException
	 */
	public void updateGoodStockOut(GoodStockInfo stock,Integer enterpriseId) throws BusinessException;
	
}
