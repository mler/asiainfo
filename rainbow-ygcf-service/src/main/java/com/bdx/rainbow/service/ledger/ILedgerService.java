package com.bdx.rainbow.service.ledger;

import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.ygcf.entity.Ledger;

/**
 * 台账类型接口
 * Created by fusj on 16/2/1.
 */
public interface ILedgerService {
    /**
     * 列表数据
     * @param ledger
     * @param pageInfo
     * @return
     */
    PageInfo list(Ledger ledger, String startDate, String endDate, PageInfo pageInfo);

    /**
     * 根据主键获取台账对象
     * @param ledgerId
     * @return
     */
    Ledger get(int ledgerId);
}
