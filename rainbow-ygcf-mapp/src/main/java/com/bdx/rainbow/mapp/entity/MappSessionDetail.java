package com.bdx.rainbow.mapp.entity;

import com.bdx.rainbow.mapp.core.base.MappUser;
import com.bdx.rainbow.urs.entity.IUserInfo;
import com.bdx.rainbow.urs.entity.SysUserInfo;

/**
 *
 * Created by fusj on 16/4/22.
 */
public class MappSessionDetail implements MappUser {

    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 企业编号
     */
    private Integer corpId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户信息
     */
    private IUserInfo userInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public IUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(IUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getUserId() {
        return getId() + "";
    }
}
