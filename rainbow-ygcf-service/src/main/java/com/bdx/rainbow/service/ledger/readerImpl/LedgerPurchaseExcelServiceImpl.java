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
import com.bdx.rainbow.ygcf.entity.LedgerPurchase;
import com.bdx.rainbow.ygcf.mapper.LedgerMapper;
import com.bdx.rainbow.ygcf.mapper.LedgerPurchaseMapper;
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
 * 采购台账上传
 * Created by fusj on 16/1/20.
 */
@Service("ledgerPurchaseExcelService")
//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
@Transactional(rollbackFor = {Exception.class})
public class LedgerPurchaseExcelServiceImpl implements IExcelDealDataService {

    @Autowired
    private LedgerPurchaseMapper ledgerPurchaseMapper;

    @Autowired
    private LedgerMapper ledgerMapper;

    /**
     * Excel行数据转换
     *  采购台账日期|产品名称|数量|规格(公斤/袋/瓶)|保质期（天）|供货商|生产厂家|生产批号
     * @param rowNo
     * @param rowData
     * @return
     */
    @Override
    public Map<String, String> transExcelRowData(int rowNo, List<String> rowData) throws SystemException, BusinessException {
        Map<String, String> rowMap = new HashMap<String, String>();

        rowMap.put(ColNames.DATE.name(), rowData.get(0));
        rowMap.put(ColNames.PRODUCT_NAME.name(), rowData.get(1));
        rowMap.put(ColNames.QUANTITY.name(), rowData.get(2));
        rowMap.put(ColNames.STANDARD.name(), rowData.get(3));
        rowMap.put(ColNames.VALIDITY.name(), rowData.get(4));
        rowMap.put(ColNames.PROVIDER.name(), rowData.get(5));
        rowMap.put(ColNames.PRODUCTOR.name(), rowData.get(6));
        rowMap.put(ColNames.PRODUCT_NO.name(), rowData.get(7));

        return rowMap;
    }

    /**
     * Excel行数据校验
     * @param rowNo    行号
     * @param rowData  行数据
     * @param request
     * @return
     */
    @Override
    public ExcelRowDataResult validateExcelRowData(int rowNo, Map<String, String> rowData, HttpServletRequest request) throws SystemException, BusinessException {
        ExcelRowDataResult validateResult = new ExcelRowDataResult();

        String purchaseDate = rowData.get(ColNames.DATE.name());
        if(StringUtils.isEmpty(purchaseDate)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[采购日期]为空", rowNo + 1)));
        }

        if(!DateUtil.isValidDate(purchaseDate)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[采购日期]格式不正确", rowNo + 1)));
        }

        String productName = rowData.get(ColNames.PRODUCT_NAME.name());
        if(StringUtils.isEmpty(productName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[产品名称]为空", rowNo + 1)));
        }

        String quantity = rowData.get(ColNames.QUANTITY.name());
        if(StringUtils.isEmpty(quantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[数量]为空", rowNo + 1)));
        }
        // 校验是否为数字
        if(!StringUtil.isNumber(quantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[数量]不为数字", rowNo + 1)));
        }

        String standard = rowData.get(ColNames.STANDARD.name());
        if(StringUtils.isEmpty(standard)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[规格(公斤/袋/瓶)]为空", rowNo + 1)));
        }

        String validity = rowData.get(ColNames.VALIDITY.name());
        if(StringUtils.isEmpty(validity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[保质期（天）]为空", rowNo + 1)));
        }

        String provider = rowData.get(ColNames.PROVIDER.name());
        if(StringUtils.isEmpty(provider)) {
            rowData.put(ColNames.PROVIDER.name(), "");
        }

        String productor = rowData.get(ColNames.PRODUCTOR.name());
        if(StringUtils.isEmpty(productor)) {
            rowData.put(ColNames.PRODUCTOR.name(), "");
        }

        String productNo = rowData.get(ColNames.PRODUCT_NO.name());
        if(StringUtils.isEmpty(productNo)) {
            rowData.put(ColNames.PRODUCT_NO.name(), "");
        }

        validateResult.setFlag(true);
        validateResult.setData(rowData);

        return validateResult;
    }

    /**
     * Excel导入数据处理
     *
     * @param rowData
     * @param request
     * @return
     */
    @Override
    public ExcelRowDataResult dealExcelRowData(int rowNo, Map<String, String> rowData, HttpServletRequest request) throws SystemException, BusinessException {
        ExcelRowDataResult dealResult = new ExcelRowDataResult();

        // 根据台账类型、企业编号、台账日期查询，如果没有，则新建一条
        String ledgerType = LedgerTypeConstants.LEDGER_TYPE_PURCHASE;
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
            ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_PURCHASE);
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
        LedgerPurchase ledgerPurchase = new LedgerPurchase();

        ledgerPurchase.setLedgerId(ledgerId);
        // 产品名称
        ledgerPurchase.setProductName(rowData.get(ColNames.PRODUCT_NAME.name()));
        // 数量
        ledgerPurchase.setQuantity(rowData.get(ColNames.QUANTITY.name()));
        // 规格
        ledgerPurchase.setStandard(rowData.get(ColNames.STANDARD.name()));
        // 保质期
        ledgerPurchase.setValidity(rowData.get(ColNames.VALIDITY.name()));
        // 供货商
        ledgerPurchase.setProvider(rowData.get(ColNames.PROVIDER.name()));
        // 生产厂家
        ledgerPurchase.setProductor(rowData.get(ColNames.PRODUCTOR.name()));
        // 生产批号
        ledgerPurchase.setProductNo(rowData.get(ColNames.PRODUCT_NO.name()));

        ledgerPurchase.setIsDel("0");
        ledgerPurchase.setCreateDate(new Timestamp(new Date().getTime()));
        ledgerPurchase.setCreateUserid(1);

        ledgerPurchaseMapper.insert(ledgerPurchase);

        dealResult.setFlag(true);
        dealResult.setData(rowData);

        return dealResult;
    }

    /**
     * 列名称定义
     *  除了Date，表示采购日期，其他字段与数据库字段一致
     */
    enum ColNames {
        DATE, PRODUCT_NAME, QUANTITY,
        STANDARD, VALIDITY, PROVIDER,
        PRODUCTOR, PRODUCT_NO
    }

}
