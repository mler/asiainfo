package com.bdx.rainbow.service.sk.impl;

import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.entity.PageInfo;
import com.bdx.rainbow.entity.vfs.FileServerConfig;
import com.bdx.rainbow.mapper.vfs.FileServerConfigMapper;
import com.bdx.rainbow.service.sk.ILedgerTravelService;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.LedgerTravel;
import com.bdx.rainbow.ygcf.entity.LedgerTravelExample;
import com.bdx.rainbow.ygcf.mapper.LedgerTravelMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 旅游接待
 * Created by fusj on 16/2/29.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LedgerTravelServiceImpl implements ILedgerTravelService {

    @Autowired
    private LedgerTravelMapper ledgerTravelMapper;

    @Autowired
    private FileServerConfigMapper fileServerConfigMapper;

    @Autowired
    private VfsSetting vfsSetting;

    @Autowired
    private IFile fileManager;

    /**
     * 分页查询
     * @param ledgerTravel
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo list(LedgerTravel ledgerTravel, PageInfo pageInfo) {
        LedgerTravelExample example = getCondition(ledgerTravel);

        if(null != pageInfo && null != pageInfo.getPageStart()) {
            example.setLimitClauseStart(pageInfo.getPageStart());
            example.setLimitClauseCount(pageInfo.getPageCount());
        }

        List<LedgerTravel> resultList = ledgerTravelMapper.selectByExample(example);
        int resultCount = ledgerTravelMapper.countByExample(example);

        pageInfo.setList(resultList);
        pageInfo.setTotalCount(resultCount);

        return pageInfo;
    }

    /**
     * 台账明细获取票证
     * @param travelId
     * @return
     */
    @Override
    public Map<String, String> ticketSingle(Integer travelId) throws Exception {
        LedgerTravel ledgerTravel = ledgerTravelMapper.selectByPrimaryKey(travelId);

        Map<String, String> map = new HashMap<>();

        if(!StringUtil.isNotEmptyObject(ledgerTravel.getFileId())) {
            return map;
        }

        FileServerConfig fileServerConfig = fileServerConfigMapper.selectByPrimaryKey(vfsSetting.getServerName());

        try {
            String filePath = fileManager.getHttpUrl(ledgerTravel.getFileId(), fileServerConfig);

            map.put(ledgerTravel.getFileId(), filePath);
        } catch (Exception ex) {
            throw new SystemException(new DefaultExceptionCode("500", ex.getMessage()));
        }

        return map;
    }

    /**
     * 根据主键获取对象
     * @param travleId
     * @return
     */
    @Override
    public LedgerTravel get(Integer travleId) {
        LedgerTravel ledgerTravel = ledgerTravelMapper.selectByPrimaryKey(travleId);

        return ledgerTravel;
    }

    /**
     * 台账日期记录获取票证
     * @param ledgerId
     * @return
     */
    @Override
    public Map<String, String> ticketList(Integer ledgerId) throws Exception {
        LedgerTravel ledgerTravel = new LedgerTravel();
        ledgerTravel.setLedgerId(ledgerId);

        LedgerTravelExample example = getCondition(ledgerTravel);
        List<LedgerTravel> travelList = ledgerTravelMapper.selectByExample(example);

        List<String> fileIdList = new ArrayList<>();
        for(LedgerTravel ledgerTravelData : travelList) {
            fileIdList.add(ledgerTravelData.getFileId());
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
     * @param ledgerTravel
     * @return
     */
    private LedgerTravelExample getCondition(LedgerTravel ledgerTravel) {
        LedgerTravelExample example = new LedgerTravelExample();

        if(null != ledgerTravel) {
            LedgerTravelExample.Criteria criteria = example.createCriteria();

            if(StringUtil.isNotEmptyObject(ledgerTravel.getTravelId())) {
                criteria.andTravelIdEqualTo(ledgerTravel.getTravelId());
            }

            if(StringUtil.isNotEmptyObject(ledgerTravel.getLedgerId())) {
                criteria.andLedgerIdEqualTo(ledgerTravel.getLedgerId());
            }

            if(StringUtil.isNotEmptyObject(ledgerTravel.getFileId())) {
                criteria.andFileIdEqualTo(ledgerTravel.getFileId());
            }

            if(StringUtil.isNotEmptyObject(ledgerTravel.getTravelName())) {
                criteria.andTravelNameLike("%" + ledgerTravel.getTravelName() + "%");
            }

            if(StringUtils.isNotBlank(ledgerTravel.getSourceFrom())) {
                criteria.andSourceFromLike("%" +  ledgerTravel.getSourceFrom() + "%");
            }
        }

        return example;
    }
}
