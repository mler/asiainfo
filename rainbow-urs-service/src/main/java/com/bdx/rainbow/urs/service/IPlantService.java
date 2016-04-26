package com.bdx.rainbow.urs.service;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.SysPlatfrom;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/1/28.
 */
public interface IPlantService {
    List<SysPlatfrom> getPlatsByUserId(Map<String, Object> condition);
    List<SysPlatfrom> getPlats(SysPlatfrom plat, Integer start, Integer count);
    int countPlat(SysPlatfrom record) throws SystemException, BusinessException;
    int insertPlat(SysPlatfrom record) throws SystemException, BusinessException;
    void updatePlatStatusByIds(List<Integer> platIds,String platStatus,Integer updator)throws SystemException, BusinessException;
    int updatePlat(SysPlatfrom record) throws SystemException, BusinessException;

}
