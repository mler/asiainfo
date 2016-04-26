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
import com.bdx.rainbow.ygcf.entity.LedgerSterilize;
import com.bdx.rainbow.ygcf.mapper.LedgerMapper;
import com.bdx.rainbow.ygcf.mapper.LedgerSterilizeMapper;
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
 * 消毒剂台账上传
 * Created by fusj on 16/1/21.
 */
@Service("ledgerSterilizeExcelService")
@Transactional(rollbackFor = {Exception.class})
public class LedgerSterilizeExcelServiceImpl implements IExcelDealDataService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerSterilizeMapper ledgerSterilizeMapper;

    /**
     * excel数据解析
     *  消毒记录台账日期|消毒内容|消毒数量|消毒内容规格(双/把/个/台)|消毒方法|消毒时间|消毒人|监督人
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
        rowMap.put(ColNames.ITEM_NAME.name(), rowData.get(1));
        rowMap.put(ColNames.QUANTITY.name(), rowData.get(2));
        rowMap.put(ColNames.STANDARD.name(), rowData.get(3));
        rowMap.put(ColNames.METHOD.name(), rowData.get(4));
        rowMap.put(ColNames.DO_TIME.name(), rowData.get(5));
        rowMap.put(ColNames.OPERATER.name(), rowData.get(6));
        rowMap.put(ColNames.CHECKER.name(), rowData.get(7));

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
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒记录台账日期]为空", rowNo + 1)));
        }

        if(!DateUtil.isValidDate(date)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒记录台账日期]格式不正确", rowNo + 1)));
        }

        String itemName = rowData.get(ColNames.ITEM_NAME.name());
        if(StringUtils.isEmpty(itemName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒内容]为空", rowNo + 1)));
        }

        String quantity = rowData.get(ColNames.QUANTITY.name());
        if(StringUtils.isEmpty(quantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒数量]为空", rowNo + 1)));
        }

        if(!StringUtil.isNumber(quantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒数量]不为数字", rowNo + 1)));
        }

        String standard = rowData.get(ColNames.STANDARD.name());
        if(StringUtils.isEmpty(standard)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒内容规格(双/把/个/台)]为空", rowNo + 1)));
        }

        String method = rowData.get(ColNames.METHOD.name());
        if(StringUtils.isEmpty(method)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒方法]为空", rowNo + 1)));
        }

        String doTime = rowData.get(ColNames.DO_TIME.name());
        if(StringUtils.isEmpty(doTime)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒时间]为空", rowNo + 1)));
        }

        String operater = rowData.get(ColNames.OPERATER.name());
        if(StringUtils.isEmpty(operater)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[消毒人]为空", rowNo + 1)));
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
        String ledgerType = LedgerTypeConstants.LEDGER_TYPE_STERILIZE;
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
            ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_STERILIZE);
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
        LedgerSterilize ledgerSterilize = new LedgerSterilize();

        ledgerSterilize.setLedgerId(ledgerId);
        ledgerSterilize.setItemName(rowData.get(ColNames.ITEM_NAME.name()));
        ledgerSterilize.setQuantity(rowData.get(ColNames.QUANTITY.name()));
        ledgerSterilize.setStandard(rowData.get(ColNames.STANDARD.name()));
        ledgerSterilize.setMethod(rowData.get(ColNames.METHOD.name()));
        ledgerSterilize.setDoTime(rowData.get(ColNames.DO_TIME.name()));
        ledgerSterilize.setOperater(rowData.get(ColNames.OPERATER.name()));
        ledgerSterilize.setChecker(rowData.get(ColNames.CHECKER.name()));

        ledgerSterilize.setIsDel("0");
        ledgerSterilize.setCreateDate(new Timestamp(new Date().getTime()));
        ledgerSterilize.setCreateUserid(1);

        ledgerSterilizeMapper.insert(ledgerSterilize);

        dealResult.setFlag(true);
        dealResult.setData(rowData);

        return dealResult;
    }

    /**
     * 消毒记录台账日期|消毒内容|消毒数量|消毒内容规格(双/把/个/台)|消毒方法|消毒时间|消毒人|监督人
     */
    enum ColNames {
        DATE, ITEM_NAME, QUANTITY, STANDARD, METHOD, DO_TIME, OPERATER, CHECKER
    }
}
