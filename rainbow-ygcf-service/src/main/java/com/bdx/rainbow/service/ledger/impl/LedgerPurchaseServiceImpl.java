package com.bdx.rainbow.service.ledger.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.ledger.ILedgerPurchaseService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.LedgerPurchase;
import com.bdx.rainbow.ygcf.entity.LedgerPurchaseExample;
import com.bdx.rainbow.ygcf.mapper.LedgerPurchaseMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购台账接口实现
 * Created by fusj on 16/1/22.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LedgerPurchaseServiceImpl implements ILedgerPurchaseService {

    @Autowired
    private LedgerPurchaseMapper ledgerPurchaseMapper;

    @Autowired
    private IFile fileManager;

    @Autowired
    private FileServerConfigMapper fileServerConfigMapper;

    @Autowired
    private VfsSetting vfsSetting;

    /**
     * 根据主键获取对象
     * @param purchaseId
     * @return
     */
    @Override
    public LedgerPurchase get(Integer purchaseId) throws Exception{
        LedgerPurchase ledgerPurchase = ledgerPurchaseMapper.selectByPrimaryKey(purchaseId);

        return ledgerPurchase;
    }

    /**
     * 分页查询
     * @param ledgerPurchase
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(LedgerPurchase ledgerPurchase, PageInfo pageInfo) {
        LedgerPurchaseExample example = getCondition(ledgerPurchase);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<LedgerPurchase> resultList = ledgerPurchaseMapper.selectByExample(example);
        int resultCount = ledgerPurchaseMapper.countByExample(example);

        pageInfo.setList(resultList);
        pageInfo.setTotalCount(resultCount);

        return pageInfo;
    }

    /**
     * 台账明细获取票证
     * @param purchaseId
     * @return
     */
    @Override
    public Map<String, String> ticketSingle(Integer purchaseId) throws Exception {
        LedgerPurchase ledgerPurchase = ledgerPurchaseMapper.selectByPrimaryKey(purchaseId);

        Map<String, String> map = new HashMap<>();

        if(StringUtils.isEmpty(ledgerPurchase.getFileId())) {
            return map;
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        try {
            String filePath = fileManager.getHttpUrl(ledgerPurchase.getFileId(), fileServerConfig);

            map.put(ledgerPurchase.getFileId(), filePath);
        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    @Override
    public Map<String, String> ticketList(Integer ledgerId) throws Exception {
        LedgerPurchase ledgerPurchase = new LedgerPurchase();
        ledgerPurchase.setLedgerId(ledgerId);

        LedgerPurchaseExample example = getCondition(ledgerPurchase);
        List<LedgerPurchase> purchaseList = ledgerPurchaseMapper.selectByExample(example);

        List<String> fileIdList = new ArrayList<>();
        for(LedgerPurchase ledgerPurchaseData : purchaseList) {
            fileIdList.add(ledgerPurchaseData.getFileId());
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        Map<String, String> map;
        try {
            map = fileManager.getHttpUrls(fileIdList, fileServerConfig);

        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 采购台账查询条件组装
     * @param ledgerPurchase
     * @return
     */
    private LedgerPurchaseExample getCondition(LedgerPurchase ledgerPurchase) {
        LedgerPurchaseExample example = new LedgerPurchaseExample();

        if(null != ledgerPurchase) {
            LedgerPurchaseExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(ledgerPurchase.getLedgerId())) {
                criteria.andLedgerIdEqualTo(ledgerPurchase.getLedgerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerPurchase.getFileId())) {
                criteria.andFileIdEqualTo(ledgerPurchase.getFileId());
            }

            if(StringUtil.isNotEmptyObject(ledgerPurchase.getProductName())) {
                criteria.andProductNameLike("%" + ledgerPurchase.getProductName() + "%");
            }

            if(StringUtil.isNotEmptyObject(ledgerPurchase.getProvider())) {
                criteria.andProviderLike("%" + ledgerPurchase.getProvider() + "%");
            }

            if(StringUtil.isNotEmptyObject(ledgerPurchase.getProductor())) {
                criteria.andProductorLike("%" + ledgerPurchase.getProductor() + "%");
            }

        }

        return example;
    }
}
