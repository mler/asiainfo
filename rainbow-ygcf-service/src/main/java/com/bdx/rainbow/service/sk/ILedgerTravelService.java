package com.bdx.rainbow.service.sk;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.LedgerTravel;

import java.util.Map;

/**
 * 旅游接待
 * Created by fusj on 16/2/29.
 */
public interface ILedgerTravelService {
    /**
     * 分页查询
     * @param ledgerTravel
     * @param pageInfo
     * @return
     */
    PageInfo list(LedgerTravel ledgerTravel, PageInfo pageInfo);

    /**
     * 台账明细获取票证
     * @param travelId
     * @return
     */
    Map<String,String> ticketSingle(Integer travelId) throws Exception;

    /**
     * 根据主键获取对象
     * @param dinnerId
     * @return
     */
    LedgerTravel get(Integer travelId);

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    Map<String,String> ticketList(Integer ledgerId) throws Exception;
}
