package com.bdx.rainbow.service.ledger.readerImpl;

import com.bdx.rainbow.common.constant.LedgerTypeConstants;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.entity.ExcelRowDataResult;
import com.bdx.rainbow.entity.ReqNames;
import com.bdx.rainbow.service.common.impl.ExcelReaderServiceImpl;
import com.bdx.rainbow.service.ledger.IExcelDealDataService;
import com.bdx.rainbow.ygcf.entity.Ledger;
import com.bdx.rainbow.ygcf.entity.LedgerAdditif;
import com.bdx.rainbow.ygcf.entity.LedgerExample;
import com.bdx.rainbow.ygcf.mapper.LedgerAdditifMapper;
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
 * 添加剂台账上传
 * Created by fusj on 16/1/21.
 */
@Service("ledgerAdditifExcelService")
@Transactional(rollbackFor = {Exception.class})
public class LedgerAdditifExcelServiceImpl implements IExcelDealDataService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private LedgerAdditifMapper ledgerAdditifMapper;

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
        rowMap.put(ColNames.PRODUCT_NAME.name(), rowData.get(1));
        rowMap.put(ColNames.ADDITIF_NAME.name(), rowData.get(2));
        rowMap.put(ColNames.MATERIAL_QUANTITY.name(), rowData.get(3));
        rowMap.put(ColNames.MATERIAL_STANDARD.name(), rowData.get(4));
        rowMap.put(ColNames.ADDITIF_QUANTITY.name(), rowData.get(5));
        rowMap.put(ColNames.ADDITIF_STANDARD.name(), rowData.get(6));

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

        String additifDate = rowData.get(ColNames.DATE.name());
        if(StringUtils.isEmpty(additifDate)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[添加剂使用日期]为空", rowNo + 1)));
        }

        // 日期校验
        if(!DateUtil.isValidDate(additifDate)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[添加剂使用日期]格式不正确", rowNo + 1)));
        }

        String productName = rowData.get(ColNames.PRODUCT_NAME.name());
        if(StringUtils.isEmpty(productName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[加工产品名称]为空", rowNo + 1)));
        }

        String additifName = rowData.get(ColNames.ADDITIF_NAME.name());
        if(StringUtils.isEmpty(additifName)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[添加剂名称]为空", rowNo + 1)));
        }

        String materialAuantity = rowData.get(ColNames.MATERIAL_QUANTITY.name());
        if(StringUtils.isEmpty(materialAuantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[原料用量]为空", rowNo + 1)));
        }

        // 校验是否为数字
        if(!StringUtil.isNumber(materialAuantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[原料用量]不为数字", rowNo + 1)));
        }

        String meterialStandard = rowData.get(ColNames.MATERIAL_STANDARD.name());
        if(StringUtils.isEmpty(meterialStandard)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[原料规格(克/公斤/袋/瓶)]为空", rowNo + 1)));
        }

        String additifQuantity = rowData.get(ColNames.ADDITIF_QUANTITY.name());
        if(StringUtils.isEmpty(additifQuantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[添加剂实际用量]为空", rowNo + 1)));
        }

        // 校验是否为数字
        if(!StringUtil.isNumber(additifQuantity)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[添加剂实际用量]不为数字", rowNo + 1)));
        }

        String additifStandard = rowData.get(ColNames.ADDITIF_STANDARD.name());
        if(StringUtils.isEmpty(additifStandard)) {
            throw new BusinessException(new DefaultExceptionCode("500", String.format("第%s行:[添加剂规格(克/公斤/袋/瓶)]为空", rowNo + 1)));
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
        String ledgerType = LedgerTypeConstants.LEDGER_TYPE_ADDITIF;
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
            ledger.setLedgerType(LedgerTypeConstants.LEDGER_TYPE_ADDITIF);
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
        LedgerAdditif ledgerAdditif = new LedgerAdditif();

        ledgerAdditif.setLedgerId(ledgerId);
        ledgerAdditif.setProductName(rowData.get(ColNames.PRODUCT_NAME.name()));
        ledgerAdditif.setAdditifName(rowData.get(ColNames.ADDITIF_NAME.name()));
        ledgerAdditif.setMaterialQuantity(rowData.get(ColNames.MATERIAL_QUANTITY.name()));
        ledgerAdditif.setMaterialStandard(rowData.get(ColNames.MATERIAL_STANDARD.name()));
        ledgerAdditif.setAdditifQuantity(rowData.get(ColNames.ADDITIF_QUANTITY.name()));
        ledgerAdditif.setAdditifStandard(rowData.get(ColNames.ADDITIF_STANDARD.name()));

        ledgerAdditif.setIsDel("0");
        ledgerAdditif.setCreateDate(new Timestamp(new Date().getTime()));
        ledgerAdditif.setCreateUserid(1);

        ledgerAdditifMapper.insert(ledgerAdditif);

        dealResult.setFlag(true);
        dealResult.setData(rowData);

        return dealResult;
    }

    /**
     * 列名称定义
     *  除了DATE，表示添加剂使用台账日期，其他字段与数据库字段一致
     */
    enum ColNames {
        DATE, PRODUCT_NAME, ADDITIF_NAME, MATERIAL_QUANTITY,
        MATERIAL_STANDARD, ADDITIF_QUANTITY, ADDITIF_STANDARD
    }
}
