package com.bdx.rainbow.controller.ydzf.system;

import com.bdx.rainbow.mapp.core.JsonMapper;
import com.bdx.rainbow.service.ydzf.common.IYDZFParamDetailService;
import com.bdx.rainbow.urs.entity.ParamDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by core on 16/4/21.
 */
@RestController
public class CacheController {
    @Autowired
    private IYDZFParamDetailService paramDetailService;
    protected static JsonMapper binder = JsonMapper.nonDefaultMapper();
    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public String getCache(@RequestParam(value = "dict") String dict,
                           @RequestParam(value = "pValue", required = false, defaultValue = "") String pValue) {
        List<ParamDetail> caches = null;
        if (StringUtils.equals("brysj", pValue))
            return "[]";
        try {
            caches = paramDetailService.findCacheByKey(dict, pValue,"urs");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return binder.toJson(caches);
    }
}
