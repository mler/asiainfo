package com.bdx.rainbow.urs.dubbo;

import com.bdx.rainbow.urs.entity.ParamDetail;

import java.util.List;

/**
 * Created by core on 16/2/3.
 */
public interface IDubDicService {
    List<ParamDetail> findCacheByKey(String key)throws Exception;
    String getParamName(String key, String value, String parent)throws Exception;

    /**
     *
     * @param key
     * @param pValue 可为空
     * @return
     * @throws Exception
     */
    List<ParamDetail> findCacheByKeyAndPValue(String key,String pValue)throws Exception;
}
