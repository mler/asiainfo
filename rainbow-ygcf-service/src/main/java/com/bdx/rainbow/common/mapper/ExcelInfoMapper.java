package com.bdx.rainbow.common.mapper;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * excel数据保存业务类KEY-VALUE关系
 * Created by fusj on 16/1/20.
 */
public class ExcelInfoMapper {

    private static final String PURCHASE = "purchase";
    private static final String ADDITIF = "additif";
    private static final String DINNER = "dinner";
    private static final String STERILIZE = "sterilize";
    private static final String TRAVEL = "travel";
    private static final String WASTE = "waste";

    // 业务服务处理类
    private static Map<String, String> ExcelServiceNames = new HashMap<String, String>();

    // 版本信息
    private static Map<String, String> ExcelVersions = new HashMap<>();

    // excel文件名称
    private static Map<String, String> ExcelDownloadFileName = new HashMap<>();

    // excel文件中文名
    private static Map<String, String> ExcelDownloadChineseName = new HashMap<>();

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    static {
        ExcelServiceNames.put(PURCHASE, "ledgerPurchaseExcelService");
        ExcelServiceNames.put(ADDITIF, "ledgerAdditifExcelService");
        ExcelServiceNames.put(DINNER, "ledgerDinnerExcelService");
        ExcelServiceNames.put(STERILIZE, "ledgerSterilizeExcelService");
        ExcelServiceNames.put(TRAVEL, "ledgerTravelExcelService");
        ExcelServiceNames.put(WASTE, "ledgerWatseExcelService");

        ExcelVersions.put(PURCHASE, "1.0");
        ExcelVersions.put(ADDITIF, "1.0");
        ExcelVersions.put(DINNER, "1.0");
        ExcelVersions.put(STERILIZE, "1.0");
        ExcelVersions.put(TRAVEL, "1.0");
        ExcelVersions.put(WASTE, "1.0");

        ExcelDownloadFileName.put(PURCHASE, "ledger/ledger_purchase.xls");
        ExcelDownloadFileName.put(ADDITIF, "ledger/ledger_additif.xls");
        ExcelDownloadFileName.put(DINNER, "ledger/ledger_dinner.xls");
        ExcelDownloadFileName.put(STERILIZE, "ledger/ledger_sterilize.xls");
        ExcelDownloadFileName.put(TRAVEL, "ledger/ledger_travel.xls");
        ExcelDownloadFileName.put(WASTE, "ledger/ledger_waste.xls");

        ExcelDownloadChineseName.put(PURCHASE, "采购台账模板.xls");
        ExcelDownloadChineseName.put(ADDITIF, "添加剂台账模板.xls");
        ExcelDownloadChineseName.put(DINNER, "集体用餐台账模板.xls");
        ExcelDownloadChineseName.put(STERILIZE, "消毒记录台账模板.xls");
        ExcelDownloadChineseName.put(TRAVEL, "旅游接待台账模板.xls");
        ExcelDownloadChineseName.put(WASTE, "废弃物回收台账模板.xls");

    }

    /**
     * 根据KEY获取serviceName
     * @param key
     * @return
     */
    public static String getServiceNameByKey(String key) {
        if(StringUtils.isEmpty(key)) {
           return "";
        }

        return ExcelServiceNames.get(key);
    }

    /**
     * 根据KEY获取excel版本号
     * @param key
     * @return
     */
    public static String getVersionByKey(String key) {
        if(StringUtils.isEmpty(key)) {
            return "";
        }

        return ExcelVersions.get(key);
    }

    /**
     * 根据Key获取excel文件名
     * @param key
     * @return
     */
    public static String getDownloadFileName(String key) {
        if(StringUtils.isEmpty(key)) {
            return "";
        }

        return ExcelDownloadFileName.get(key);
    }

    /**
     * 根据KEY获取excel中文名
     * @param key
     * @return
     */
    public static String getDownloadChineseName(String key) {
        if(StringUtils.isEmpty(key)) {
            return "";
        }

        return ExcelDownloadChineseName.get(key);
    }
}
