package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.sk.ILedgerWasteService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.LedgerWaste;
import com.bdx.rainbow.ygcf.entity.LedgerWasteExample;
import com.bdx.rainbow.ygcf.mapper.LedgerWasteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 废弃物台账
 * Created by fusj on 16/2/29.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LedgerWasteServiceImpl implements ILedgerWasteService {

    @Autowired
    private LedgerWasteMapper ledgerWasteMapper;

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
    public PageInfo list(LedgerWaste ledgerWaste, PageInfo pageInfo) {
        LedgerWasteExample example = getCondition(ledgerWaste);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<LedgerWaste> resultList = ledgerWasteMapper.selectByExample(example);
        int resultCount = ledgerWasteMapper.countByExample(example);

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
    public Map<String, String> ticketSingle(Integer wasteId) throws Exception {
        LedgerWaste ledgerWaste = ledgerWasteMapper.selectByPrimaryKey(wasteId);

        Map<String, String> map = new HashMap<>();

        if(!StringUtil.isNotEmptyObject(ledgerWaste.getFileId())) {
            return map;
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        try {
            String filePath = fileManager.getHttpUrl(ledgerWaste.getFileId(), fileServerConfig);

            map.put(ledgerWaste.getFileId(), filePath);
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
    public LedgerWaste get(Integer wasteId) {
        LedgerWaste ledgerWaste = ledgerWasteMapper.selectByPrimaryKey(wasteId);

        return ledgerWaste;
    }

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    @Override
    public Map<String, String> ticketList(Integer ledgerId) throws Exception {
        LedgerWaste ledgerWaste = new LedgerWaste();
        ledgerWaste.setLedgerId(ledgerId);

        LedgerWasteExample example = getCondition(ledgerWaste);
        List<LedgerWaste> wasteList = ledgerWasteMapper.selectByExample(example);

        List<String> fileIdList = new ArrayList<>();
        for(LedgerWaste ledgerWasteData : wasteList) {
            fileIdList.add(ledgerWasteData.getFileId());
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
     * @param ledgerWaste
     * @return
     */
    private LedgerWasteExample getCondition(LedgerWaste ledgerWaste) {
        LedgerWasteExample example = new LedgerWasteExample();

        if(null != ledgerWaste) {
            LedgerWasteExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(ledgerWaste.getWasteId())) {
                criteria.andWasteIdEqualTo(ledgerWaste.getWasteId());
            }

            if(StringUtil.isNotEmptyObject(ledgerWaste.getLedgerId())) {
                criteria.andLedgerIdEqualTo(ledgerWaste.getLedgerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerWaste.getFileId())) {
                criteria.andFileIdEqualTo(ledgerWaste.getFileId());
            }
        }

        return example;
    }
}
