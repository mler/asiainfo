package com.bdx.rainbow.service.ledger;

import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.entity.ExcelRowDataResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * excel数据读取业务类需实现此接口
 * Created by fusj on 16/1/20.
 */
public interface IExcelDealDataService {

    /**
     * 对excel一行数据转换
     * @param rowNo    行号
     * @param rowData  行数据
     * @return
     */
    Map<String, String> transExcelRowData(int rowNo, List<String> rowData) throws SystemException, BusinessException;

    /**
     * 对excel一行数据校验
     * @param rowNo    行号
     * @param rowData  行数据
     * @param request
     * @return
     */
    ExcelRowDataResult validateExcelRowData(int rowNo, Map<String, String> rowData, HttpServletRequest request) throws SystemException, BusinessException;

    /**
     * 对excel一行数据进行处理
     * @param rowNo    行号
     * @param rowData  行数据
     * @param request
     * @return
     */
    ExcelRowDataResult dealExcelRowData(int rowNo, Map<String, String> rowData, HttpServletRequest request) throws SystemException, BusinessException;
}
