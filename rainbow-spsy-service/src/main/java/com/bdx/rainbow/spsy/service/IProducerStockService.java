package com.bdx.rainbow.spsy.service;

import java.util.List;
import java.util.Map;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.spsy.bean.ProducerStockOutInfo;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockIn;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockOut;

/**
 * 生产企业出入库信息操作
 * @author tanglian 2016-02-25
 *
 */
public interface IProducerStockService {
	/**
	 * 查询符合条件的入库信息
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @throws BusinessException
	 */
    public List<TOriginProducerStockIn> getProducerStockInList(TOriginProducerStockIn condition,int start,int limit) throws BusinessException;
    
    /**
     * 查询符合条件的入库信息总数
     * @param condition
     * @return
     * @throws BusinessException
     */
    public Integer coutProducerStockIns(TOriginProducerStockIn condition) throws BusinessException;
    
    /**
     * 生产企业入库信息新增
     * @param stockIn
     * @param codelist
     * @throws BusinessException
     */
    public void insertProducerStockIn(TOriginProducerStockIn stockIn, String codelist) throws BusinessException;
    
    /**
     * 查询符合条件的出库信息
     * @param condition
     * @param start
     * @param limit
     * @return
     * @throws BusinessException
     */
    public List<TOriginProducerStockOut> getProducerStockOutList(TOriginProducerStockOut condition,int start,int limit) throws BusinessException;
    
    /**
     * 查询符合条件的出库信息总数
     * @param condition
     * @return
     * @throws BusinessException
     */
    public Integer countProducerStockOuts(TOriginProducerStockOut condition) throws BusinessException;
    
    /**
     * 新增生产企业出库信息
     * @param stockOut
     * @throws BusinessException
     */
    public void insertProducerStockOut(ProducerStockOutInfo stockOut,Integer enterpriseId) throws BusinessException;
    
    /**
     * 根据操作编码查看入库信息
     * @param operationInId
     * @return
     * @throws BusinessException
     */
    public Map<String,Object> getProducerStockIn(Integer operationInId) throws BusinessException;
    
    /**
     * 修改入库记录
     * @param stockIn
     * @param codelist
     * @throws BusinessException
     */
    public void updateProducerStockIn(TOriginProducerStockIn stockIn,String codelist) throws BusinessException;
    
    /**
     * 根据主键获取出库信息记录
     * @param operationOutId
     * @return
     * @throws BusinessException
     */
    public Map<String,Object> getStockOutInf(Integer operationOutId) throws BusinessException; 
    
    /**
     * 修改出库记录
     * @param stockOut
     * @throws BusinessException
     */
    public void updateProducerStockOut(ProducerStockOutInfo stockOut,Integer enterpriseId) throws BusinessException;
}
