package com.bdx.rainbow.service.mapp;

import com.bdx.rainbow.urs.entity.IUserInfo;

import java.util.List;
import java.util.Map;

/**
 * 台账上传接口
 * Created by fusj on 16/3/22.
 */
public interface ISK0022Service {
    /**
     * 台账上传
     * @param map
     * @param ticket
     * @param sysUserInfo
     */
    void uploadLedger(List<Map<String, String>> list, byte[] ticket, IUserInfo sysUserInfo) throws Exception;
}
