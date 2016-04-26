package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.configuration.vfs.VfsSetting;
import com.bdx.rainbow.service.mapp.ISK0022Service;
import com.bdx.rainbow.service.vfs.IFile;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.ygcf.entity.*;
import com.bdx.rainbow.ygcf.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 台账上传
 * Created by fusj on 16/3/22.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0022ServiceImpl implements ISK0022Service {

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
    private VfsSetting vfsSetting;

    @Autowired
    private IFile fileService;

    /**
     * 台账上传
     * @param list
     * @param ticket
     * @param sysUserInfo
     */
    @Override
    public void uploadLedger(List<Map<String, String>> list, byte[] ticket, IUserInfo sysUserInfo) throws Exception {
        // 上传票证
        String fileId = "";

        if(null != ticket) {
            fileId = fileService.uploadFile(fileService.getServerConfig(vfsSetting.getServerName()), ticket, "jpg", sysUserInfo.getUserId() + "_" + System.currentTimeMillis()+"_" + Math.random(), sysUserInfo.getUserId(), false, true);
        }

        // 上传台账数据
        for(Map<String, String> map : list) {
            /*
             采购
                productName/quantity/standard/validity/provider/producter/producternumber
             添加剂
                productName/additifName/materialQuantity/standard/additifQuantity/additifStandard
             废弃物
                purpose/quantity/standard/operater/checker
             消毒记录
                itemName/quantity/standard/method/checker/operater
             旅游接待
                travelAgencyName/repastTeamName/fromLocal/quantity/repastTime
             集体聚餐
                organizersName/quantity/phone/matters/repastTime
            */

            if(!StringUtil.isNotEmptyObject(map.get("ledgerType"))) {
                continue;
            }

            String ledgerType = convertLedgerType(map.get("ledgerType").toString());
            switch (ledgerType) {
                case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                    dealLedgerPurchase(map, fileId, sysUserInfo);
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                    dealLedgerAdditif(map, fileId, sysUserInfo);
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                    dealLedgerWaste(map, fileId, sysUserInfo);
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                    dealLedgerSterilize(map, fileId, sysUserInfo);
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                    dealLedgerTravel(map, fileId, sysUserInfo);
                    break;
                case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                    dealLedgerDinner(map, fileId, sysUserInfo);
                    break;
            }
        }

    }

    /**
     * 采购台账
     * @param map
     * @param fileId
     * @param sysUserInfo
     *
     *  采购
            productName/quantity/standard/validity/provider/producter/producternumber
     */
    private void dealLedgerPurchase(Map<String, String> map, String fileId, IUserInfo sysUserInfo) throws Exception {
        // 明细编号不为空
        if(StringUtil.isNotEmptyObject(map.get("id"))) {
            String id = map.get("id").toString();
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账明细记录
            LedgerPurchase ledgerPurchase = ledgerPurchaseMapper.selectByPrimaryKey(Integer.parseInt(id));

            Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerPurchase.getLedgerId());

            // 数据库中台账日期
            String oldLedgerDate = ledger.getLedgerDate();

            // 用户修改了台账日期，如果有票据的，需要把相同票据的台账记录同样修改为改日期
            if(!DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4).equals(oldLedgerDate)) {

                // 修改后的台账记录
                Ledger ledgerData = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_PURCHASE, date, sysUserInfo);

                // 更新台账ID
                ledgerPurchase.setLedgerId(ledgerData.getLedgerId());

                // 有票据
                if(StringUtil.isNotEmptyObject(ledgerPurchase.getFileId())) {
                    // 做同步修改

                    // 更新的条件
                    LedgerPurchaseExample example = new LedgerPurchaseExample();
                    example.createCriteria().andFileIdEqualTo(ledgerPurchase.getFileId());

                    // 需要更新的内容
                    LedgerPurchase record = new LedgerPurchase();
                    record.setLedgerId(ledgerData.getLedgerId());

                    if(StringUtil.isNotEmptyObject(fileId)) {
                        record.setFileId(fileId);
                    }

                    ledgerPurchaseMapper.updateByExampleSelective(record, example);
                }
            }

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("productName"))) {
                ledgerPurchase.setProductName(map.get("productName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerPurchase.setQuantity(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerPurchase.setStandard(map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("validity"))) {
                ledgerPurchase.setValidity(map.get("validity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("provider"))) {
                ledgerPurchase.setProvider(map.get("provider").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("producter"))) {
                ledgerPurchase.setProductor(map.get("producter").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("producternumber"))) {
                ledgerPurchase.setProductNo(map.get("producternumber").toString());
            }

            ledgerPurchase.setUpdateDate(DateUtil.getCurrent());
            ledgerPurchase.setUpdateUserid(sysUserInfo.getUserId());

            ledgerPurchaseMapper.updateByPrimaryKeySelective(ledgerPurchase);
        }
        // 新增
        else {
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账记录
            Ledger ledger = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_PURCHASE, date, sysUserInfo);

            LedgerPurchase ledgerPurchaseData = new LedgerPurchase();
            ledgerPurchaseData.setLedgerId(ledger.getLedgerId());

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("productName"))) {
                ledgerPurchaseData.setProductName(map.get("productName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerPurchaseData.setQuantity(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerPurchaseData.setStandard(map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("validity"))) {
                ledgerPurchaseData.setValidity(map.get("validity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("provider"))) {
                ledgerPurchaseData.setProvider(map.get("provider").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("producter"))) {
                ledgerPurchaseData.setProductor(map.get("producter").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("producternumber"))) {
                ledgerPurchaseData.setProductNo(map.get("producternumber").toString());
            }

            if(StringUtil.isNotEmptyObject(fileId)) {
                ledgerPurchaseData.setFileId(fileId);
            }

            ledgerPurchaseData.setCreateDate(DateUtil.getCurrent());
            ledgerPurchaseData.setCreateUserid(sysUserInfo.getUserId());
            ledgerPurchaseData.setIsDel("0");

            ledgerPurchaseMapper.insertSelective(ledgerPurchaseData);
        }
    }

    /**
     * 添加剂台账
     * @param map
     * @param fileId
     * @param sysUserInfo
     *
     *  添加剂
            productName/additifName/materialQuantity/standard/additifQuantity/additifStandard
     */
    private void dealLedgerAdditif(Map<String, String> map, String fileId, IUserInfo sysUserInfo) throws Exception{
        // 明细编号不为空
        if(StringUtil.isNotEmptyObject(map.get("id"))) {
            String id = map.get("id").toString();
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账明细记录
            LedgerAdditif ledgerAdditif = ledgerAdditifMapper.selectByPrimaryKey(Integer.parseInt(id));

            Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerAdditif.getLedgerId());

            // 数据库中台账日期
            String oldLedgerDate = ledger.getLedgerDate();

            // 用户修改了台账日期，如果有票据的，需要把相同票据的台账记录同样修改为改日期
            if(!DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4).equals(oldLedgerDate)) {

                // 修改后的台账记录
                Ledger ledgerData = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_ADDITIF, date, sysUserInfo);

                // 更新台账ID
                ledgerAdditif.setLedgerId(ledgerData.getLedgerId());

                // 有票据
                if(StringUtil.isNotEmptyObject(ledgerAdditif.getFileId())) {
                    // 做同步修改

                    // 更新的条件
                    LedgerAdditifExample example = new LedgerAdditifExample();
                    example.createCriteria().andFileIdEqualTo(ledgerAdditif.getFileId());

                    // 需要更新的内容
                    LedgerAdditif record = new LedgerAdditif();
                    record.setLedgerId(ledgerData.getLedgerId());

                    if(StringUtil.isNotEmptyObject(fileId)) {
                        record.setFileId(fileId);
                    }

                    ledgerAdditifMapper.updateByExampleSelective(record, example);
                }
            }

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("productName"))) {
                ledgerAdditif.setProductName(map.get("productName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("additifName"))) {
                ledgerAdditif.setAdditifName(map.get("additifName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("materialQuantity"))) {
                ledgerAdditif.setMaterialQuantity(map.get("materialQuantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerAdditif.setMaterialStandard(map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("additifQuantity"))) {
                ledgerAdditif.setAdditifQuantity(map.get("additifQuantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("additifStandard"))) {
                ledgerAdditif.setAdditifStandard(map.get("additifStandard").toString());
            }

            ledgerAdditif.setUpdateDate(DateUtil.getCurrent());
            ledgerAdditif.setUpdateUserid(sysUserInfo.getUserId());

            ledgerAdditifMapper.updateByPrimaryKeySelective(ledgerAdditif);
        }
        // 新增
        else {
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账记录
            Ledger ledger = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_ADDITIF, date, sysUserInfo);

            LedgerAdditif ledgeradditifData = new LedgerAdditif();
            ledgeradditifData.setLedgerId(ledger.getLedgerId());

            if(StringUtil.isNotEmptyObject(map.get("productName"))) {
                ledgeradditifData.setProductName(map.get("productName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("additifName"))) {
                ledgeradditifData.setAdditifName(map.get("additifName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("materialQuantity"))) {
                ledgeradditifData.setMaterialQuantity(map.get("materialQuantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgeradditifData.setMaterialStandard(map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("additifQuantity"))) {
                ledgeradditifData.setAdditifQuantity(map.get("additifQuantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("additifStandard"))) {
                ledgeradditifData.setAdditifStandard(map.get("additifStandard").toString());
            }

            if(StringUtil.isNotEmptyObject(fileId)) {
                ledgeradditifData.setFileId(fileId);
            }

            ledgeradditifData.setCreateDate(DateUtil.getCurrent());
            ledgeradditifData.setCreateUserid(sysUserInfo.getUserId());
            ledgeradditifData.setIsDel("0");

            ledgerAdditifMapper.insertSelective(ledgeradditifData);
        }
    }

    /**
     * 废弃物台账
     * @param map
     * @param fileId
     * @param sysUserInfo
     *
     *  废弃物
            purpose/quantity/standard/operater/checker
     */
    private void dealLedgerWaste(Map<String, String> map, String fileId, IUserInfo sysUserInfo) throws Exception {
        // 明细编号不为空
        if(StringUtil.isNotEmptyObject(map.get("id"))) {
            String id = map.get("id").toString();
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账明细记录
            LedgerWaste ledgerWaste = ledgerWasteMapper.selectByPrimaryKey(Integer.parseInt(id));

            Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerWaste.getLedgerId());

            // 数据库中台账日期
            String oldLedgerDate = ledger.getLedgerDate();

            // 用户修改了台账日期，如果有票据的，需要把相同票据的台账记录同样修改为改日期
            if(!DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4).equals(oldLedgerDate)) {

                // 修改后的台账记录
                Ledger ledgerData = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_WASTE, date, sysUserInfo);

                // 更新台账ID
                ledgerWaste.setLedgerId(ledgerData.getLedgerId());

                // 有票据
                if(StringUtil.isNotEmptyObject(ledgerWaste.getFileId())) {
                    // 做同步修改

                    // 更新的条件
                    LedgerWasteExample example = new LedgerWasteExample();
                    example.createCriteria().andFileIdEqualTo(ledgerWaste.getFileId());

                    // 需要更新的内容
                    LedgerWaste record = new LedgerWaste();
                    record.setLedgerId(ledgerData.getLedgerId());

                    if(StringUtil.isNotEmptyObject(fileId)) {
                        record.setFileId(fileId);
                    }

                    ledgerWasteMapper.updateByExampleSelective(record, example);
                }
            }

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("purpose"))) {
                ledgerWaste.setPurpose(map.get("purpose").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerWaste.setQuantity(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerWaste.setQuantity(ledgerWaste.getQuantity() + map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("operater"))) {
                ledgerWaste.setOperater(map.get("operater").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("checker"))) {
                ledgerWaste.setChecker(map.get("checker").toString());
            }

            ledgerWaste.setUpdateDate(DateUtil.getCurrent());
            ledgerWaste.setUpdateUserid(sysUserInfo.getUserId());

            ledgerWasteMapper.updateByPrimaryKeySelective(ledgerWaste);
        }
        // 新增
        else {
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账记录
            Ledger ledger = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_WASTE, date, sysUserInfo);

            LedgerWaste ledgerWasteData = new LedgerWaste();
            ledgerWasteData.setLedgerId(ledger.getLedgerId());

            if(StringUtil.isNotEmptyObject(map.get("purpose"))) {
                ledgerWasteData.setPurpose(map.get("purpose").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerWasteData.setQuantity(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerWasteData.setQuantity(ledgerWasteData.getQuantity() + map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("operater"))) {
                ledgerWasteData.setOperater(map.get("operater").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("checker"))) {
                ledgerWasteData.setChecker(map.get("checker").toString());
            }

            if(StringUtil.isNotEmptyObject(fileId)) {
                ledgerWasteData.setFileId(fileId);
            }

            ledgerWasteData.setCreateDate(DateUtil.getCurrent());
            ledgerWasteData.setCreateUserid(sysUserInfo.getUserId());
            ledgerWasteData.setIsDel("0");

            ledgerWasteMapper.insertSelective(ledgerWasteData);
        }
    }

    /**
     * 消毒记录台账
     * @param map
     * @param fileId
     * @param sysUserInfo
     *
     *  消毒记录
            itemName/quantity/standard/method/checker/operater
     */
    private void dealLedgerSterilize(Map<String, String> map, String fileId, IUserInfo sysUserInfo) throws Exception {
        // 明细编号不为空
        if(StringUtil.isNotEmptyObject(map.get("id"))) {
            String id = map.get("id").toString();
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账明细记录
            LedgerSterilize ledgerSterilize = ledgerSterilizeMapper.selectByPrimaryKey(Integer.parseInt(id));

            Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerSterilize.getLedgerId());

            // 数据库中台账日期
            String oldLedgerDate = ledger.getLedgerDate();

            // 用户修改了台账日期，如果有票据的，需要把相同票据的台账记录同样修改为改日期
            if(!DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4).equals(oldLedgerDate)) {

                // 修改后的台账记录
                Ledger ledgerData = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_STERILIZE, date, sysUserInfo);

                // 更新台账ID
                ledgerSterilize.setLedgerId(ledgerData.getLedgerId());

                // 有票据
                if(StringUtil.isNotEmptyObject(ledgerSterilize.getFileId())) {
                    // 做同步修改

                    // 更新的条件
                    LedgerSterilizeExample example = new LedgerSterilizeExample();
                    example.createCriteria().andFileIdEqualTo(ledgerSterilize.getFileId());

                    // 需要更新的内容
                    LedgerSterilize record = new LedgerSterilize();
                    record.setLedgerId(ledgerData.getLedgerId());

                    if(StringUtil.isNotEmptyObject(fileId)) {
                        record.setFileId(fileId);
                    }

                    ledgerSterilizeMapper.updateByExampleSelective(record, example);
                }
            }

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("itemName"))) {
                ledgerSterilize.setItemName(map.get("itemName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerSterilize.setQuantity(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerSterilize.setStandard(map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("method"))) {
                ledgerSterilize.setMethod(map.get("method").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("checker"))) {
                ledgerSterilize.setChecker(map.get("checker").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("operater"))) {
                ledgerSterilize.setOperater(map.get("operater").toString());
            }

            ledgerSterilize.setDoTime(DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4));

            ledgerSterilize.setUpdateDate(DateUtil.getCurrent());
            ledgerSterilize.setUpdateUserid(sysUserInfo.getUserId());

            ledgerSterilizeMapper.updateByPrimaryKeySelective(ledgerSterilize);
        }
        // 新增
        else {
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账记录
            Ledger ledger = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_STERILIZE, date, sysUserInfo);

            LedgerSterilize ledgerSterilizeData = new LedgerSterilize();
            ledgerSterilizeData.setLedgerId(ledger.getLedgerId());

            if(StringUtil.isNotEmptyObject(map.get("itemName"))) {
                ledgerSterilizeData.setItemName(map.get("itemName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerSterilizeData.setQuantity(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("standard"))) {
                ledgerSterilizeData.setStandard(map.get("standard").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("method"))) {
                ledgerSterilizeData.setMethod(map.get("method").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("checker"))) {
                ledgerSterilizeData.setChecker(map.get("checker").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("operater"))) {
                ledgerSterilizeData.setOperater(map.get("operater").toString());
            }

            if(StringUtil.isNotEmptyObject(fileId)) {
                ledgerSterilizeData.setFileId(fileId);
            }

            ledgerSterilizeData.setDoTime(DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4));

            ledgerSterilizeData.setCreateDate(DateUtil.getCurrent());
            ledgerSterilizeData.setCreateUserid(sysUserInfo.getUserId());
            ledgerSterilizeData.setIsDel("0");

            ledgerSterilizeMapper.insertSelective(ledgerSterilizeData);
        }
    }

    /**
     * 旅游接待台账
     * @param map
     * @param fileId
     * @param sysUserInfo
     *
     *  旅游接待
            travelAgencyName/repastTeamName/fromLocal/quantity/repastTime
     */
    private void dealLedgerTravel(Map<String, String> map, String fileId, IUserInfo sysUserInfo) throws Exception {
        // 明细编号不为空
        if(StringUtil.isNotEmptyObject(map.get("id"))) {
            String id = map.get("id").toString();
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账明细记录
            LedgerTravel ledgerTravel = ledgerTravelMapper.selectByPrimaryKey(Integer.parseInt(id));

            Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerTravel.getLedgerId());

            // 数据库中台账日期
            String oldLedgerDate = ledger.getLedgerDate();

            // 用户修改了台账日期，如果有票据的，需要把相同票据的台账记录同样修改为改日期
            if(!DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4).equals(oldLedgerDate)) {

                // 修改后的台账记录
                Ledger ledgerData = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_TRAVEL, date, sysUserInfo);

                // 更新台账ID
                ledgerTravel.setLedgerId(ledgerData.getLedgerId());

                // 有票据
                if(StringUtil.isNotEmptyObject(ledgerTravel.getFileId())) {
                    // 做同步修改

                    // 更新的条件
                    LedgerTravelExample example = new LedgerTravelExample();
                    example.createCriteria().andFileIdEqualTo(ledgerTravel.getFileId());

                    // 需要更新的内容
                    LedgerTravel record = new LedgerTravel();
                    record.setLedgerId(ledgerData.getLedgerId());

                    if(StringUtil.isNotEmptyObject(fileId)) {
                        record.setFileId(fileId);
                    }

                    ledgerTravelMapper.updateByExampleSelective(record, example);
                }
            }

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("travelAgencyName"))) {
                ledgerTravel.setTravelName(map.get("travelAgencyName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("repastTeamName"))) {
                ledgerTravel.setGroupName(map.get("repastTeamName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("fromLocal"))) {
                ledgerTravel.setSourceFrom(map.get("fromLocal").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerTravel.setPeopleNum(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("repastTime"))) {
                ledgerTravel.setDinnerTime(map.get("repastTime").toString());
            }

            ledgerTravel.setUpdateDate(DateUtil.getCurrent());
            ledgerTravel.setUpdateUserid(sysUserInfo.getUserId());

            ledgerTravelMapper.updateByPrimaryKeySelective(ledgerTravel);
        }
        // 新增
        else {
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账记录
            Ledger ledger = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_TRAVEL, date, sysUserInfo);

            LedgerTravel ledgerTravelData = new LedgerTravel();
            ledgerTravelData.setLedgerId(ledger.getLedgerId());

            if(StringUtil.isNotEmptyObject(map.get("travelAgencyName"))) {
                ledgerTravelData.setTravelName(map.get("travelAgencyName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("repastTeamName"))) {
                ledgerTravelData.setGroupName(map.get("repastTeamName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("fromLocal"))) {
                ledgerTravelData.setSourceFrom(map.get("fromLocal").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerTravelData.setPeopleNum(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("repastTime"))) {
                ledgerTravelData.setDinnerTime(map.get("repastTime").toString());
            }

            if(StringUtil.isNotEmptyObject(fileId)) {
                ledgerTravelData.setFileId(fileId);
            }

            ledgerTravelData.setCreateDate(DateUtil.getCurrent());
            ledgerTravelData.setCreateUserid(sysUserInfo.getUserId());
            ledgerTravelData.setIsDel("0");

            ledgerTravelMapper.insertSelective(ledgerTravelData);
        }
    }

    /**
     * 集体聚餐台账
     * @param map
     * @param fileId
     * @param sysUserInfo
     *
     *  集体聚餐
            organizersName/quantity/phone/matters/repastTime
     */
    private void dealLedgerDinner(Map<String, String> map, String fileId, IUserInfo sysUserInfo) throws Exception {
        // 明细编号不为空
        if(StringUtil.isNotEmptyObject(map.get("id"))) {
            String id = map.get("id").toString();
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账明细记录
            LedgerDinner ledgerDinner = ledgerDinnerMapper.selectByPrimaryKey(Integer.parseInt(id));

            Ledger ledger = ledgerMapper.selectByPrimaryKey(ledgerDinner.getLedgerId());

            // 数据库中台账日期
            String oldLedgerDate = ledger.getLedgerDate();

            // 用户修改了台账日期，如果有票据的，需要把相同票据的台账记录同样修改为改日期
            if(!DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4).equals(oldLedgerDate)) {

                // 修改后的台账记录
                Ledger ledgerData = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_DINNER, date, sysUserInfo);

                // 更新台账ID
                ledgerDinner.setLedgerId(ledgerData.getLedgerId());

                // 有票据
                if(StringUtil.isNotEmptyObject(ledgerDinner.getFileId())) {
                    // 做同步修改

                    // 更新的条件
                    LedgerDinnerExample example = new LedgerDinnerExample();
                    example.createCriteria().andFileIdEqualTo(ledgerDinner.getFileId());

                    // 需要更新的内容
                    LedgerDinner record = new LedgerDinner();
                    record.setLedgerId(ledgerData.getLedgerId());

                    if(StringUtil.isNotEmptyObject(fileId)) {
                        record.setFileId(fileId);
                    }

                    ledgerDinnerMapper.updateByExampleSelective(record, example);
                }
            }

            // 修改台账信息
            if(StringUtil.isNotEmptyObject(map.get("organizersName"))) {
                ledgerDinner.setDinnerName(map.get("organizersName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerDinner.setDinnerNum(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("phone"))) {
                ledgerDinner.setPhone(map.get("phone").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("matters"))) {
                ledgerDinner.setMatters(map.get("matters").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("repastTime"))) {
                ledgerDinner.setDinnerTime(map.get("repastTime").toString());
            }

            ledgerDinner.setUpdateDate(DateUtil.getCurrent());
            ledgerDinner.setUpdateUserid(sysUserInfo.getUserId());

            ledgerDinnerMapper.updateByPrimaryKeySelective(ledgerDinner);
        }
        // 新增
        else {
            // 接口传的台账日期
            String date = map.get("date").toString();

            // 台账记录
            Ledger ledger = checkLedgerIsExists(LedgerTypeConstants.LEDGER_TYPE_DINNER, date, sysUserInfo);

            LedgerDinner ledgerDinnerData = new LedgerDinner();
            ledgerDinnerData.setLedgerId(ledger.getLedgerId());

            if(StringUtil.isNotEmptyObject(map.get("organizersName"))) {
                ledgerDinnerData.setDinnerName(map.get("organizersName").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("quantity"))) {
                ledgerDinnerData.setDinnerNum(map.get("quantity").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("phone"))) {
                ledgerDinnerData.setPhone(map.get("phone").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("matters"))) {
                ledgerDinnerData.setMatters(map.get("matters").toString());
            }

            if(StringUtil.isNotEmptyObject(map.get("repastTime"))) {
                ledgerDinnerData.setDinnerTime(map.get("repastTime").toString());
            }

            if(StringUtil.isNotEmptyObject(fileId)) {
                ledgerDinnerData.setFileId(fileId);
            }

            ledgerDinnerData.setCreateDate(DateUtil.getCurrent());
            ledgerDinnerData.setCreateUserid(sysUserInfo.getUserId());
            ledgerDinnerData.setIsDel("0");

            ledgerDinnerMapper.insertSelective(ledgerDinnerData);
        }
    }

    /**
     * 校验日期和台账类型是否存在台账记录，没有则新增，有则返回
     * @param ledgerType
     * @param date
     * @return
     */
    private Ledger checkLedgerIsExists(String ledgerType, String date, IUserInfo sysUserInfo) throws Exception {
        String ledgerDate = DateUtil.parse(date, DateUtil.DATE_FORMAT2, DateUtil.DATE_FORMAT4);

        // 查询修改后的日期是否存在台账记录
        LedgerExample ledgerExample = new LedgerExample();
        ledgerExample.createCriteria().andLedgerDateEqualTo(ledgerDate)
                .andEnterpriseIdEqualTo(sysUserInfo.getUser().getCorpId())
                .andLedgerTypeEqualTo(ledgerType);

        List<Ledger> ledgerList = ledgerMapper.selectByExample(ledgerExample);

        // 不存在, 新增台账上传记录
        if(CollectionUtils.isEmpty(ledgerList)) {
            Ledger ledgerData = new Ledger();
            ledgerData.setLedgerType(ledgerType);
            ledgerData.setEnterpriseId(sysUserInfo.getUser().getCorpId());
            ledgerData.setLedgerDate(ledgerDate);
            ledgerData.setIsUpload("1");
            ledgerData.setIsDel("0");
            ledgerData.setCreateDate(DateUtil.getCurrent());
            ledgerData.setCreateUserid(sysUserInfo.getUserId());

            ledgerMapper.insertSelective(ledgerData);

            return ledgerData;
        } else {
            return ledgerList.get(0);
        }
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
