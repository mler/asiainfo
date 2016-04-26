package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.sk.ILedgerAdditifService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.LedgerAdditif;
import com.bdx.rainbow.ygcf.entity.LedgerAdditifExample;
import com.bdx.rainbow.ygcf.mapper.LedgerAdditifMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加剂使用台账
 * Created by fusj on 16/2/29.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LedgerAdditifServiceImpl implements ILedgerAdditifService {

    @Autowired
    private LedgerAdditifMapper ledgerAdditifMapper;

    @Autowired
    private FileServerConfigMapper fileServerConfigMapper;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private IFile fileManager;

    /**
     * 分页查询
     * @param ledgerAdditif
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(LedgerAdditif ledgerAdditif, PageInfo pageInfo) {
        LedgerAdditifExample example = getCondition(ledgerAdditif);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<LedgerAdditif> resultList = ledgerAdditifMapper.selectByExample(example);
        int resultCount = ledgerAdditifMapper.countByExample(example);

        pageInfo.setList(resultList);
        pageInfo.setTotalCount(resultCount);

        return pageInfo;
    }

    /**
     * 台账明细获取票证
     * @param additifId
     * @return
     */
    @Override
    public Map<String, String> ticketSingle(Integer additifId) throws Exception {
        LedgerAdditif ledgerAdditif = ledgerAdditifMapper.selectByPrimaryKey(additifId);

        Map<String, String> map = new HashMap<>();

        if(!StringUtil.isNotEmptyObject(ledgerAdditif.getFileId())) {
            return map;
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        try {
            String filePath = fileManager.getHttpUrl(ledgerAdditif.getFileId(), fileServerConfig);

            map.put(ledgerAdditif.getFileId(), filePath);
        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 根据主键获取对象
     * @param additifId
     * @return
     */
    @Override
    public LedgerAdditif get(Integer additifId) {
        LedgerAdditif ledgerAdditif = ledgerAdditifMapper.selectByPrimaryKey(additifId);

        return ledgerAdditif;
    }

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    @Override
    public Map<String, String> ticketList(Integer ledgerId) throws Exception {
        LedgerAdditif ledgerAdditif = new LedgerAdditif();
        ledgerAdditif.setLedgerId(ledgerId);

        LedgerAdditifExample example = getCondition(ledgerAdditif);
        List<LedgerAdditif> additifList = ledgerAdditifMapper.selectByExample(example);

        List<String> fileIdList = new ArrayList<>();
        for(LedgerAdditif ledgerAddtifData : additifList) {
            // 不为空再去VFS查询
            if(StringUtil.isNotEmptyObject(ledgerAddtifData.getFileId())) {
                fileIdList.add(ledgerAddtifData.getFileId());
            }
        }

        Map<String, String> map = new HashMap<>();
        try {
            if(!CollectionUtils.isEmpty(fileIdList)) {
                FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());
                map = fileManager.getHttpUrls(fileIdList, fileServerConfig);
            }

        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 查询条件组装
     * @param ledgerAdditif
     * @return
     */
    private LedgerAdditifExample getCondition(LedgerAdditif ledgerAdditif) {
        LedgerAdditifExample example = new LedgerAdditifExample();

        if(null != ledgerAdditif) {
            LedgerAdditifExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(ledgerAdditif.getAdditifId())) {
                criteria.andAdditifIdEqualTo(ledgerAdditif.getAdditifId());
            }

            if(StringUtil.isNotEmptyObject(ledgerAdditif.getLedgerId())) {
                criteria.andLedgerIdEqualTo(ledgerAdditif.getLedgerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerAdditif.getFileId())) {
                criteria.andFileIdEqualTo(ledgerAdditif.getFileId());
            }

            if(StringUtil.isNotEmptyObject(ledgerAdditif.getProductName())) {
                criteria.andProductNameLike('%' + ledgerAdditif.getProductName() + '%');
            }
        }

        return example;
    }
}
