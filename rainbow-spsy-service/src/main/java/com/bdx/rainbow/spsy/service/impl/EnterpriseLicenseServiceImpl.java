package com.bdx.rainbow.spsy.service.impl;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseLicense;
import com.bdx.rainbow.basic.dubbo.bean.DubboLicense;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.spsy.common.ConsumerUtil;
import com.bdx.rainbow.spsy.service.IEnterpriseLicenseService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by core on 16/3/10.
 */
public class EnterpriseLicenseServiceImpl implements IEnterpriseLicenseService {
    private final static Logger log= LoggerFactory.getLogger(EnterpriseLicenseServiceImpl.class);
    @Autowired
    private IEnterpriseDubboService enterpriseDubboService ;
    @Override
    public Map<String, Object> getLicenses(DubboEnterpriseLicense condition, Integer start, Integer limit)throws Exception {
        List<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap= enterpriseDubboService.getMoreEnterpriseLicense(condition, start, limit);
        return resultMap;
    }

    @Override
    public HSSFWorkbook ExcelOut(String title,String[] headers,List<DubboEnterpriseLicense> resultLists, String pattern) {
        return exportExcel(title,headers,resultLists,pattern);
    }

    /**
     *
     * @param title 表格标题名
     * @param headers 表格属性列名
     * @param resultLists 表格数据
     * @param pattern 时间格式,默认为"yyyy-MM-dd"
     */
    private HSSFWorkbook exportExcel(String title,String[] headers, List<DubboEnterpriseLicense> resultLists, String pattern){
       if (resultLists==null){
           return null;
       }
        if (pattern==null&& StringUtils.isEmpty(pattern)){
           pattern="yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        // 遍历集合数据，产生数据行
        for (int i = 0; i <resultLists.size() ; i++) {
                row = sheet.createRow(i+1);
                int index=0;
//                HSSFCell cell = row.createCell(j);
//                row.createCell(index++).setCellStyle(style2);
                DubboEnterpriseLicense info=resultLists.get(i);
                row.createCell(index++).setCellValue(info.getEnterpriseName());
                row.createCell(index++).setCellValue(info.getOrganizationCode());
                row.createCell(index++).setCellValue(info.getLegalPerson());
                row.createCell(index++).setCellValue(info.getLegalPersonPhone());
                String type=info.getLicenseType();
                if ("001".equals(type)){
                    row.createCell(index++).setCellValue("生产");
                }else if("002".equals(type)){
                    row.createCell(index++).setCellValue("经营");
                }else if("003".equals(type)){
                    row.createCell(index++).setCellValue("销售");
                }else if("004".equals(type)){
                    row.createCell(index++).setCellValue("流通");
                }else {
                    row.createCell(index++).setCellValue(type);
                }

                row.createCell(index++).setCellValue(info.getLicenseCode());
                if (info.getLicenseTime()!=null){
                    row.createCell(index++).setCellValue(sdf.format(info.getLicenseTime()));
                }else {
                    row.createCell(index++).setCellValue("");
                }
                if(info.getInvalidDate()!=null){
                    Long time=(System.currentTimeMillis()-info.getInvalidDate().getTime())/(1000*3600*24);
                    if (time>0){
                        row.createCell(index++).setCellValue("已过期");
                    }else if(time<=0){
                        row.createCell(index++).setCellValue("正常");
                    }else{
                        row.createCell(index++).setCellValue("1个月过期");
                    }
                }else{
                    row.createCell(index++).setCellValue("");
                }


        }
        return workbook;
    }
}
