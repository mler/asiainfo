package com.bdx.rainbow.urs.service;

import com.bdx.rainbow.entity.urs.SysViews;

import java.util.List;

/**
 * Created by core on 16/2/1.
 */
public interface ISysViewService {
    List<SysViews> findCacheByKey(String key);

    SysViews findCacheByKeyAndValue(String key, String value);

    List<SysViews> findCacheByKey(String var1, String var2);

    List<SysViews> findCacheByKey(String var1, List<String> var2);

    SysViews findCacheByKeyAndValueAndPvalue(String var1, String var2, String var3);
}
