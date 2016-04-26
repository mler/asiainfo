package com.bdx.rainbow.service.ledger.readerImpl;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.ExcelRowDataResult;
import com.bdx.rainbow.entity.ReqNames;
import com.bdx.rainbow.service.ledger.IExcelDealDataService;
import com.bdx.rainbow.ygcf.entity.Ledger;
import com.bdx.rainbow.ygcf.entity.LedgerExample;
import com.bdx.rainbow.ygcf.entity.LedgerWaste;
import com.bdx.rainbow.ygcf.mapper.LedgerMapper;
import com.bdx.rainbow.ygcf.mapper.LedgerWasteMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 废弃物回收上传
 * Created by fusj on 16/1/22.
 */
@Service("ledgerWatseExcelService")
@Transactional(rollbackFor = {Exception.class})
public class LedgerWasteExcelServiceImpl implements IExcelDealDataService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerWasteMapper ledgerWasteMapper;

    /**
     * excel数据解析
     * @param rowNo    行号
     * @param rowData  行数据
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    @Override
    public Map<String, String> transExcelRowData(int rowNo, List<String> rowData) throws SystemException, BusinessException {
        Map<String, String> rowMap = new HashMap<String, String>();

        rowMap.put(ColNames.DATE.name(), rowData.get(0));
        rowMap.put(ColNames.QUANTITY.name(), rowData.get(1));
        rowMap.put(ColNames.PURPOSE.name(), rowData.get(2));
        rowMap.put(ColNames.OPERATER.name(), rowData.get(3));
        rowMap.put(ColNames.CHECKER.name(), rowData.get(4));

        return rowMap;
    }

    /**
     * excel数据校验
     * @param rowNo    行号
     * @param rowData  行数据
     * @param request
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    @Override
    public ExcelRowDataResult validateExcelRowData(int rowNo, Map<String, String> rowData, HttpServletRequest request) throws SystemException, BusinessException {
        ExcelRowDataResult validateResult = new ExcelRowDataResult();

        String date = rowData.get(ColNames.DATE.name());
        if(StringUtils.isEmpty(date)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[废弃物回收台账日期]为空", rowNo + 1)));
        }

        if(!DateUtil.isValidDate(date)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[废弃物回收台账日期]格式不正确", rowNo + 1)));
        }

        String quantity = rowData.get(ColNames.QUANTITY.name());
        if(StringUtils.isEmpty(quantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[泔水数量(公斤)]为空", rowNo + 1)));
        }

        if(!StringUtil.isNumber(quantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[泔水数量(公斤)]不为数字", rowNo + 1)));
        }

        String purpose = rowData.get(ColNames.PURPOSE.name());
        if(StringUtils.isEmpty(purpose)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[回收用途]为空", rowNo + 1)));
        }

        String operater = rowData.get(ColNames.OPERATER.name());
        if(StringUtils.isEmpty(operater)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[回收人]为空", rowNo + 1)));
        }

        String checker = rowData.get(ColNames.CHECKER.name());
        if(StringUtils.isEmpty(checker)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[监督人]为空", rowNo + 1)));
        }

        validateResult.setFlag(true);
        validateResult.setData(rowData);

        return validateResult;
    }

    /**
     * excel数据处理
     * @param rowNo    行号
     * @param rowData  行数据
     * @param request
     * @return
     * @throws SystemException
     * @throws BusinessException
     */
    @Override
    public ExcelRowDataResult dealExcelRowData(int rowNo, Map<String, String> rowData, HttpServletRequest request) throws SystemException, BusinessException {
        ExcelRowDataResult dealResult = new ExcelRowDataResult();

        // 根据台账类型、企业编号、台账日期查询，如果没有，则新建一条
        String ledgerType = LedgerTypeConstants.LEDGER_TYPE_WASTE;
        int enterpriseId = 1;

        LedgerExample ledgerExample = new LedgerExample();
        ledgerExample.createCriteria().andLedgerTypeEqualTo(ledgerType)
                .andEnterpriseIdEqualTo(enterpriseId)
                .andLedgerDateEqualTo(rowData.get(ColNames.DATE.name()));

        int ledgerId;

        List<Ledger> ledgerList = ledgerMapper.selectByExample(ledgerExample);
        if(CollectionUtils.isEmpty(ledgerList)) {
            Ledger ledger = new Ledger();

            ledger.setEnterpriseId(Integer.parseInt(rowData.get(ReqNames.ENTERPRISE_ID.name())));
            ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_WASTE);
            ledger.setLedgerDate(rowData.get(ColNames.DATE.name()));
            ledger.setIsUpload("1");
            ledger.setIsDel("0");
            ledger.setCreateDate(new Timestamp(new Date().getTime()));
            ledger.setCreateUserid(1);

            ledgerId = ledgerMapper.insert(ledger);
        } else {
            ledgerId = ledgerList.get(0).getLedgerId();
        }

        // 保存台账明细
        LedgerWaste ledgerWaste = new LedgerWaste();

        ledgerWaste.setLedgerId(ledgerId);
        ledgerWaste.setQuantity(rowData.get(ColNames.QUANTITY.name()));
        ledgerWaste.setPurpose(rowData.get(ColNames.PURPOSE.name()));
        ledgerWaste.setOperater(rowData.get(ColNames.OPERATER.name()));
        ledgerWaste.setChecker(rowData.get(ColNames.CHECKER.name()));

        ledgerWaste.setIsDel("0");
        ledgerWaste.setCreateDate(new Timestamp(new Date().getTime()));
        ledgerWaste.setCreateUserid(1);

        ledgerWasteMapper.insert(ledgerWaste);

        dealResult.setFlag(true);
        dealResult.setData(rowData);

        return dealResult;
    }

    /**
     * 废弃物回收台账日期|泔水数量(公斤)|回收用途|回收人|监督人
     */
    enum ColNames {
        DATE, QUANTITY, PURPOSE, OPERATER, CHECKER
    }
}
