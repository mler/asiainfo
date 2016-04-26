package com.bdx.rainbow.service.mapp;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.Map;

/**
 * 基础通用接口
 * Created by fusj on 16/3/21.
 */
public interface IBasicService {

    /**
     * 根据ID获取企业信息
     * @param corpId
     * @return
     */
    DubboEnterpriseInfo getEnterpriseById(Integer corpId) throws Exception;

    /**
     * 根据key获取基础数据
     * @param key
     * @return
     * @throws Exception
     */
    Map<String, String> findCacheByKey(String key)throws Exception;

    /**
     * 获取当前用户组织机构
     * @param sysUserInfo
     * @return
     */
    Map<String,String> getSysDeptTree(IUserInfo sysUserInfo) throws Exception;
}
