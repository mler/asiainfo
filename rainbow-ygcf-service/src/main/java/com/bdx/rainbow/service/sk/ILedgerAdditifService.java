package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.LedgerAdditif;

import java.util.Map;

/**
 * Created by fusj on 16/2/29.
 */
public interface ILedgerAdditifService {

    /**
     * 分页查询
     * @param ledgerAdditif
     * @param pageInfo
     * @return
     */
    PageInfo list(LedgerAdditif ledgerAdditif, PageInfo pageInfo);

    /**
     * 台账明细获取票证
     * @param additifId
     * @return
     */
    Map<String,String> ticketSingle(Integer additifId) throws Exception;

    /**
     * 根据主键获取对象
     * @param additifId
     * @return
     */
    LedgerAdditif get(Integer additifId);

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    Map<String,String> ticketList(Integer ledgerId) throws Exception;
}
