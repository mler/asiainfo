package com.bdx.rainbow.service.vfs;

//import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
//import com.bdx.rainbow.configuration.vfs.OpenOfficeSetting;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * OpenOffice连接池，单例
 * Created by fusj on 16/4/8.
 */
public enum OpenOfficeConnection {

    INSTANCE;

//    private com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection connection;
//
//    OpenOfficeConnection() {
//        try {
//            OpenOfficeSetting openOfficeSetting = SpringBeanFactory.getSpringBean(OpenOfficeSetting.class);
//
//            connection = new SocketOpenOfficeConnection(openOfficeSetting.getIp(), openOfficeSetting.getPort());
//            connection.connect();
//        } catch (Exception ex) {
//            Logger logger = LoggerFactory.getLogger(OpenOfficeConnection.class);
//            logger.error("openOffice连接初始化失败:" + ex.getMessage(), ex);
//
//            connection = null;
//        }
//    }
//
//    public com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection getConnection() {
//        return connection;
//    }
//
//    public void setConnection(com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection connection) {
//        this.connection = connection;
//    }
}
