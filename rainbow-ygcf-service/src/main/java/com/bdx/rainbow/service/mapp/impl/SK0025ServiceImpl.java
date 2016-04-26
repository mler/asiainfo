package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.service.mapp.ISK0025Service;
import com.bdx.rainbow.ygcf.entity.*;
import com.bdx.rainbow.ygcf.mapper.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 台账删除
 * Created by fusj on 16/3/28.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0025ServiceImpl implements ISK0025Service {

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

    /**
     * 删除台账
     * @param ledgerType
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteLedgerById(String ledgerType, String id) throws Exception {
        switch (convertLedgerType(ledgerType)) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                deleteLedgerPurchase(Integer.parseInt(id));
                break;
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                deleteLedgerAdditif(Integer.parseInt(id));
                break;
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                deleteLedgerWaste(Integer.parseInt(id));
                break;
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                deleteLedgerSterilize(Integer.parseInt(id));
                break;
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                deleteLedgerTravel(Integer.parseInt(id));
                break;
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                deleteLedgerDinner(Integer.parseInt(id));
                break;
        }
    }

    /**
     * 采购台账删除
     * @param id
     */
    private void deleteLedgerPurchase(int id) throws Exception {
        LedgerPurchase ledgerPurchase = ledgerPurchaseMapper.selectByPrimaryKey(id);

        if(null != ledgerPurchase) {
            int ledgerId = ledgerPurchase.getLedgerId();

            // 如果台账日期下面没有台账，删除台账日期记录
            LedgerPurchaseExample example = new LedgerPurchaseExample();
            example.createCriteria().andLedgerIdEqualTo(ledgerId);

            List<LedgerPurchase> list = ledgerPurchaseMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                ledgerMapper.deleteByPrimaryKey(ledgerId);
            }

            ledgerPurchaseMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 添加剂台账删除
     * @param id
     */
    private void deleteLedgerAdditif(int id) {
        LedgerAdditif ledgerAdditif = ledgerAdditifMapper.selectByPrimaryKey(id);

        if(null != ledgerAdditif) {
            int ledgerId = ledgerAdditif.getLedgerId();

            LedgerAdditifExample example = new LedgerAdditifExample();
            example.createCriteria().andLedgerIdEqualTo(ledgerId);

            List<LedgerAdditif> list = ledgerAdditifMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                ledgerMapper.deleteByPrimaryKey(ledgerId);
            }

            ledgerPurchaseMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 废弃物台账删除
     * @param id
     */
    private void deleteLedgerWaste(int id) throws Exception {
        LedgerWaste ledgerWaste = ledgerWasteMapper.selectByPrimaryKey(id);

        if(null != ledgerWaste) {
            int ledgerId = ledgerWaste.getLedgerId();

            LedgerWasteExample example = new LedgerWasteExample();
            example.createCriteria().andLedgerIdEqualTo(ledgerId);

            List<LedgerWaste> list = ledgerWasteMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                ledgerMapper.deleteByPrimaryKey(ledgerId);
            }

            ledgerWasteMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 消毒记录台账删除
     * @param id
     */
    private void deleteLedgerSterilize(int id) {
        LedgerSterilize ledgerSterilize = ledgerSterilizeMapper.selectByPrimaryKey(id);

        if(null != ledgerSterilize) {
            int ledgerId = ledgerSterilize.getLedgerId();

            LedgerSterilizeExample example = new LedgerSterilizeExample();
            example.createCriteria().andLedgerIdEqualTo(ledgerId);

            List<LedgerSterilize> list = ledgerSterilizeMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                ledgerMapper.deleteByPrimaryKey(ledgerId);
            }

            ledgerSterilizeMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 旅游台账删除
     * @param id
     */
    private void deleteLedgerTravel(int id) {
        LedgerTravel ledgerTravel = ledgerTravelMapper.selectByPrimaryKey(id);

        if(null != ledgerTravel) {
            int ledgerId = ledgerTravel.getLedgerId();

            LedgerTravelExample example = new LedgerTravelExample();
            example.createCriteria().andLedgerIdEqualTo(ledgerId);

            List<LedgerTravel> list = ledgerTravelMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                ledgerMapper.deleteByPrimaryKey(ledgerId);
            }

            ledgerTravelMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 集体聚餐台账删除
     * @param id
     */
    private void deleteLedgerDinner(int id) {
        LedgerDinner ledgerDinner = ledgerDinnerMapper.selectByPrimaryKey(id);

        if(null != ledgerDinner) {
            int ledgerId = ledgerDinner.getLedgerId();

            LedgerDinnerExample example = new LedgerDinnerExample();
            example.createCriteria().andLedgerIdEqualTo(ledgerId);

            List<LedgerDinner> list = ledgerDinnerMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                ledgerMapper.deleteByPrimaryKey(ledgerId);
            }

            ledgerDinnerMapper.deleteByPrimaryKey(id);
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
                return ledgerType;
        }
    }
}
