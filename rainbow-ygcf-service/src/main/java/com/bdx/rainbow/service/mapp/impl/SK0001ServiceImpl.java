package com.bdx.rainbow.service.mapp.impl;

import com.bdx.rainbow.basic.dubbo.bean.DubboEnterpriseInfo;
import com.bdx.rainbow.basic.dubbo.service.IEnterpriseDubboService;
import com.bdx.rainbow.common.util.StringUtil;
import com.bdx.rainbow.service.mapp.ISK0001Service;
import com.bdx.rainbow.urs.dubbo.IDubUserService;
import com.bdx.rainbow.urs.entity.IUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 手机0001服务接口
 * Created by fusj on 16/3/21.
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class SK0001ServiceImpl implements ISK0001Service {

    @Autowired
    private IDubUserService dubUserService;

    /**
     * 校验登陆
     * @param map
     * @return
     */
    @Override
    public IUserInfo checkUserLogin(Map map) throws Exception {
        String userName = "";
        if(StringUtil.isNotEmptyObject(map.get("username"))) {
            userName = map.get("username").toString();
        }

        String password = "";
        if(StringUtil.isNotEmptyObject(map.get("password"))) {
            password = map.get("password").toString();
        }

        Map<String, Object> sysLogon = new HashMap<>();

        IUserInfo sysUserInfo = dubUserService.login(userName, password, 1, sysLogon);

        return sysUserInfo;
    }
}
