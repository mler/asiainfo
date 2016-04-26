package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.service.mapp.ISK0005Service;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.ygcf.entity.*;
import com.bdx.rainbow.ygcf.mapper.*;
import com.bdx.rainbow.ygcf.mapper.custom.LedgerCustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 0005接口,台账明细
 * Created by fusj on 16/3/22.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0005ServiceImpl implements ISK0005Service {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerCustomMapper ledgerCustomMapper;

    @Autowired
    private LedgerPurchaseMapper ledgerPurchaseMapper;

    @Autowired
    private LedgerAdditifMapper ledgerAdditifMapper;

    @Autowired
    private LedgerWasteMapper ledgerWasteMapper;

    @Autowired
    private LedgerSterilizeMapper ledgerSterilizeMapper;

    @Autowired
    private LedgerTravelMapper ledgerTravelMapper;

    @Autowired
    private LedgerDinnerMapper ledgerDinnerMapper;

    @Autowired
    private IFile fileService;

    @Autowired
    private VfsSetting vfsSetting;

    /**
     * 查询台账明细
     * @param ledgerType
     *          0, 全部
                1, 采购台帐
                2, 添加剂使用台帐
                3, 废弃物回收台帐
                4, 消毒记录台帐
                5, 接待旅游台帐
                6, 集体用餐台帐
     * @param begTime     yyyy-MM-dd
     * @param endTime     yyyy-MM-dd
     * @param pageNumber
     * @param pageCount
     * @return
     */
    @Override
    public List legalList(String ledgerType, String begTime, String endTime, String pageNumber, String pageCount, IUserInfo sysUserInfo) throws Exception {
        List<Map<Ledger, List>> result = new ArrayList<>();

        List<Ledger> ledgerList = new ArrayList<>();

        // 台账类型不为空
        if(StringUtil.isNotEmptyObject(ledgerType) && !"0".equals(ledgerType)) {
            // 查询台账记录
            LedgerExample example = new LedgerExample();
            LedgerExample.Criteria criteria = example.createCriteria();

            criteria.andIsDelEqualTo("0");

            criteria.andLedgerTypeEqualTo(convertLedgerType(ledgerType));
            // 企业编号
            criteria.andEnterpriseIdEqualTo(sysUserInfo.getUser().getCorpId());

            // 开始时间
            if(StringUtil.isNotEmptyObject(begTime)) {
                // 转换格式
                String begDate = DateUtil.parse(begTime, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4);
                criteria.andLedgerDateGreaterThanOrEqualTo(begDate);
            }

            // 结束时间
            if(StringUtil.isNotEmptyObject(endTime)) {
                String endDate = DateUtil.parse(endTime, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4);
                criteria.andLedgerDateLessThanOrEqualTo(endDate);
            }

            example.setLimitClauseCount(Integer.parseInt(pageCount));
            example.setLimitClauseStart(Integer.parseInt(pageNumber) * Integer.parseInt(pageCount));

            example.setOrderByClause(" ledger_date desc ");

            ledgerList = ledgerMapper.selectByExample(example);
        }
        // 台账类型为空，调用自己写的SQL
        else {
            Map param = new HashMap();

            // 开始时间
            if(StringUtil.isNotEmptyObject(begTime)) {
                // 转换格式
                String begDate = DateUtil.parse(begTime, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4);
                param.put("begDate", begDate);
            }

            // 结束时间
            if(StringUtil.isNotEmptyObject(endTime)) {
                String endDate = DateUtil.parse(endTime, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4);
                param.put("endDate", endDate);
            }

            param.put("count", Integer.parseInt(pageCount));
            param.put("start", Integer.parseInt(pageNumber) * Integer.parseInt(pageCount));
            param.put("enterpriseId", sysUserInfo.getUser().getCorpId());

            List<Map> list = ledgerCustomMapper.selectLedgerGroupByLedgerDate(param);

            for(Map map : list) {
                String ledgerDate = map.get("ledgerDate").toString();

                LedgerExample example = new LedgerExample();
                example.createCriteria().andLedgerDateEqualTo(ledgerDate)
                        .andEnterpriseIdEqualTo(sysUserInfo.getUser().getCorpId());

                example.setOrderByClause(" ledger_type asc ");

                List<Ledger> subList = ledgerMapper.selectByExample(example);

                ledgerList.addAll(subList);
            }
        }

        // 查询台账明细
        for(Ledger ledger : ledgerList) {
            Map<Ledger, List> ledgerItem = new HashMap<>();

            switch (ledger.getLedgerType()) {
                case LedgerTypeConstants.LEDGER_TYPE_PURCHASE :
                    ledgerItem.put(ledger, getLedgerPurchaseByLedgerId(ledger.getLedgerId()));
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_ADDITIF :
                    ledgerItem.put(ledger, getLedgerAdditifByLedgerId(ledger.getLedgerId()));
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_WASTE :
                    ledgerItem.put(ledger, getLedgerWasteByLedgerId(ledger.getLedgerId()));
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_STERILIZE :
                    ledgerItem.put(ledger, getLedgerSterilizeByLedgerId(ledger.getLedgerId()));
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_TRAVEL :
                    ledgerItem.put(ledger, getLedgerTravelByLedgerId(ledger.getLedgerId()));
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_DINNER :
                    ledgerItem.put(ledger, getLedgerDinnerByLedgerId(ledger.getLedgerId()));
                    break;
            }

            result.add(ledgerItem);
        }

        return result;
    }

    /**
     * 采购台账明细获取
     * @param ledgerId
     * @return
     */
    private List getLedgerPurchaseByLedgerId(Integer ledgerId) throws Exception {
        LedgerPurchaseExample example = new LedgerPurchaseExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andLedgerIdEqualTo(ledgerId);

        List<LedgerPurchase> list = ledgerPurchaseMapper.selectByExample(example);

        // 批量获取VFS文件路径
        List<String> fileIdList = new ArrayList<>();
        for(LedgerPurchase ledgerPurchase : list) {
            if(StringUtil.isNotEmptyObject(ledgerPurchase.getFileId())) {
                fileIdList.add(ledgerPurchase.getFileId());
            }
        }

        // key＝fileId, value=URL
        Map<String, String> map = fileService.getHttpUrls(fileIdList, fileService.getServerConfig(vfsSetting.getServerName()));

        // 回填filePath到fileId字段
        for(LedgerPurchase ledgerPurchase : list) {
            if(StringUtil.isNotEmptyObject(ledgerPurchase.getFileId())) {
                ledgerPurchase.setFileId(map.get(ledgerPurchase.getFileId()));
            }
        }

        return list;
    }

    /**
     * 添加剂台账明细获取
     * @param ledgerId
     * @return
     */
    private List getLedgerAdditifByLedgerId(Integer ledgerId) throws Exception {
        LedgerAdditifExample example = new LedgerAdditifExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andLedgerIdEqualTo(ledgerId);

        List<LedgerAdditif> list = ledgerAdditifMapper.selectByExample(example);

        // 批量获取VFS文件路径
        List<String> fileIdList = new ArrayList<>();
        for(LedgerAdditif ledgerAdditif : list) {
            if(StringUtil.isNotEmptyObject(ledgerAdditif.getFileId())) {
                fileIdList.add(ledgerAdditif.getFileId());
            }
        }

        // key＝fileId, value=URL
        Map<String, String> map = fileService.getHttpUrls(fileIdList, fileService.getServerConfig(vfsSetting.getServerName()));

        // 回填filePath到fileId字段
        for(LedgerAdditif ledgerAdditif : list) {
            if(StringUtil.isNotEmptyObject(ledgerAdditif.getFileId())) {
                ledgerAdditif.setFileId(map.get(ledgerAdditif.getFileId()));
            }
        }

        return list;
    }

    /**
     * 废弃物台账明细获取
     * @param ledgerId
     * @return
     */
    private List getLedgerWasteByLedgerId(Integer ledgerId) throws Exception {
        LedgerWasteExample example = new LedgerWasteExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andLedgerIdEqualTo(ledgerId);

        List<LedgerWaste> list = ledgerWasteMapper.selectByExample(example);

        // 批量获取VFS文件路径
        List<String> fileIdList = new ArrayList<>();
        for(LedgerWaste ledgerWaste : list) {
            if(StringUtil.isNotEmptyObject(ledgerWaste.getFileId())) {
                fileIdList.add(ledgerWaste.getFileId());
            }
        }

        // key＝fileId, value=URL
        Map<String, String> map = fileService.getHttpUrls(fileIdList, fileService.getServerConfig(vfsSetting.getServerName()));

        // 回填filePath到fileId字段
        for(LedgerWaste ledgerWaste : list) {
            if(StringUtil.isNotEmptyObject(ledgerWaste.getFileId())) {
                ledgerWaste.setFileId(map.get(ledgerWaste.getFileId()));
            }
        }

        return list;
    }

    /**
     * 消毒记录台账明细获取
     * @param ledgerId
     * @return
     */
    private List getLedgerSterilizeByLedgerId(Integer ledgerId) throws Exception {
        LedgerSterilizeExample example = new LedgerSterilizeExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andLedgerIdEqualTo(ledgerId);

        List<LedgerSterilize> list = ledgerSterilizeMapper.selectByExample(example);

        // 批量获取VFS文件路径
        List<String> fileIdList = new ArrayList<>();
        for(LedgerSterilize ledgerSterilize : list) {
            if(StringUtil.isNotEmptyObject(ledgerSterilize.getFileId())) {
                fileIdList.add(ledgerSterilize.getFileId());
            }
        }

        // key＝fileId, value=URL
        Map<String, String> map = fileService.getHttpUrls(fileIdList, fileService.getServerConfig(vfsSetting.getServerName()));

        // 回填filePath到fileId字段
        for(LedgerSterilize ledgerSterilize : list) {
            if(StringUtil.isNotEmptyObject(ledgerSterilize.getFileId())) {
                ledgerSterilize.setFileId(map.get(ledgerSterilize.getFileId()));
            }
        }

        return list;
    }

    /**
     * 旅游接待台账明细获取
     * @param ledgerId
     * @return
     */
    private List getLedgerTravelByLedgerId(Integer ledgerId) throws Exception{
        LedgerTravelExample example = new LedgerTravelExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andLedgerIdEqualTo(ledgerId);

        List<LedgerTravel> list = ledgerTravelMapper.selectByExample(example);

        // 批量获取VFS文件路径
        List<String> fileIdList = new ArrayList<>();
        for(LedgerTravel ledgerTravel : list) {
            if(StringUtil.isNotEmptyObject(ledgerTravel.getFileId())) {
                fileIdList.add(ledgerTravel.getFileId());
            }
        }

        // key＝fileId, value=URL
        Map<String, String> map = fileService.getHttpUrls(fileIdList, fileService.getServerConfig(vfsSetting.getServerName()));

        // 回填filePath到fileId字段
        for(LedgerTravel ledgerTravel : list) {
            if(StringUtil.isNotEmptyObject(ledgerTravel.getFileId())) {
                ledgerTravel.setFileId(map.get(ledgerTravel.getFileId()));
            }
        }

        return list;
    }

    /**
     * 集体聚餐台账明细获取
     * @param ledgerId
     * @return
     */
    private List getLedgerDinnerByLedgerId(Integer ledgerId) throws Exception{
        LedgerDinnerExample example = new LedgerDinnerExample();
        example.createCriteria().andIsDelEqualTo("0")
                .andLedgerIdEqualTo(ledgerId);

        List<LedgerDinner> list = ledgerDinnerMapper.selectByExample(example);

        // 批量获取VFS文件路径
        List<String> fileIdList = new ArrayList<>();
        for(LedgerDinner ledgerDinner : list) {
            if(StringUtil.isNotEmptyObject(ledgerDinner.getFileId())) {
                fileIdList.add(ledgerDinner.getFileId());
            }
        }

        // key＝fileId, value=URL
        Map<String, String> map = fileService.getHttpUrls(fileIdList, fileService.getServerConfig(vfsSetting.getServerName()));

        // 回填filePath到fileId字段
        for(LedgerDinner ledgerDinner : list) {
            if(StringUtil.isNotEmptyObject(ledgerDinner.getFileId())) {
                ledgerDinner.setFileId(map.get(ledgerDinner.getFileId()));
            }
        }

        return list;
    }

    /**
     * 手机前段台账类型和数据库存储台账类型进行转换
     * @param ledgerType
     * @return
     */
    private String convertLedgerType(String ledgerType) {
        switch (ledgerType) {
            case "1" :
                return LedgerTypeConstants.LEDGER_TYPE_PURCHASE;
            case "2" :
                return LedgerTypeConstants.LEDGER_TYPE_ADDITIF;
            case "3" :
                return LedgerTypeConstants.LEDGER_TYPE_WASTE;
            case "4" :
                return LedgerTypeConstants.LEDGER_TYPE_STERILIZE;
            case "5" :
                return LedgerTypeConstants.LEDGER_TYPE_TRAVEL;
            case "6" :
                return LedgerTypeConstants.LEDGER_TYPE_DINNER;
            default:
                return ledgerType;
        }
    }
}
