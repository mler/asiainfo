package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.entity.urs.SysParamDetail;
import com.bdx.rainbow.entity.urs.SysParamDetailExample;
import com.bdx.rainbow.mapper.urs.SysParamDetailMapper;
import com.bdx.rainbow.urs.service.ISysParamService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by core on 16/2/17.
 */
@Service
public class SysParamServiceImpl implements ISysParamService {
    @Autowired
    private SysParamDetailMapper paramDetailMapper;
    @Override
    public Integer insertParam(SysParamDetail record) {
        return paramDetailMapper.insertSelective(record);
    }

    @Override
    public List<SysParamDetail> getParams(SysParamDetail record, Integer start, Integer count) {
        SysParamDetailExample where=getCondition(record);
        if(start != null) {
            where.setLimitClauseStart(start);
            where.setLimitClauseCount(count);
        }
        return paramDetailMapper.selectByExample(where);
    }

    @Override
    public int countParam(SysParamDetail record) {
        SysParamDetailExample where=getCondition(record);
        return paramDetailMapper.countByExample(where);
    }

    @Override
    public int updateParam(SysParamDetail record,String str1,String str2) {
        SysParamDetail detail=new SysParamDetail();
        detail.setpKey(str1);
        detail.setpValue(str2);
        SysParamDetailExample where=getCondition(detail);

        return paramDetailMapper.updateByExampleSelective(record,where);
    }

    @Override
    public SysParamDetail getParam(String pKey, String pValue) {
        SysParamDetail record=new SysParamDetail();
        record.setpKey(pKey);
        record.setpValue(pValue);
        SysParamDetailExample where=getCondition(record);
        SysParamDetail detail=paramDetailMapper.selectByExample(where).get(0);
        return detail;
    }

    @Override
    public void deleteParam(SysParamDetail record) {
        SysParamDetailExample where=getCondition(record);
        paramDetailMapper.deleteByExample(where);
    }


    private SysParamDetailExample getCondition(SysParamDetail record) {
        SysParamDetailExample where = new SysParamDetailExample();
        if (record != null) {
            SysParamDetailExample.Criteria cr = where.createCriteria();
            if (record.getpKey()!=null&& StringUtils.isNotEmpty(record.getpKey())){
                //cr.andPKeyEqualTo(record.getpKey());
                cr.andPKeyLike("%"+record.getpKey()+"%");

            }
            if (record.getpValue()!=null&&StringUtils.isNotEmpty(record.getpValue())){
                cr.andPValueEqualTo(record.getpValue());
            }
            if (record.getpDesc()!=null&&StringUtils.isNotEmpty(record.getpDesc())){
                cr.andPDescEqualTo(record.getpDesc());
            }
        }
        return where;
    }
}
