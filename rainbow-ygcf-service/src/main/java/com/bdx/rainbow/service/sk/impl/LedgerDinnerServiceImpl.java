package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.sk.ILedgerDinnerService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.LedgerDinner;
import com.bdx.rainbow.ygcf.entity.LedgerDinnerExample;
import com.bdx.rainbow.ygcf.mapper.LedgerDinnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集体聚餐
 * Created by fusj on 16/2/29.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LedgerDinnerServiceImpl implements ILedgerDinnerService {

    @Autowired
    private LedgerDinnerMapper ledgerDinnerMapper;

    @Autowired
    private FileServerConfigMapper fileServerConfigMapper;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private IFile fileManager;

    /**
     * 分页查询
     * @param ledgerDinner
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(LedgerDinner ledgerDinner, PageInfo pageInfo) {
        LedgerDinnerExample example = getCondition(ledgerDinner);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<LedgerDinner> resultList = ledgerDinnerMapper.selectByExample(example);
        int resultCount = ledgerDinnerMapper.countByExample(example);

        pageInfo.setList(resultList);
        pageInfo.setTotalCount(resultCount);

        return pageInfo;
    }

    /**
     * 台账明细获取票证
     * @param dinnerId
     * @return
     */
    @Override
    public Map<String, String> ticketSingle(Integer dinnerId) throws Exception {
        LedgerDinner ledgerDinner = ledgerDinnerMapper.selectByPrimaryKey(dinnerId);

        Map<String, String> map = new HashMap<>();

        if(!StringUtil.isNotEmptyObject(ledgerDinner.getFileId())) {
            return map;
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        try {
            String filePath = fileManager.getHttpUrl(ledgerDinner.getFileId(), fileServerConfig);

            map.put(ledgerDinner.getFileId(), filePath);
        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 根据主键获取对象
     * @param dinnerId
     * @return
     */
    @Override
    public LedgerDinner get(Integer dinnerId) {
        LedgerDinner ledgerDinner = ledgerDinnerMapper.selectByPrimaryKey(dinnerId);

        return ledgerDinner;
    }

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    @Override
    public Map<String, String> ticketList(Integer ledgerId) throws Exception {
        LedgerDinner ledgerDinner = new LedgerDinner();
        ledgerDinner.setLedgerId(ledgerId);

        LedgerDinnerExample example = getCondition(ledgerDinner);
        List<LedgerDinner> dinnerList = ledgerDinnerMapper.selectByExample(example);

        List<String> fileIdList = new ArrayList<>();
        for(LedgerDinner ledgerDinnerData : dinnerList) {
            fileIdList.add(ledgerDinnerData.getFileId());
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
     * @param ledgerDinner
     * @return
     */
    private LedgerDinnerExample getCondition(LedgerDinner ledgerDinner) {
        LedgerDinnerExample example = new LedgerDinnerExample();

        if(null != ledgerDinner) {
            LedgerDinnerExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(ledgerDinner.getDinnerId())) {
                criteria.andDinnerIdEqualTo(ledgerDinner.getDinnerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerDinner.getLedgerId())) {
                criteria.andLedgerIdEqualTo(ledgerDinner.getLedgerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerDinner.getFileId())) {
                criteria.andFileIdEqualTo(ledgerDinner.getFileId());
            }
        }

        return example;
    }
}
