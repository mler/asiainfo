package com.bdx.rainbow.mapp.model.req;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 用户登录
 *
 * @author luli
 */
public class YDZF0001Request extends BDXBody {
    /**
     * 用户名
     */
    private String usercode;
    /**
     * 用户密码
     */
    private String pwd;
    
    /**
     * 平台ID
     */
    private Integer platId;
    /**
     * 用户类型
     */
    private String sysType;
    /**
     * 客户端版本号
     */
    private String version;
    /**
     * 客户端操作系统
     */
    private String os;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 登陆平台
     */
    private String logonSys;

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getLogonSys() {
        return logonSys;
    }

    public void setLogonSys(String logonSys) {
        this.logonSys = logonSys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        YDZF0001Request that = (YDZF0001Request) o;

        if (usercode != null ? !usercode.equals(that.usercode) : that.usercode != null)
            return false;
        if (pwd != null ? !pwd.equals(that.pwd) : that.pwd != null) return false;
        return !(os != null ? !os.equals(that.os) : that.os != null);
    }

    @Override
    public int hashCode() {
        int result = usercode != null ? usercode.hashCode() : 0;
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        return result;
    }

	public Integer getPlatId() {
		return platId;
	}

	public void setPlatId(Integer platId) {
		this.platId = platId;
	}
}
