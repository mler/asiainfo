package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.LedgerWaste;

import java.util.Map;

/**
 *
 * Created by fusj on 16/2/29.
 */
public interface ILedgerWasteService {
    /**
     * 分页查询
     * @param ledgerWaste
     * @param pageInfo
     * @return
     */
    PageInfo list(LedgerWaste ledgerWaste, PageInfo pageInfo);

    /**
     * 台账明细获取票证
     * @param wasteId
     * @return
     */
    Map<String,String> ticketSingle(Integer wasteId) throws Exception;

    /**
     * 根据主键获取对象
     * @param wasteId
     * @return
     */
    LedgerWaste get(Integer wasteId);

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    Map<String,String> ticketList(Integer ledgerId) throws Exception;
}
