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
import com.bdx.rainbow.ygcf.entity.LedgerTravel;
import com.bdx.rainbow.ygcf.mapper.LedgerMapper;
import com.bdx.rainbow.ygcf.mapper.LedgerTravelMapper;
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
 * 旅游接待台账上传
 * Created by fusj on 16/1/22.
 */
@Service("ledgerTravelExcelService")
@Transactional(rollbackFor = {Exception.class})
public class LedgerTravelExcelServiceImpl implements IExcelDealDataService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerTravelMapper ledgerTravelMapper;

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
        rowMap.put(ColNames.TRAVEL_NAME.name(), rowData.get(1));
        rowMap.put(ColNames.GROUP_NAME.name(), rowData.get(2));
        rowMap.put(ColNames.SOURCE_FROM.name(), rowData.get(3));
        rowMap.put(ColNames.PEOPLE_NUM.name(), rowData.get(4));
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

        String date = rowData.get(ColNames.DATE.name());
        if(StringUtils.isEmpty(date)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[接待旅游台账日期]为空", rowNo + 1)));
        }

        if(!DateUtil.isValidDate(date)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[接待旅游台账日期]格式不正确", rowNo + 1)));
        }

        String travelName = rowData.get(ColNames.TRAVEL_NAME.name());
        if(StringUtils.isEmpty(travelName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[旅行社名称]为空", rowNo + 1)));
        }

        String groupName = rowData.get(ColNames.GROUP_NAME.name());
        if(StringUtils.isEmpty(groupName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[就餐团队名称]为空", rowNo + 1)));
        }

        String sourceFrom = rowData.get(ColNames.SOURCE_FROM.name());
        if(StringUtils.isEmpty(sourceFrom)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[来源地]为空", rowNo + 1)));
        }

        String peopleNum = rowData.get(ColNames.PEOPLE_NUM.name());
        if(StringUtils.isEmpty(peopleNum)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[就餐人数]为空", rowNo + 1)));
        }

        if(!StringUtil.isNumber(peopleNum)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[就餐人数]不为数字", rowNo + 1)));
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
        String ledgerType = LedgerTypeConstants.LEDGER_TYPE_TRAVEL;
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
            ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_TRAVEL);
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
        LedgerTravel ledgerTravel = new LedgerTravel();

        ledgerTravel.setLedgerId(ledgerId);
        ledgerTravel.setTravelName(rowData.get(ColNames.TRAVEL_NAME.name()));
        ledgerTravel.setGroupName(rowData.get(ColNames.GROUP_NAME.name()));
        ledgerTravel.setSourceFrom(rowData.get(ColNames.SOURCE_FROM.name()));
        ledgerTravel.setPeopleNum(rowData.get(ColNames.PEOPLE_NUM.name()));
        ledgerTravel.setDinnerTime(rowData.get(ColNames.DINNER_TIME.name()));

        ledgerTravel.setIsDel("0");
        ledgerTravel.setCreateDate(new Timestamp(new Date().getTime()));
        ledgerTravel.setCreateUserid(1);

        ledgerTravelMapper.insert(ledgerTravel);

        dealResult.setFlag(true);
        dealResult.setData(rowData);

        return dealResult;
    }

    /**
     * 接待旅游台账日期|旅行社名称|就餐团队名称|来源地|就餐人数|就餐时间
     */
    enum  ColNames {
        DATE, TRAVEL_NAME, GROUP_NAME, SOURCE_FROM, PEOPLE_NUM, DINNER_TIME
    }
}
