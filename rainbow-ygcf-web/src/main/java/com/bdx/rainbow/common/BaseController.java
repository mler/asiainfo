package com.bdx.rainbow.common;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.exception.BusinessException;
import com.bdx.rainbow.common.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller基类
 * Created by fusj on 16/3/3.
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected IEnterpriseDubboService enterpriseService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        SimpleDateFormat datetimeFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        datetimeFormat.setLenient(false);

        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
                dateFormat, true));
        binder.registerCustomEditor(java.sql.Timestamp.class,
                new CustomTimestampEditor(datetimeFormat, true));
    }

    /**
     * ajax请求异常返回
     * @param ex
     * @return
     */
    protected ResultBean ajaxException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        ResultBean resultBean = new ResultBean(false);

        if(ex instanceof SystemException) {
            resultBean.setMsg(((SystemException) ex).getErrorMsg());
        } else if (ex instanceof BusinessException) {
            resultBean.setMsg(((BusinessException) ex).getErrorMsg());
        } else {
            resultBean.setMsg(ex.getMessage());
        }

        return resultBean;
    }

    /**
     * 根据企业名称查询企业列表
     * @param enterpriseName
     * @return
     * @throws Exception
     */
    protected List<DubboEnterpriseInfo> dubboQueryEnterpriseByName(String enterpriseName) throws Exception {
        List<DubboEnterpriseInfo> list = new ArrayList<>();

        // 企业名称条件不为空
        if(StringUtil.isNotEmptyObject(enterpriseName)) {
            DubboEnterpriseInfo condition = new DubboEnterpriseInfo();
            condition.setEnterpriseName(enterpriseName);

            Map<String, Object> map = enterpriseService.getEnterpriseInfos(condition, -1, 0);
            list = (List<DubboEnterpriseInfo>) map.get("list");
        }

        return list;
    }

    /**
     * 根据企业ID列表查询企业数据
     * @param idList
     * @return
     * @throws Exception
     */
    protected Map<Integer, DubboEnterpriseInfo> dubboQueryEnterpriseInfoByIds(List<Integer> idList) throws Exception {
        Map<Integer, DubboEnterpriseInfo> map = new HashMap<>();

        if(CollectionUtils.isEmpty(idList)) {
            return map;
        }

        List<DubboEnterpriseInfo> enterpriseList = enterpriseService.getDubboEnterpriseInfoByIds(new ArrayList<Integer>(idList));

        for (DubboEnterpriseInfo enterpriseInfo : enterpriseList) {
            map.put(enterpriseInfo.getEnterpriseId(), enterpriseInfo);
        }

        return map;
    }

    /**
     * 企业列表转换成企业ID查询条件
     * @param enterpriseInfoList
     * @return
     */
    protected List<Integer> getEnterpriseIdList(List<DubboEnterpriseInfo> enterpriseInfoList) {
        List<Integer> enterpriseIdList = new ArrayList<>();
        for (DubboEnterpriseInfo enterpriseInfo : enterpriseInfoList) {
            enterpriseIdList.add(enterpriseInfo.getEnterpriseId());
        }

        return enterpriseIdList;
    }
}
