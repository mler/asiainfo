package com.bdx.rainbow.spsy.service;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseLicense;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * Created by core on 16/3/10.
 */
public interface IEnterpriseLicenseService {
    public Map<String, Object> getLicenses(DubboEnterpriseLicense condition,Integer start,Integer limit)throws Exception;
    public HSSFWorkbook ExcelOut(String title,String[] headers,List<DubboEnterpriseLicense> resultLists, String pattern);
}
