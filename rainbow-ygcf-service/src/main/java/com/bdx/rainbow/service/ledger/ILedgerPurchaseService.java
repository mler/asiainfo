package com.bdx.rainbow.service.ledger;

import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.LedgerPurchase;

import java.util.List;
import java.util.Map;

/**
 * 采购台账接口
 * Created by fusj on 16/1/20.
 */
public interface ILedgerPurchaseService {

    /**
     * 根据主键获取对象
     * @param purchaseId
     * @return
     */
    LedgerPurchase get(Integer purchaseId) throws Exception;

    /**
     * 分页查询
     * @param ledgerPurchase
     * @param pageInfo
     * @return
     */
    PageInfo list(LedgerPurchase ledgerPurchase, PageInfo pageInfo);

    /**
     * 台账明细获取票证
     * @param purchaseId
     * @return
     */
    Map<String, String> ticketSingle(Integer purchaseId) throws Exception;

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    Map<String, String> ticketList(Integer ledgerId) throws Exception;
}
