package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.LedgerDinner;

import java.util.Map;

/**
 * 集体聚餐台账
 * Created by fusj on 16/2/29.
 */
public interface ILedgerDinnerService {
    /**
     * 分页查询
     * @param ledgerDinner
     * @param pageInfo
     * @return
     */
    PageInfo list(LedgerDinner ledgerDinner, PageInfo pageInfo);

    /**
     * 台账明细获取票证
     * @param dinnerId
     * @return
     */
    Map<String,String> ticketSingle(Integer dinnerId) throws Exception;

    /**
     * 根据主键获取对象
     * @param dinnerId
     * @return
     */
    LedgerDinner get(Integer dinnerId);

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    Map<String,String> ticketList(Integer ledgerId) throws Exception;
}
