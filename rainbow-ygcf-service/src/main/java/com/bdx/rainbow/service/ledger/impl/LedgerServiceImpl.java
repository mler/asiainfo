package com.bdx.rainbow.service.ledger.impl;

import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.service.ledger.ILedgerService;
import com.bdx.rainbow.ygcf.entity.Ledger;
import com.bdx.rainbow.ygcf.entity.LedgerExample;
import com.bdx.rainbow.ygcf.mapper.LedgerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 台账类型接口实现
 * Created by fusj on 16/2/1.
 */
@Service
@Transactional
public class LedgerServiceImpl implements ILedgerService {

    @Autowired
    private LedgerMapper ledgerMapper;

    /**
     * 列表数据
     * @param ledger
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(Ledger ledger, String startDate, String endDate, PageInfo pageInfo) {

        LedgerExample example = getCondition(ledger, startDate, endDate);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List resultList = ledgerMapper.selectByExample(example);
        int resultCount = ledgerMapper.countByExample(example);

        pageInfo.setList(resultList);
        pageInfo.setTotalCount(resultCount);

        return pageInfo;
    }

    /**
     * 根据主键获取台账对象
     * @param ledgerId
     * @return
     */
    @Override
    public Ledger get(int ledgerId) {
        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerId);

        return ledger;
    }

    /**
     * 组装查询条件
     * @param ledger
     * @return
     */
    private LedgerExample getCondition(Ledger ledger, String startDate, String endDate) {
        LedgerExample example = new LedgerExample();

        if(null != ledger) {
            LedgerExample.Criteria criteria = example.createCriteria();

            // 台账类型
            if(StringUtil.isNotEmptyObject(ledger.getLedgerType())) {
                criteria.andLedgerTypeEqualTo(ledger.getLedgerType());
            }

            // 开始时间和结束时间都不为空
            if(StringUtil.isNotEmptyObject(startDate) && StringUtil.isNotEmptyObject(endDate)) {
                criteria.andLedgerDateBetween(startDate, endDate);
            }
            // 开始时间不为空
            else if(StringUtil.isNotEmptyObject(startDate)) {
                criteria.andLedgerDateGreaterThanOrEqualTo(startDate);
            }
            // 结束时间不为空
            else if(StringUtil.isNotEmptyObject(endDate)) {
                criteria.andLedgerDateLessThanOrEqualTo(endDate);
            }

            if(StringUtil.isNotEmptyObject(ledger.getEnterpriseId())) {
                criteria.andEnterpriseIdEqualTo(ledger.getEnterpriseId());
            }
        }

        return example;
    }
}
