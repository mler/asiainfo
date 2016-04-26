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
import com.bdx.rainbow.ygcf.entity.LedgerDinner;
import com.bdx.rainbow.ygcf.entity.LedgerExample;
import com.bdx.rainbow.ygcf.mapper.LedgerDinnerMapper;
import com.bdx.rainbow.ygcf.mapper.LedgerMapper;
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
 * 集体用餐台账上传
 * Created by fusj on 16/1/21.
 */
@Service("ledgerDinnerExcelService")
@Transactional(rollbackFor = {Exception.class})
public class LedgerDinnerExcelServiceImpl implements IExcelDealDataService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerDinnerMapper ledgerDinnerMapper;

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
        rowMap.put(ColNames.DINNER_NAME.name(), rowData.get(1));
        rowMap.put(ColNames.DINNER_NUM.name(), rowData.get(2));
        rowMap.put(ColNames.PHONE.name(), rowData.get(3));
        rowMap.put(ColNames.MATTERS.name(), rowData.get(4));
        rowMap.put(ColNames.DINNER_TIME.name(), rowData.get(5));

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

        String dinnerDate = rowData.get(ColNames.DATE.name());
        if(StringUtils.isEmpty(dinnerDate)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[集体用餐台账日期]为空", rowNo + 1)));
        }

        // 日期校验
        if(!DateUtil.isValidDate(dinnerDate)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[集体用餐台账日期]格式不正确", rowNo + 1)));
        }

        String dinnerName = rowData.get(ColNames.DINNER_NAME.name());
        if(StringUtils.isEmpty(dinnerName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[举办者名称]为空", rowNo + 1)));
        }

        String dinnerNum = rowData.get(ColNames.DINNER_NUM.name());
        if(StringUtils.isEmpty(dinnerNum)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[就餐人数]为空", rowNo + 1)));
        }

        // 校验是否为数字
        if(!StringUtil.isNumber(dinnerNum)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[就餐人数]不为数字", rowNo + 1)));
        }

        String phone = rowData.get(ColNames.PHONE.name());
        if(StringUtils.isEmpty(phone)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[联系电话]为空", rowNo + 1)));
        }

        String matters = rowData.get(ColNames.MATTERS.name());
        if(StringUtils.isEmpty(matters)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[举办事宜]为空", rowNo + 1)));
        }

        String dinnerTime = rowData.get(ColNames.DINNER_TIME.name());
        if(StringUtils.isEmpty(dinnerTime)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[就餐时间]为空", rowNo + 1)));
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
        String ledgerType = LedgerTypeConstants.LEDGER_TYPE_DINNER;
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
            ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_DINNER);
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
        LedgerDinner ledgerDinner = new LedgerDinner();

        ledgerDinner.setLedgerId(ledgerId);
        ledgerDinner.setDinnerName(rowData.get(ColNames.DINNER_NAME.name()));
        ledgerDinner.setDinnerNum(rowData.get(ColNames.DINNER_NUM.name()));
        ledgerDinner.setPhone(rowData.get(ColNames.PHONE.name()));
        ledgerDinner.setMatters(rowData.get(ColNames.MATTERS.name()));
        ledgerDinner.setDinnerTime(rowData.get(ColNames.DINNER_TIME.name()));

        ledgerDinner.setIsDel("0");
        ledgerDinner.setCreateDate(new Timestamp(new Date().getTime()));
        ledgerDinner.setCreateUserid(1);

        ledgerDinnerMapper.insert(ledgerDinner);


        dealResult.setFlag(true);
        dealResult.setData(rowData);

        return dealResult;
    }

    enum ColNames {
        DATE, DINNER_NAME, DINNER_NUM, PHONE, MATTERS, DINNER_TIME
    }
}
