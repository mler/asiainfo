package com.bdx.rainbow.service.ydzf.common;

import com.bdx.rainbow.urs.entity.ParamDetail;

import java.util.List;

/**
 * Created by core on 16/4/19.
 */
public interface IYDZFParamDetailService {
    /**
     * 根据key查询出数据字典
     * @param key
     * @param pValue 可为空
     * @return
     * @throws Exception
     */
    public List<ParamDetail> findCacheByKey(String key, String pValue,String dubboName)throws Exception;

    /**
     * 查询中文翻译
     * @param key
     * @param value
     * @throws Exception
     */
    public String getParamName(String key,String value, String pValue,String dubboName) throws Exception;
}
