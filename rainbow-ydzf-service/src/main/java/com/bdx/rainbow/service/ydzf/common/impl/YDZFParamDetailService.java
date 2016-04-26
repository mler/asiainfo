package com.bdx.rainbow.service.ydzf.common.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdx.rainbow.service.ydzf.common.IYDZFParamDetailService;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.entity.ParamDetail;

/**
 * Created by core on 16/4/19.
 */
@Service("ydzfParamDetailService")
public class YDZFParamDetailService implements IYDZFParamDetailService {
    @Autowired
    private IDubDicService dicService;
    @Override
    public List<ParamDetail> findCacheByKey(String key,String pValue,String dubboName) throws Exception {
        if ("urs".equals(dubboName)){
            return dicService.findCacheByKeyAndPValue(key,pValue);
        }else if("basic".equals(dubboName)){
            return null;
        }
        return null;
    }

    @Override
    public String getParamName(String key, String value, String pValue,String dubboName) throws Exception {
        String paramName = "";
        if ("urs".equals(dubboName)){
            paramName = dicService.getParamName(key, value, pValue);
        }else if("basic".equals(dubboName)){

        }
        return paramName;
    }
}
