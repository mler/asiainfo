package com.bdx.rainbow.ygcf.mapper.custom;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 台账查询自定义SQL查询
 */
public interface LedgerCustomMapper {

    /**
     * 查询台账日期前几天的台账日期
     * @param param
     * @return  返回map中包含ledgerDate
     */
    List<Map> selectLedgerGroupByLedgerDate(@Param("param") Map param);
}