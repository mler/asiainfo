package com.bdx.rainbow.common;

import com.bdx.rainbow.base.contextHolder.SpringContextHolder;
import com.bdx.rainbow.service.ydzf.common.IYDZFParamDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

/**
 * Created by core on 16/4/19.
 */
public class CachedDict {

    public static String getCachedName(String key, String value, String parent,String dubboName) throws Exception {
        if (value != null && StringUtils.isNotEmpty(value)) {
            IYDZFParamDetailService paramService = SpringContextHolder.getBean(IYDZFParamDetailService.class);
            String vc = paramService.getParamName(key, value,parent,dubboName);
            return vc;
        }else{
            return "";
        }
    }
}
