package com.bdx.rainbow.service.common.impl;

import com.bdx.rainbow.common.mapper.ExcelInfoMapper;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.ExcelRowDataResult;
import com.bdx.rainbow.entity.ReqNames;
import com.bdx.rainbow.service.common.IExcelReaderService;
import com.bdx.rainbow.service.ledger.IExcelDealDataService;
import com.bdx.rainbow.util.SpringBeanFactory;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Excel文件解析接口实现
 * Created by fusj on 16/1/20.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ExcelReaderServiceImpl implements IExcelReaderService {

    /**
     * excel数据读取
     * @param inputStream
     * @param param
     * @return
     */
    @Override
    public List<ExcelRowDataResult> excelRead(InputStream inputStream, Map<String, String> param, HttpServletRequest request) throws SystemException, BusinessException {
        String key = param.get("serviceKey");
        // 企业编号
        String enterpriseId = param.get("enterpriseId");

        if(StringUtils.isEmpty(enterpriseId)) {
            throw new BusinessException(new DefaultExceptionCode("500", "您不是企业用户或企业编号为空"));
        }

        if(StringUtils.isEmpty(key)) {
            throw new BusinessException(new DefaultExceptionCode("500", "未设置key值"));
        }

        // Excel列数
        String colTotal = param.get("colTotal");
        // Excel数据开始行
        String rowStart = param.get("rowStart");

        if(StringUtils.isEmpty(colTotal)) {
            throw new BusinessException(new DefaultExceptionCode("500", "未设置数据总列数"));
        }

        if(StringUtils.isEmpty(rowStart)) {
            throw new BusinessException(new DefaultExceptionCode("500", "未设置数据开始行"));
        }
//
//        if(null == file || "".equals(file.getName())) {
//            throw new BusinessException(new DefaultExceptionCode("500", "上传的文件为空"));
//        }

        // 业务类名称
        String serviceName = ExcelInfoMapper.getServiceNameByKey(key);
        // 版本号
        String versionNo = ExcelInfoMapper.getVersionByKey(key);

        if(StringUtils.isEmpty(serviceName)) {
            throw new BusinessException(new DefaultExceptionCode("500", "未设置业务处理类"));
        }

        if(StringUtils.isEmpty(versionNo)) {
            throw new BusinessException(new DefaultExceptionCode("500", "未设置Excel版本号"));
        }

        // 获取业务处理service
        IExcelDealDataService iExcelDealDataService = (IExcelDealDataService) SpringBeanFactory.getSpringBean(serviceName);

        Workbook workbook;
        try {
            workbook = Workbook.getWorkbook(inputStream);
        } catch (IOException e) {
            throw new SystemException(new DefaultExceptionCode("500", "文件IO异常"), e);
        } catch (BiffException e) {
            throw new SystemException(new DefaultExceptionCode("500", "上传的文件不是标准的Excel文件"), e);
        }

        Sheet sheet = workbook.getSheet(0);

        // 版本号
        String xlsVersion = sheet.getRow(0)[0].getContents();
        if(StringUtils.isEmpty(xlsVersion) || !versionNo.equals(xlsVersion)) {
            throw new BusinessException(new DefaultExceptionCode("500", "上传的Excel版本号不正确"));
        }

        // excel的行数
        int totalRow = sheet.getRows();
        int startRow = Integer.parseInt(rowStart);
        int totalCol = Integer.parseInt(colTotal);

        // 读取数据病处理数据
        for(int i = startRow; i < totalRow; i++) {
            Cell[] row = sheet.getRow(i);

            if(row.length == 0) {
                continue;
            }

            List<String> rowData = new ArrayList<String>();
            for(int j = 0; j < totalCol; j++) {
                if(j >= row.length) {
                    rowData.add("");
                } else {
                    rowData.add(row[j].getContents());
                }
            }

            // 数据转换
            Map<String, String> dataMap = iExcelDealDataService.transExcelRowData(i, rowData);

            // 数据校验
            ExcelRowDataResult validateResult = iExcelDealDataService.validateExcelRowData(i, dataMap, request);
            if(!validateResult.getFlag()) {
                throw new BusinessException(new DefaultExceptionCode("500", validateResult.getMsg()));
            }

            // 添加企业编号
            dataMap.put(ReqNames.ENTERPRISE_ID.name(), enterpriseId);

            // 保存数据
            ExcelRowDataResult dealResult = iExcelDealDataService.dealExcelRowData(i, dataMap, request);
            if(!dealResult.getFlag()) {
                throw new BusinessException(new DefaultExceptionCode("500", dealResult.getMsg()));
            }
        }

        workbook.close();

        return new ArrayList<ExcelRowDataResult>();
    }
}
