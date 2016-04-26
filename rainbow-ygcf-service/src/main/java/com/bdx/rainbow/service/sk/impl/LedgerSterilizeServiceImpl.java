package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.sk.ILedgerSterilizeService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.LedgerSterilize;
import com.bdx.rainbow.ygcf.entity.LedgerSterilizeExample;
import com.bdx.rainbow.ygcf.mapper.LedgerSterilizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消毒记录台账
 * Created by fusj on 16/2/29.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LedgerSterilizeServiceImpl implements ILedgerSterilizeService {

    @Autowired
    private LedgerSterilizeMapper ledgerSterilizeMapper;

    @Autowired
    private FileServerConfigMapper fileServerConfigMapper;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private IFile fileManager;

    /**
     * 分页查询
     * @param ledgerWaste
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(LedgerSterilize ledgerSterilize, PageInfo pageInfo) {
        LedgerSterilizeExample example = getCondition(ledgerSterilize);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<LedgerSterilize> resultList = ledgerSterilizeMapper.selectByExample(example);
        int resultCount = ledgerSterilizeMapper.countByExample(example);

        pageInfo.setList(resultList);
        pageInfo.setTotalCount(resultCount);

        return pageInfo;
    }

    /**
     * 台账明细获取票证
     * @param wasteId
     * @return
     */
    @Override
    public Map<String, String> ticketSingle(Integer sterilizeId) throws Exception {
        LedgerSterilize ledgerSterilize = ledgerSterilizeMapper.selectByPrimaryKey(sterilizeId);

        Map<String, String> map = new HashMap<>();

        if(!StringUtil.isNotEmptyObject(ledgerSterilize.getFileId())) {
            return map;
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        try {
            String filePath = fileManager.getHttpUrl(ledgerSterilize.getFileId(), fileServerConfig);

            map.put(ledgerSterilize.getFileId(), filePath);
        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 根据主键获取对象
     * @param wasteId
     * @return
     */
    @Override
    public LedgerSterilize get(Integer sterilizeId) {
        LedgerSterilize ledgerSterilize = ledgerSterilizeMapper.selectByPrimaryKey(sterilizeId);

        return ledgerSterilize;
    }

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    @Override
    public Map<String, String> ticketList(Integer ledgerId) throws Exception {
        LedgerSterilize ledgerSterilize = new LedgerSterilize();
        ledgerSterilize.setLedgerId(ledgerId);

        LedgerSterilizeExample example = getCondition(ledgerSterilize);
        List<LedgerSterilize> sterilizeList = ledgerSterilizeMapper.selectByExample(example);

        List<String> fileIdList = new ArrayList<>();
        for(LedgerSterilize ledgerSterilizeData : sterilizeList) {
            fileIdList.add(ledgerSterilizeData.getFileId());
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
     * 查询条件组装
     * @param ledgerSterilize
     * @return
     */
    private LedgerSterilizeExample getCondition(LedgerSterilize ledgerSterilize) {
        LedgerSterilizeExample example = new LedgerSterilizeExample();

        if(null != ledgerSterilize) {
            LedgerSterilizeExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(ledgerSterilize.getSterilizeId())) {
                criteria.andSterilizeIdEqualTo(ledgerSterilize.getSterilizeId());
            }

            if(StringUtil.isNotEmptyObject(ledgerSterilize.getLedgerId())) {
                criteria.andLedgerIdEqualTo(ledgerSterilize.getLedgerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerSterilize.getFileId())) {
                criteria.andFileIdEqualTo(ledgerSterilize.getFileId());
            }
        }

        return example;
    }
}
