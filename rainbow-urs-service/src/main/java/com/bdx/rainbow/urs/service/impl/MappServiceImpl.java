package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.SysMappApnsToken;
import com.bdx.rainbow.entity.urs.SysMappApnsTokenExample;
import com.bdx.rainbow.mapper.urs.SysMappApnsTokenMapper;
import com.bdx.rainbow.urs.service.ImappService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ban on 2016/3/16.
 */
@Service
public class MappServiceImpl implements ImappService {
    @Autowired
    SysMappApnsTokenMapper sysMappApnsTokenMapper;
    @Override
        public SysMappApnsToken getMappsBytoken(String token) {
        return  sysMappApnsTokenMapper.selectByPrimaryKey(token);

    }

    @Override
    public List<SysMappApnsToken> getMapps(SysMappApnsToken record,Integer start, Integer count) {
      SysMappApnsTokenExample where=getCondition(record);
        if (start != null) {
            where.setLimitClauseStart(start);
            where.setLimitClauseCount(count);
        }
        return sysMappApnsTokenMapper.selectByExample(where);
    }

    @Override
    public int countMapp(SysMappApnsToken record) throws SystemException, BusinessException {
        SysMappApnsTokenExample where=getCondition(record);
        return  sysMappApnsTokenMapper.countByExample(where);




    }

    @Override
    public int insertMapp(SysMappApnsToken record) throws SystemException, BusinessException {
        return sysMappApnsTokenMapper.insertSelective(record);
    }

    @Override
    public void updateMappValidBytokens(List<String> tokens, String valid) throws SystemException, BusinessException {
        for(int i=0;i<tokens.size();i++){
            SysMappApnsToken apns =new SysMappApnsToken();
            apns.setValid(valid);
            apns.setToken(tokens.get(i));
            sysMappApnsTokenMapper.updateByPrimaryKeySelective(apns);
        }
    }




    @Override
    public int updateMapp(SysMappApnsToken record) throws SystemException, BusinessException {
        return sysMappApnsTokenMapper.updateByPrimaryKey(record);
    }
    private SysMappApnsTokenExample getCondition(SysMappApnsToken record){
        SysMappApnsTokenExample where=new SysMappApnsTokenExample();
        if (record != null) {
            SysMappApnsTokenExample.Criteria cr = where.createCriteria();
            if (record.getToken() != null && StringUtils.isNotEmpty(record.getToken())) {
                cr.andTokenLike("%" + record.getToken() + "%");
            }
            if (record.getUsercode() != null && StringUtils.isNotEmpty(record.getUsercode().toString())) {
                cr.andUsercodeEqualTo(record.getUsercode());
            }


        }
        return where;
    }
}
