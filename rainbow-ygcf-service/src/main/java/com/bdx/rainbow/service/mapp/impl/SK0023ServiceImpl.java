package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.service.mapp.ISK0023Service;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.ygcf.entity.*;
import com.bdx.rainbow.ygcf.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 0023台账明细详情
 * Created by fusj on 16/3/22.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0023ServiceImpl implements ISK0023Service {

    @Autowired
    private LedgerMapper ledgerMapper;

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
     * 台账明细详情,map中包含key＝ticket票证路径
     * @param ledgerType
     * @param id
     * @return
     */
    @Override
    public List<Map<String, String>> ledgerDetail(String ledgerType, String id) throws Exception{

        List<Map<String, String>> result = new ArrayList<>();

        switch (convertLedgerType(ledgerType)) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                result = purchaseDetail(id);
                break;
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                result = additifDetail(id);
                break;
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                result = wasteDetail(id);
                break;
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                result = sterilizeDetail(id);
                break;
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                result = travelDetail(id);
                break;
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                result = dinnerDetail(id);
                break;
        }

        return result;
    }

    /**
     * 采购台账明细
     * @param id
     * @return
     */
    private List<Map<String, String>> purchaseDetail(String id) throws Exception {
        LedgerPurchase ledgerPurchase = ledgerPurchaseMapper.selectByPrimaryKey(Integer.parseInt(id));

        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerPurchase.getLedgerId());

        Map<String, String> map1 = new HashMap<>();
        map1.put("产品名称", ledgerPurchase.getProductName());

        Map<String, String> map2 = new HashMap<>();
        map2.put("数量", ledgerPurchase.getQuantity() + ledgerPurchase.getStandard());

        Map<String, String> map3 = new HashMap<>();
        map3.put("保质期", ledgerPurchase.getValidity() + "天");

        Map<String, String> map4 = new HashMap<>();
        map4.put("供应商", ledgerPurchase.getProvider());

        Map<String, String> map5 = new HashMap<>();
        map5.put("生产厂家", ledgerPurchase.getProductor());

        Map<String, String> map6 = new HashMap<>();
        map6.put("生产批号", ledgerPurchase.getProductNo());

        Map<String, String> map7 = new HashMap<>();
        map7.put("采购时间", DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));

        Map<String, String> map8 = new HashMap<>();
        if(StringUtil.isNotEmptyObject(ledgerPurchase.getFileId())) {
            String fileUrl = fileService.getHttpUrl(ledgerPurchase.getFileId(), fileService.getServerConfig(vfsSetting.getServerName()));
            map8.put("ticket", fileUrl);
        }

        List<Map<String, String>> result = new ArrayList<>();
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);
        result.add(map7);
        result.add(map8);

        return result;
    }

    /**
     * 添加剂台账明细
     * @param id
     * @return
     */
    private List<Map<String, String>> additifDetail(String id) throws Exception{
        LedgerAdditif ledgerAdditif = ledgerAdditifMapper.selectByPrimaryKey(Integer.parseInt(id));

        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerAdditif.getLedgerId());

        Map<String, String> map1 = new HashMap<>();
        map1.put("加工产品", ledgerAdditif.getProductName());

        Map<String, String> map2 = new HashMap<>();
        map2.put("添加剂", ledgerAdditif.getAdditifName());

        Map<String, String> map3 = new HashMap<>();
        map3.put("原料用量", ledgerAdditif.getMaterialQuantity());

        Map<String, String> map4 = new HashMap<>();
        map4.put("添加剂用量", ledgerAdditif.getAdditifQuantity());

        Map<String, String> map5 = new HashMap<>();
        map5.put("添加剂使用日期", DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));

        Map<String, String> map6 = new HashMap<>();
        if(StringUtil.isNotEmptyObject(ledgerAdditif.getFileId())) {
            String fileUrl = fileService.getHttpUrl(ledgerAdditif.getFileId(), fileService.getServerConfig(vfsSetting.getServerName()));
            map6.put("ticket", fileUrl);
        }

        List<Map<String, String>> result = new ArrayList<>();
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);

        return result;
    }

    /**
     * 废弃物台账明细
     * @param id
     * @return
     */
    private List<Map<String, String>> wasteDetail(String id) throws Exception{
        LedgerWaste ledgerWaste = ledgerWasteMapper.selectByPrimaryKey(Integer.parseInt(id));

        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerWaste.getLedgerId());

        Map<String, String> map1 = new HashMap<>();
        map1.put("数量", ledgerWaste.getQuantity());

        Map<String, String> map2 = new HashMap<>();
        map2.put("回收企业", ledgerWaste.getPurpose());

        Map<String, String> map3 = new HashMap<>();
        map3.put("回收人", ledgerWaste.getOperater());

        Map<String, String> map4 = new HashMap<>();
        map4.put("监督人", ledgerWaste.getChecker());

        Map<String, String> map5 = new HashMap<>();
        map5.put("回收日期", DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));

        Map<String, String> map6 = new HashMap<>();
        if(StringUtil.isNotEmptyObject(ledgerWaste.getFileId())) {
            String fileUrl = fileService.getHttpUrl(ledgerWaste.getFileId(), fileService.getServerConfig(vfsSetting.getServerName()));
            map6.put("ticket", fileUrl);
        }

        List<Map<String, String>> result = new ArrayList<>();
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);

        return result;
    }

    /**
     * 消毒记录台账明细
     * @param id
     * @return
     */
    private List<Map<String, String>> sterilizeDetail(String id) throws Exception{
        LedgerSterilize ledgerSterilize = ledgerSterilizeMapper.selectByPrimaryKey(Integer.parseInt(id));

        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerSterilize.getLedgerId());

        Map<String, String> map1 = new HashMap<>();
        map1.put("消毒内容", ledgerSterilize.getItemName());

        Map<String, String> map2 = new HashMap<>();
        map2.put("消毒数量", ledgerSterilize.getQuantity());

        Map<String, String> map3 = new HashMap<>();
        map3.put("消毒方法", ledgerSterilize.getMethod());

        Map<String, String> map4 = new HashMap<>();
        map4.put("消毒人", ledgerSterilize.getOperater());

        Map<String, String> map5 = new HashMap<>();
        map5.put("监督人", ledgerSterilize.getChecker());

        Map<String, String> map6 = new HashMap<>();
        map6.put("消毒时间", DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));

        Map<String, String> map7 = new HashMap<>();
        if(StringUtil.isNotEmptyObject(ledgerSterilize.getFileId())) {
            String fileUrl = fileService.getHttpUrl(ledgerSterilize.getFileId(), fileService.getServerConfig(vfsSetting.getServerName()));
            map7.put("ticket", fileUrl);
        }

        List<Map<String, String>> result = new ArrayList<>();
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);
        result.add(map7);

        return result;
    }

    /**
     * 旅游接待台账明细
     * @param id
     * @return
     */
    private List<Map<String, String>> travelDetail(String id) throws Exception{
        LedgerTravel ledgerTravel = ledgerTravelMapper.selectByPrimaryKey(Integer.parseInt(id));

        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerTravel.getLedgerId());

        Map<String, String> map1 = new HashMap<>();
        map1.put("旅行社", ledgerTravel.getTravelName());

        Map<String, String> map2 = new HashMap<>();
        map2.put("就餐团队", ledgerTravel.getGroupName());

        Map<String, String> map3 = new HashMap<>();
        map3.put("来源地", ledgerTravel.getSourceFrom());

        Map<String, String> map4 = new HashMap<>();
        map4.put("就餐人数", ledgerTravel.getPeopleNum());

        Map<String, String> map5 = new HashMap<>();
        map5.put("就餐时间", ledgerTravel.getDinnerTime());

        Map<String, String> map6 = new HashMap<>();
        map6.put("就餐日期", DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));

        Map<String, String> map7 = new HashMap<>();
        if(StringUtil.isNotEmptyObject(ledgerTravel.getFileId())) {
            String fileUrl = fileService.getHttpUrl(ledgerTravel.getFileId(), fileService.getServerConfig(vfsSetting.getServerName()));
            map7.put("ticket", fileUrl);
        }

        List<Map<String, String>> result = new ArrayList<>();
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);
        result.add(map7);

        return result;
    }

    /**
     * 集体聚餐台账明细
     * @param id
     * @return
     */
    private List<Map<String, String>> dinnerDetail(String id) throws Exception{
        LedgerDinner ledgerDinner = ledgerDinnerMapper.selectByPrimaryKey(Integer.parseInt(id));

        Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerDinner.getLedgerId());

        Map<String, String> map1 = new HashMap<>();
        map1.put("举办者", ledgerDinner.getDinnerName());

        Map<String, String> map2= new HashMap<>();
        map2.put("就餐人数", ledgerDinner.getDinnerNum());

        Map<String, String> map3 = new HashMap<>();
        map3.put("联系电话", ledgerDinner.getPhone());

        Map<String, String> map4 = new HashMap<>();
        map4.put("举办事宜", ledgerDinner.getMatters());

        Map<String, String> map5 = new HashMap<>();
        map5.put("就餐时间", ledgerDinner.getDinnerTime());

        Map<String, String> map6 = new HashMap<>();
        map6.put("就餐日期", DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));

        Map<String, String> map7 = new HashMap<>();
        if(StringUtil.isNotEmptyObject(ledgerDinner.getFileId())) {
            String fileUrl = fileService.getHttpUrl(ledgerDinner.getFileId(), fileService.getServerConfig(vfsSetting.getServerName()));
            map7.put("ticket", fileUrl);
        }

        List<Map<String, String>> result = new ArrayList<>();
        result.add(map1);
        result.add(map2);
        result.add(map3);
        result.add(map4);
        result.add(map5);
        result.add(map6);
        result.add(map7);

        return result;
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
                return "";
        }
    }
}
