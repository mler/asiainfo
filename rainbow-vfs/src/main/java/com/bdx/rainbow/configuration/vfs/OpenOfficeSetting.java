package com.bdx.rainbow.configuration.vfs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OpenOffice文件设置
 * Created by fusj on 16/4/8.
 */
@ConfigurationProperties("openOffice")
public class OpenOfficeSetting extends AbstractSettings {

    /**
     * 安装根目录
     */
    private String home;

    /**
     * 执行软件
     */
    private String app;

    /**
     * 运行的机器IP，一般是本机
     */
    private String ip;

    /**
     * 进程启动端口
     */
    private Integer port;

    /**
     * 转换过程中临时文件存储目录
     *  运行程序本机的目录
     */
    private String temp;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
