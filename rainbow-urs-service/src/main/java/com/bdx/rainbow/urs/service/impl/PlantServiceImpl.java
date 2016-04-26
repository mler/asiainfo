package com.bdx.rainbow.urs.service.impl;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.urs.SysPlatfrom;
import com.bdx.rainbow.entity.urs.SysPlatfromExample;
import com.bdx.rainbow.mapper.urs.SysPlatfromMapper;
import com.bdx.rainbow.mapper.urs.SysPowerCommMapper;
import com.bdx.rainbow.urs.service.IPlantService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/1/28.
 */
@Service
public class PlantServiceImpl implements IPlantService {
    @Autowired
    protected SysPowerCommMapper sysPowerCommMapper;
    @Autowired
    private SysPlatfromMapper sysPlatfromMapper;

    @Override
    public List<SysPlatfrom> getPlatsByUserId(Map<String, Object> condition) {
        return sysPowerCommMapper.getPlatsByUserId(condition);
    }

    @Override
    public List<SysPlatfrom> getPlats(SysPlatfrom plat, Integer start, Integer count) {
        SysPlatfromExample where = getCondition(plat);
        if (start != null) {
            where.setLimitClauseStart(start);
            where.setLimitClauseCount(count);
        }

        return sysPlatfromMapper.selectByExample(where);
    }

    @Override
    public int countPlat(SysPlatfrom record) throws SystemException, BusinessException {
        SysPlatfromExample where = getCondition(record);
        return sysPlatfromMapper.countByExample(where);
    }

    @Override
    public int insertPlat(SysPlatfrom record) throws SystemException, BusinessException {
        record.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return sysPlatfromMapper.insertSelective(record);
    }

    public int updatePlatStatusById(Integer platId, String platStatus, Integer updator) {
        SysPlatfrom plat = new SysPlatfrom();
        plat.setUpdater(updator);
        plat.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        plat.setPlatStatus(platStatus);
        plat.setPlatId(platId);
        int count = sysPlatfromMapper.updateByPrimaryKeySelective(plat);
        return count;
    }

    @Override
    public void updatePlatStatusByIds(List<Integer> platIds, String platStatus, Integer updator) {

        for(Integer platId:platIds){
            this.updatePlatStatusById(platId, platStatus, updator);
        }

    }

    @Override
    public int updatePlat(SysPlatfrom record) throws SystemException, BusinessException {
        record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return  sysPlatfromMapper.updateByPrimaryKeySelective(record);
    }

    private SysPlatfromExample getCondition(SysPlatfrom record) {
        SysPlatfromExample where = new SysPlatfromExample();
        if (record != null) {
            SysPlatfromExample.Criteria cr = where.createCriteria();
            if (record.getPlatName() != null && StringUtils.isNotEmpty(record.getPlatName())) {
                cr.andPlatNameLike("%" + record.getPlatName() + "%");
            }
            if (record.getPlatId() != null && StringUtils.isNotEmpty(record.getPlatId().toString())) {
                cr.andPlatIdEqualTo(record.getPlatId());
            }


        }
        return where;
    }
}
