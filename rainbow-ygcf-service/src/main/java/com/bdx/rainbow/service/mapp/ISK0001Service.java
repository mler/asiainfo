package com.bdx.rainbow.service.mapp;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.Map;

/**
 * 接口0001的Service
 * Created by fusj on 16/3/21.
 */
public interface ISK0001Service {

    /**
     * 校验登陆
     * @param map
     * @return
     */
    IUserInfo checkUserLogin(Map map) throws Exception;
}
