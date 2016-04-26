package com.bdx.rainbow.controller;

import com.bdx.rainbow.common.CustomTimestampEditor;
import com.bdx.rainbow.common.bean.ResultBean;
import com.bdx.rainbow.common.exception.SystemException;
import com.bdx.rainbow.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;

/**
 * controller基类
 * Created by fusj on 16/3/3.
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

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

}
