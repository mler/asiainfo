package com.bdx.rainbow.toolbox;

import com.bdx.rainbow.common.configuration.Constants;
import com.bdx.rainbow.urs.entity.SysUserInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * session处理
 * Created by fusj on 16/3/4.
 */
public class SessionManager {

    /**
     * 从session中获取key
     * @param key
     * @param request
     * @return
     */
    public static String getSessionValueByKey(String key, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(key);

        if(null == obj) {
            return "";
        }

        return obj.toString();
    }

    /**
     * 获取用户名
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        SysUserInfo sysUserInfo =  (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);

        if(null == sysUserInfo) {
            return "";
        }

        return sysUserInfo.getUser().getUserName();
    }

    /**
     * 获取用户类型
     * @param request
     * @return
     */
    public static String getUserType(HttpServletRequest request) {
        SysUserInfo sysUserInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);

        if(null == sysUserInfo) {
            return "";
        }

        return sysUserInfo.getUser().getUserType();
    }

    /**
     * 获取组织名称
     * @param request
     * @return
     */
    public static String getDeptName(HttpServletRequest request) {
        SysUserInfo sysUserInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);

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
        SysUserInfo sysUserInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SESSION_FRAME);

        if(null == sysUserInfo) {
            return 0;
        }

        return sysUserInfo.getUser().getCorpId();
    }
}
