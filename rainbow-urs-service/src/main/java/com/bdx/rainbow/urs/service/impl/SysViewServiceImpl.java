package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.entity.urs.SysViews;
import com.bdx.rainbow.entity.urs.SysViewsExample;
import com.bdx.rainbow.mapper.urs.SysViewsMapper;
import com.bdx.rainbow.urs.service.ISysViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by core on 16/2/1.
 */
@Service()
public class SysViewServiceImpl implements ISysViewService {
    @Autowired
    private SysViewsMapper viewCacheDAO;
    @Override
    @Cacheable(
            value = {"sysCache"},
            key = "#key"
    )
    public List<SysViews> findCacheByKey(String key) {
        SysViewsExample example = new SysViewsExample();
        example.createCriteria().andPKeyEqualTo(key);
        System.out.println("search.....");
        return this.viewCacheDAO.selectByExample(example);
    }

    @Override
    @Cacheable(
            value = {"sysCache"},
            key = "#key + #value"
    )
    public SysViews findCacheByKeyAndValue(String key, String value) {
        SysViewsExample example = new SysViewsExample();
        example.createCriteria().andPKeyEqualTo(key).andPValueEqualTo(value);
        List vs = this.viewCacheDAO.selectByExample(example);
        return vs.size() == 1?(SysViews)vs.get(0):null;
    }

    @Override
    @Cacheable(
            value = {"sysCache"},
            key = "#key + #pValue"
    )
    public List<SysViews> findCacheByKey(String key, String pValue) {
        SysViewsExample example = new SysViewsExample();
        example.createCriteria().andPKeyEqualTo(key).andPPValueEqualTo(pValue);
        List vs = this.viewCacheDAO.selectByExample(example);
        return vs;
    }

    @Override
    @Cacheable(
            value = {"sysCache"},
            key = "#key + #pValueList.toString()"
    )
    public List<SysViews> findCacheByKey(String key, List<String> pValueList) {
        SysViewsExample example = new SysViewsExample();
        example.createCriteria().andPKeyEqualTo(key).andPPValueIn(pValueList);
        List vs = this.viewCacheDAO.selectByExample(example);
        return vs;
    }
//
    @Override
    @Cacheable(
            value = {"sysCache"},
            key = "#key + #value + #pValue"
    )
    public SysViews findCacheByKeyAndValueAndPvalue(String key, String value, String pValue) {
        SysViewsExample example = new SysViewsExample();
        example.createCriteria().andPKeyEqualTo(key).andPValueEqualTo(value).andPPValueEqualTo(pValue);
        List vs = this.viewCacheDAO.selectByExample(example);
        return vs.size() == 1?(SysViews)vs.get(0):null;
    }
}
