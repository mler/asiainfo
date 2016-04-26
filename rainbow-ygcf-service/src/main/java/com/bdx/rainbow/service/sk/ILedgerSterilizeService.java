package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.LedgerSterilize;

import java.util.Map;

/**
 * 消毒记录台账
 * Created by fusj on 16/2/29.
 */
public interface ILedgerSterilizeService {

    /**
     * 分页查询
     * @param ledgerSterilize
     * @param pageInfo
     * @return
     */
    PageInfo list(LedgerSterilize ledgerSterilize, PageInfo pageInfo);

    /**
     * 台账明细获取票证
     * @param sterilizeId
     * @return
     */
    Map<String,String> ticketSingle(Integer sterilizeId) throws Exception;

    /**
     * 根据主键获取对象
     * @param sterilizeId
     * @return
     */
    LedgerSterilize get(Integer sterilizeId);

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    Map<String,String> ticketList(Integer ledgerId) throws Exception;
}
