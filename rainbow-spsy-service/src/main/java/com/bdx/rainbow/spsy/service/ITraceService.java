package com.bdx.rainbow.spsy.service;

import com.bdx.rainbow.spsy.dal.ibatis.model.SelfTrace;
import com.bdx.rainbow.spsy.dal.ibatis.model.TOriginProducerStockDetailIn;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengwenjuan on 16/3/7.
 */
public interface ITraceService {
	/**
	 * 商品追溯数据
	 * @param condition
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
    public Map<String,Object> getSpjgTraces(SelfTrace condition,int start,int limit)throws Exception;
    
    /**
     * 所有商品总的追溯数据
     * @param condition
     * @return
     * @throws Exception
     */
    public int totalSpjgTrace(SelfTrace condition)throws Exception;
    
    /**
     * 获取图表数据
     * @param skuId
     * @param YYYYMM
     * @return
     * @throws Exception
     */
    public Map<String, Object> getSpjgChartData(Integer skuId,String YYYYMM)throws Exception;
    
    /**
     * 获得企业监管数据
     * @param condition
     * @param start
     * @param limit
     * @return
     * @throws Exception
     */
    public Map<String, Object> getQyjgTraces(SelfTrace condition,int start,int limit)throws Exception;
    
    /**
     * 所有企业总的追溯数据
     * @param condition
     * @return
     * @throws Exception
     */
    public int totalQyjgTrace(SelfTrace condition)throws Exception;
    
    /**
     * 获取企业图表数据
     * @param skuId
     * @param YYYYMM
     * @return
     * @throws Exception
     */
    public Map<String, Object> getQyjgChartData(Integer enterpriseId,String YYYYMM)throws Exception;
    
    /**
     * 获得商品追溯销售信息
     * @param condition
     * @param start  暂无用
     * @param limit
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpzsSale(SelfTrace condition,int start,int limit)throws Exception;
    
    /**
     * 获得商品追溯流通信息
     * @param condition
     * @param start 暂无用
     * @param limit
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpzsPass(SelfTrace condition,int start,int limit)throws Exception;
    
    /**
     * 获得商品追溯生产信息
     * @param condition
     * @param start 暂无用
     * @param limit
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpzsProduce(SelfTrace condition,int start,int limit)throws Exception;
    
    /**
     * 根据溯源码查条形码和批次号
     * @param sym 溯源码
     * @return TOriginProducerStockIn.skuBarCode,TOriginProducerStockIn.skuBatch
     * @throws Exception
     */
    public TOriginProducerStockDetailIn getSkuBarCodeAndBatchBySYM(String sym)throws Exception;
    
}
