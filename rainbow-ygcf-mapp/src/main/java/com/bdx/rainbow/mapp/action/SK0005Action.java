package com.bdx.rainbow.mapp.action;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.mapp.core.annotation.Action;
import com.bdx.rainbow.mapp.core.base.AbstractMappAction;
import com.bdx.rainbow.mapp.core.context.MappContext;
import com.bdx.rainbow.mapp.core.exception.ActionException;
import com.bdx.rainbow.mapp.core.exception.ErrorCodeDefine;
import com.bdx.rainbow.mapp.entity.MappSessionDetail;
import com.bdx.rainbow.mapp.model.req.SK0005Request;
import com.bdx.rainbow.mapp.model.rsp.SK0005Response;
import com.bdx.rainbow.service.mapp.ISK0005Service;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.ygcf.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by core on 15/10/15.
 * 台帐明细
 */
@Service("sk0005")
@Action(bizcode="sk0005", version = "1.0", usercheck=false, ipcheck=false)
public class SK0005Action extends AbstractMappAction<SK0005Request, SK0005Response> {

    private static final Integer OUT_OF_DAY = 7;

    @Autowired
    private ISK0005Service sk0005Service;

    @Override
    public void doAction(SK0005Request request, SK0005Response response) throws Exception {
        MappSessionDetail sessionDetail = (MappSessionDetail) MappContext.getAttribute(MappContext.MAPPCONTEXT_USER);

        if(null != sessionDetail && null != sessionDetail.getCorpId()) {
            // 一页显示数量为空，默认显示5条
            if(!StringUtil.isNotEmptyObject(request.getPageCount())) {
                request.setPageCount("5");
            }

            // 默认显示第一页
            if(!StringUtil.isNotEmptyObject(request.getPageNumber())) {
                request.setPageNumber("0");
            }

            // 台账类型，全部=0
            List<Map<Ledger, List>> list = sk0005Service.legalList(request.getLedgerType(), request.getBegTime(), request.getEndTime(),
                    request.getPageNumber(), request.getPageCount(), sessionDetail.getCorpId());

            // 结果为空,并返回
            if(CollectionUtils.isEmpty(list)) {
                response.setDays(null);
                return;
            }

            List<SK0005Response.Day> daysList = new ArrayList<>();

            // 当前时间字符串
            String nowDate = DateUtil.parse(DateUtil.getCurrent().getTime(), DateUtil.DATE_FORMAT4);

            Map<String, SK0005Response.Day> dayMap = new HashMap<>();

            // 组装response对象
            for(int i = 0; i < list.size(); i++) {
                Map<Ledger, List> map = list.get(i);

                SK0005Response.Day day;

                for(Ledger ledger : map.keySet()) {

                    // 超过N天则不能修改
                    boolean outOfDate = (Integer.parseInt(nowDate) - Integer.parseInt(ledger.getLedgerDate())) > OUT_OF_DAY ? true : false;

                    if(dayMap.containsKey(ledger.getLedgerDate())) {
                        day = dayMap.get(ledger.getLedgerDate());
                        day.getItems().addAll(buildDayItem(ledger.getLedgerType(), outOfDate, map.get(ledger)));
                    }
                    else {
                        day = new SK0005Response.Day();
                        day.setDate(DateUtil.parse(ledger.getLedgerDate(), DateUtil.DATE_FORMAT4, DateUtil.DATE_FORMAT2));
                        day.setItems(buildDayItem(ledger.getLedgerType(), outOfDate, map.get(ledger)));

                        dayMap.put(ledger.getLedgerDate(), day);
                        daysList.add(day);
                    }
                }
            }

            response.setDays(daysList);
        } else {
            throw new ActionException(ErrorCodeDefine.NO_USER_INFO, "登陆超时");
//            throw new BusinessException(new DefaultExceptionCode(ErrorCodeDefine.NO_USER_INFO, "登录超时!"));
        }
    }

    /**
     * 组装台账明细
     * @param list
     * @return
     */
    private List<SK0005Response.Item> buildDayItem(String ledgerType, boolean outOfDate, List list) {
        switch (ledgerType) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                List<LedgerPurchase> purchaseList = list;
                return buildPurchaseItem(ledgerType, outOfDate, purchaseList);
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                List<LedgerAdditif> additifList = list;
                return buildAdditifItem(ledgerType, outOfDate, additifList);
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                List<LedgerWaste> wasteList = list;
                return buildWasteItem(ledgerType, outOfDate, wasteList);
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                List<LedgerSterilize> sterilizeList = list;
                return buildSterilizeItem(ledgerType, outOfDate, sterilizeList);
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                List<LedgerTravel> travelList = list;
                return buildTravelItem(ledgerType, outOfDate, travelList);
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                List<LedgerDinner> dinnerList = list;
                return buildDinnerItem(ledgerType, outOfDate, dinnerList);
        }

        return new ArrayList<>();
    }

    /**
     * 采购台账明细组装
     * @param purchaseList
     * @return
     */
    private List<SK0005Response.Item> buildPurchaseItem(String ledgerType, boolean outOfDate, List<LedgerPurchase> purchaseList) {
        List<SK0005Response.Item> itemList = new ArrayList<>();

        for(LedgerPurchase ledgerPurchase : purchaseList) {
            SK0005Response.Item item = new SK0005Response.Item();

            item.setId(ledgerPurchase.getPurchaseId() + "");
            item.setTicket(ledgerPurchase.getFileId());
            item.setName(ledgerPurchase.getProductName());
            item.setUnit(ledgerPurchase.getStandard());
            item.setQuantity(ledgerPurchase.getQuantity());
            item.setType(convertLedgerType(ledgerType));
            item.setOutOfDate(outOfDate);

            Map<String, String> map = new HashMap<>();
            map.put("供货商", ledgerPurchase.getProvider());

            item.setMap(map);

            itemList.add(item);
        }

        return itemList;
    }

    /**
     * 添加剂台账明细组装
     * @param ledgerType
     * @param outOfDate
     * @param additifList
     * @return
     */
    private List<SK0005Response.Item> buildAdditifItem(String ledgerType, boolean outOfDate, List<LedgerAdditif> additifList) {
        List<SK0005Response.Item> itemList = new ArrayList<>();

        for(LedgerAdditif ledgerAdditif : additifList) {
            SK0005Response.Item item = new SK0005Response.Item();

            item.setId(ledgerAdditif.getAdditifId() + "");
            item.setTicket(ledgerAdditif.getFileId());
            item.setName(ledgerAdditif.getAdditifName());
            item.setQuantity(ledgerAdditif.getAdditifQuantity());
            item.setUnit(ledgerAdditif.getAdditifStandard());
            item.setType(convertLedgerType(ledgerType));
            item.setOutOfDate(outOfDate);

            Map<String, String> map = new HashMap<>();
            map.put("加工产品", ledgerAdditif.getProductName());

            item.setMap(map);

            itemList.add(item);
        }

        return itemList;
    }

    /**
     * 废弃物台账明细组装
     * @param ledgerType
     * @param outOfDate
     * @param wasteList
     * @return
     */
    private List<SK0005Response.Item> buildWasteItem(String ledgerType, boolean outOfDate, List<LedgerWaste> wasteList) {
        String wasteName = "泔水";

        List<SK0005Response.Item> itemList = new ArrayList<>();

        for(LedgerWaste ledgerWaste : wasteList) {
            SK0005Response.Item item = new SK0005Response.Item();

            item.setId(ledgerWaste.getWasteId() + "");
            item.setTicket(ledgerWaste.getFileId());
            item.setName(wasteName);
            item.setQuantity(ledgerWaste.getQuantity());
            item.setType(convertLedgerType(ledgerType));
            item.setOutOfDate(outOfDate);

            Map<String, String> map = new HashMap<>();
            map.put("回收企业", ledgerWaste.getPurpose());
            item.setMap(map);

            itemList.add(item);
        }

        return itemList;
    }

    /**
     * 消毒记录台账明细拼装
     * @param ledgerType
     * @param outOfDate
     * @param sterilizeList
     * @return
     */
    private List<SK0005Response.Item> buildSterilizeItem(String ledgerType, boolean outOfDate, List<LedgerSterilize> sterilizeList) {
        List<SK0005Response.Item> itemList = new ArrayList<>();

        for(LedgerSterilize ledgerSterilize : sterilizeList) {
            SK0005Response.Item item = new SK0005Response.Item();

            item.setId(ledgerSterilize.getSterilizeId() + "");
            item.setTicket(ledgerSterilize.getFileId());
            item.setName(ledgerSterilize.getItemName());
            item.setQuantity(ledgerSterilize.getQuantity());
            item.setUnit(ledgerSterilize.getStandard());
            item.setType(convertLedgerType(ledgerType));
            item.setOutOfDate(outOfDate);

            Map<String, String> map = new HashMap<>();
            map.put("消毒方法", ledgerSterilize.getMethod());
            item.setMap(map);

            itemList.add(item);
        }

        return itemList;
    }

    /**
     * 旅游接待台账明细组装
     * @param ledgerType
     * @param outOfDate
     * @param travelList
     * @return
     */
    private List<SK0005Response.Item> buildTravelItem(String ledgerType, boolean outOfDate, List<LedgerTravel> travelList) {
        List<SK0005Response.Item> itemList = new ArrayList<>();

        for(LedgerTravel ledgerTravel : travelList) {
            SK0005Response.Item item = new SK0005Response.Item();

            item.setId(ledgerTravel.getTravelId() + "");
            item.setTicket(ledgerTravel.getFileId());
            item.setName(ledgerTravel.getTravelName());
            item.setQuantity(ledgerTravel.getPeopleNum());
            item.setType(convertLedgerType(ledgerType));
            item.setOutOfDate(outOfDate);

            Map<String, String> map = new HashMap<>();
            map.put("就餐团队", ledgerTravel.getGroupName());
            map.put("time", ledgerTravel.getDinnerTime());
            item.setMap(map);

            itemList.add(item);
        }

        return itemList;
    }

    /**
     * 集体聚餐台账明细组装
     * @param ledgerType
     * @param outOfDate
     * @param dinnerList
     * @return
     */
    private List<SK0005Response.Item> buildDinnerItem(String ledgerType, boolean outOfDate, List<LedgerDinner> dinnerList) {
        List<SK0005Response.Item> itemList = new ArrayList<>();

        for(LedgerDinner ledgerDinner : dinnerList) {
            SK0005Response.Item item = new SK0005Response.Item();

            item.setId(ledgerDinner.getDinnerId() + "");
            item.setTicket(ledgerDinner.getFileId());
            item.setName(ledgerDinner.getDinnerName());
            item.setQuantity(ledgerDinner.getDinnerNum());
            item.setType(convertLedgerType(ledgerType));
            item.setOutOfDate(outOfDate);

            Map<String, String> map = new HashMap<>();
            map.put("举办事宜", ledgerDinner.getMatters());
            map.put("time", ledgerDinner.getDinnerTime());
            item.setMap(map);

            itemList.add(item);
        }

        return itemList;
    }

    /**
     * 数据库存储台账类型转换成手机端的台账类型
     * @param ledgerType
     * @return
     */
    private String convertLedgerType(String ledgerType) {
        switch (ledgerType) {
            case LedgerTypeConstants.LEDGER_TYPE_PURCHASE:
                return "1";
            case LedgerTypeConstants.LEDGER_TYPE_ADDITIF:
                return "2";
            case LedgerTypeConstants.LEDGER_TYPE_WASTE:
                return "3";
            case LedgerTypeConstants.LEDGER_TYPE_STERILIZE:
                return "4";
            case LedgerTypeConstants.LEDGER_TYPE_TRAVEL:
                return "5";
            case LedgerTypeConstants.LEDGER_TYPE_DINNER:
                return "6";
            default:
                return "";
        }
    }
}
