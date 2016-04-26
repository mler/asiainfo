package com.bdx.rainbow.spsy.dal.ibatis.dao;

import com.bdx.rainbow.spsy.dal.ibatis.model.SelfTrace;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengwenjuan on 16/3/7.
 */
public interface SelfTraceDAO {

    /**
     * 商品追溯 list
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpjgTrace(Map<String, Object> map)throws Exception;

    /**
     * 商品追溯 count
     * @param map
     * @return
     * @throws Exception
     */
    public int countSpjgTrace(Map<String, Object> map)throws Exception;
    
    /**
     * 总的商品监管追溯数据
     * @param map
     * @return
     * @throws Exception
     */
    public int totalSpjgTrace(Map<String, Object> map)throws Exception;
    
    /**
     * 商品追溯图表数据 map
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpjgTraceChart(Map<String, Object> map)throws Exception;
    
    /**
     * 企业监管 list
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getQyjgTrace(Map<String, Object> map)throws Exception;
    
    /**
     * 企业监管 count
     * @param map
     * @return
     * @throws Exception
     */
    public int countQyjgTrace(Map<String, Object> map)throws Exception;
    
    /**
     * 总的企业监管追溯数据
     * @param map
     * @return
     * @throws Exception
     */
    public int totalQyjgTrace(Map<String, Object> map)throws Exception;
    
    /**
     * 企业追溯图表数据 map
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getQyjgTraceChart(Map<String, Object> map)throws Exception;
    
    /**
     * 获得商品追溯销售信息
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpzsSale(Map<String, Object> map)throws Exception;
    
    
    /**
     * 获得商品追溯流通信息
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpzsPass(Map<String, Object> map)throws Exception;
    
    /**
     * 获得商品追溯生产信息
     * @param map
     * @return
     * @throws Exception
     */
    public List<SelfTrace> getSpzsProduce(Map<String, Object> map)throws Exception;
    
    /**
     * 获得末端销售商IDS 
     * @param map
     * @return 逗号间隔
     * @throws Exception
     */
    public String getSpzsLastAgencyIds(Map<String, Object> map)throws Exception;
    
    /**
     * 从销售端追溯流通信息
     * @param map
     * @return
     * @throws Exception
     */
    public String getTraceFromOut(Map<String, Object> map)throws Exception;
    
    
}
