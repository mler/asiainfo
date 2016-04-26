package com.bdx.rainbow.common;

import com.bdx.rainbow.urs.entity.SysUserInfo;
import javax.servlet.http.HttpServletRequest;

/**
 * session处理
 * Created by fusj on 16/3/4.
 */
public class SessionManager {

    private static final String SESSION_NAME = "sysUserInfo";

    /**
     * 获取用户名
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        SysUserInfo sysUserInfo =  (SysUserInfo) request.getSession().getAttribute(SESSION_NAME);

        if(null == sysUserInfo) {
            return "";
        }

        return sysUserInfo.getUser().getUserName();
    }

    /**
     * 获取组织名称
     * @param request
     * @return
     */
    public static String getDeptName(HttpServletRequest request) {
        SysUserInfo sysUserInfo = (SysUserInfo) request.getSession().getAttribute(SESSION_NAME);

        if(null == sysUserInfo) {
            return "";
        }

        return sysUserInfo.getDeptName();
    }

    /**
     * 获取企业编号
     * @param request
     * @return
     */
    public static Integer getEnterpriseId(HttpServletRequest request) {
        SysUserInfo sysUserInfo = (SysUserInfo) request.getSession().getAttribute(SESSION_NAME);

        if(null == sysUserInfo) {
            return 0;
        }

        return sysUserInfo.getUser().getCorpId();
    }
}
