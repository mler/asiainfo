package com.bdx.rainbow.kafka;

import org.springframework.integration.kafka.support.KafkaHeaders;

public class BdxJmsHeaders extends KafkaHeaders {
	
	public final static String MSG_HEADER_FROM = "system_form";//来源的系统
	
	public final static String MSG_HEADER_TO = "system_to";//目的地系统
	
	public final static String MSG_HEADER_IP = "ip_from";//来源的ip
	
	public final static String MSG_HEADER_ERR_CODE = "err_code";//错误编码
	
	public final static String MSG_HEADER_ERR_INFO = "err_info";//错误信息
	
	public final static String MSG_HEADER_TOKEN = "token";//令牌信息
	
	public final static String MSG_HEADER_BIZCODE = "bizcode";//业务编码或者地址
	
	public final static String MSG_HEADER_SIGN = "sign";//签名
	
	public final static String MSG_HEADER_VERSION = "version";//版本
	
	public final static String MSG_HEADER_ENCRYPT = "encrypt";//是否加密
	
	public final static String MSG_HEADER_CONTEXTID = "contextid";//上下文id
}
