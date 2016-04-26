package com.bdx.rainbow.urs.service;


import com.bdx.rainbow.entity.urs.SysParamDetail;

import java.util.List;

/**
 * Created by core on 16/2/17.
 */
public interface ISysParamService {
    Integer insertParam(SysParamDetail record);
    List<SysParamDetail> getParams(SysParamDetail record, Integer start, Integer count);
    //获取SysRole记录数
    int countParam(SysParamDetail record) ;

    int updateParam(SysParamDetail record,String str1,String str2);
    SysParamDetail getParam(String pKey,String pValue);
    void deleteParam(SysParamDetail record);
}
