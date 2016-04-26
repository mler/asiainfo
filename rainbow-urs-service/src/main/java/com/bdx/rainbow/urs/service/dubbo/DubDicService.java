package com.bdx.rainbow.urs.service.dubbo;

import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.bdx.rainbow.common.exception.BaseException;
import com.bdx.rainbow.common.exception.DefaultExceptionCode;
import com.bdx.rainbow.entity.urs.SysViews;
import com.bdx.rainbow.urs.common.BeanCopyUtil;
import com.bdx.rainbow.urs.dubbo.IDubDicService;
import com.bdx.rainbow.urs.entity.ParamDetail;
import com.bdx.rainbow.urs.service.ISysViewService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by core on 16/2/3.
 */
@Service
public class DubDicService implements IDubDicService ,GenericService {
    private static final Logger log = LoggerFactory.getLogger(DubDicService.class);
    @Autowired
    private ISysViewService sysViewService;

    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args)
            throws GenericException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ParamDetail> findCacheByKey(String key)  throws Exception {

        if (StringUtils.isBlank(key)){
            throw new  com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888","key值不能为空！"));
        }
        try {
            List<SysViews> views = sysViewService.findCacheByKey(key);
            List<ParamDetail> paramDetails = new ArrayList<ParamDetail>();
            if (views != null && views.size() > 0) {
                for (int i = 0; i < views.size(); i++) {
                    ParamDetail detail = new ParamDetail();
                    BeanCopyUtil.copyProperties(views.get(i), detail);
                    paramDetails.add(detail);
                }

            } else {
                log.warn("没有找到对应的参数[" + key + "]的数据列表");
            }
            return paramDetails;
        }catch (Exception e){
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode( e1.getErrorCode(), e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                throw new Exception("系统异常！");
            }
        }

    }

    @Override
    public String getParamName(String key, String value, String parent) throws Exception{
        if (StringUtils.isBlank(key)){
            throw new  com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888","key值不能为空！"));
        }
        if (StringUtils.isBlank(value)){
            throw new  com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888","value值不能为空！"));
        }
        SysViews vc=null;
        try{
            if(parent!=null&&StringUtils.isNotBlank(parent)){
                vc=sysViewService.findCacheByKeyAndValueAndPvalue(key,value,parent);
            }else {
                vc = sysViewService.findCacheByKeyAndValue(key, value);
            }
            if (vc==null){
                log.warn("没有找到对应的参数[key:"+key+";value:"+value+";parent:"+parent+"]的数据列表");
                return null;
            }
        }catch (Exception e){
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode( e1.getErrorCode(), e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                throw new Exception("系统异常！");
            }
        }

        return vc.getpDesc();
    }

    @Override
    public List<ParamDetail> findCacheByKeyAndPValue(String key, String pValue) throws Exception {
        if (StringUtils.isBlank(key)){
            throw new  com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode("8888","key值不能为空！"));
        }
        try {
            List<SysViews> views =new ArrayList<SysViews>();
            if (pValue!=null&&StringUtils.isNotBlank(pValue)){
                views = sysViewService.findCacheByKey(key,pValue);
            }else{
                views = sysViewService.findCacheByKey(key);
            }
            List<ParamDetail> paramDetails = new ArrayList<ParamDetail>();
            if (views != null && views.size() > 0) {
                for (int i = 0; i < views.size(); i++) {
                    ParamDetail detail = new ParamDetail();
                    BeanCopyUtil.copyProperties(views.get(i), detail);
                    paramDetails.add(detail);
                }

            } else {
                log.warn("没有找到对应的参数[" + key + "]的数据列表");
            }
            return paramDetails;
        }catch (Exception e){
            if(e instanceof BaseException) {
                BaseException e1=(BaseException)e;
                throw new com.bdx.rainbow.urs.exception.BusinessException(new com.bdx.rainbow.urs.exception.DefaultExceptionCode( e1.getErrorCode(), e1.getErrorMsg()));
            }else if(e instanceof com.bdx.rainbow.urs.exception.BaseException){
                throw e;
            }else {
                throw new Exception("系统异常！");
            }
        }
    }
}
