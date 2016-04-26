package com.bdx.rainbow.util;

import com.bdx.rainbow.entity.urs.SysViews;
import com.bdx.rainbow.urs.service.ISysViewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

/**
 * Created by core on 16/2/1.
 */
public class CachedDict {
    private static ApplicationContext applicationContext;
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
    public static String getCachedName(String key, String value, String parent) {
        if (value != null && StringUtils.isNotEmpty(value)) {
            ISysViewService sysViewService = applicationContext.getBean(ISysViewService.class);//BeanFactory.getObject("sysViewService",ISysViewService.class);
            SysViews vc=null;
            if(parent!=null&&StringUtils.isNotBlank(parent)){
                vc=sysViewService.findCacheByKeyAndValueAndPvalue(key,value,parent);
            }else {
                vc = sysViewService.findCacheByKeyAndValue(key, value);
            }
            return vc != null ? vc.getpDesc() : null;
        } else
        {
            return "";
        }
    }

}
