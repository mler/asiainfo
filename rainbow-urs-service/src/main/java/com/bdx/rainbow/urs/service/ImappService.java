package com.bdx.rainbow.urs.service;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.urs.SysMappApnsToken;

import java.util.List;

/**
 * Created by core on 16/1/28.
 */
public interface ImappService {
   SysMappApnsToken  getMappsBytoken(String token);
    List<SysMappApnsToken> getMapps(SysMappApnsToken mapp, Integer start, Integer count);
    int countMapp(SysMappApnsToken record) throws SystemException, BusinessException;
    int insertMapp(SysMappApnsToken record) throws SystemException, BusinessException;
    void updateMappValidBytokens(List<String> tokens, String valid)throws SystemException, BusinessException;
    int updateMapp(SysMappApnsToken record) throws SystemException, BusinessException;

}
