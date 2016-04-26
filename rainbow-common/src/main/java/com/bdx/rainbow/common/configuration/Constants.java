package com.bdx.rainbow.common.configuration;




public class Constants {
	
	public static final String SESSION_FRAME = "SYS_USER";
	
	public static final Integer UNKNOW_USER_ID = 9999;
	/**
	 * 默认密码
	 */
	public static String LOGPWD = "123456";
	/**
	 * 正常状态1
	 */
	public static String COMMON_STATUS_VALID = "1";
	/**
	 * 电商的用户类型: 0平台管理员 1加盟渠道管理员 2平台普通员工 3加盟渠道普通员工 4自有渠道员工 5 个人用户
	 */
	public static String USERTYPES0 = "0";
	/**
	 * 电商的用户类型: 0平台管理员 1加盟渠道管理员 2平台普通员工 3加盟渠道普通员工 4自有渠道员工 5 个人用户
	 */
	public static String USERTYPES1 = "1";
	/**
	 * 电商的用户类型: 0平台管理员 1加盟渠道管理员 2平台普通员工 3加盟渠道普通员工 4自有渠道员工 5 个人用户
	 */
	public static String USERTYPES2 = "2";
	/**
	 * 电商的用户类型: 0平台管理员 1加盟渠道管理员 2平台普通员工 3加盟渠道普通员工 4自有渠道员工 5 个人用户
	 */
	public static String USERTYPES3 = "3";
	/**
	 * 电商的用户类型: 0平台管理员 1加盟渠道管理员 2平台普通员工 3加盟渠道普通员工 4自有渠道员工 5 个人用户
	 */
	public static String USERTYPES4 = "4";
	/**
	 * 电商的用户类型: 0平台管理员 1加盟渠道管理员 2平台普通员工 3加盟渠道普通员工 4自有渠道员工 5 个人用户
	 */
	public static String USERTYPES5 = "5";

	/**
	 * 系统的手机端doman 配置
	 */
	public static String P_MENUDOMAN = "";

	/**
	 * 组织机构状态 1：正常，
	 */
	public static final String DEPT_STATUS_1 = "1";
	/**
	 * 组织机构状态 2：禁用
	 */
	public static final String DEPT_STATUS_2 = "2";
	/**
	 * 组织机构状态  3 无效
	 */
	public static final String DEPT_STATUS_3 = "3";
	
	
	/**
	 * sysLogon 登录的信息的key: serverIp  主机服务器地址，   logonIp 用户登录地址 ， logonMac 客户端Mac
	 */
	public static String SYS_LOGON_SERVERIP = "serverIp";
	public static String SYS_LOGON_LOGONIP = "logonIp";
	public static String SYS_LOGON_LOGONMAC = "logonMac";
	public static String SYS_LOGON_IMEI = "logonImei";
	public static String SYS_LOGON_VERSION = "logonVersion";
	public static String SYS_LOGON_BRAND = "logonBrand";
	public static String SYS_LOGON_MODEL = "logonModel";
	public static String SYS_LOGON_OS = "logonOs";
	public static String SYS_LOGON_SYS = "logonSys";
	/**
	 * 登录的终端类型。iphone，ipad，andorid手机，androidpad
	 */
	public static String SYS_LOGON_LOGONDEVICE = "logonDevice";
	/**
	 * 登录的源系统。快销，快服，快销PC
	 */
	public static String SYS_LOGON_LOGONSYS = "logonSys";
	

	/**
	 * 错误类型定义
	 * @author zhujl
	 *
	 */
	public static enum ErrorType{
		USER_NOT_EXSIST("error.user.unknownUser","用户不存在!"),
		PWD_ERROR("error.pwd.passwordError","登录密码错误!"),
		ROLE_NO_MENU("error.role.noMenuError","用户的角色未绑定有效的菜单，请联系平台管理员!"),
		USER_POWER_NO_ROLE("error.user.noRoleError","用户未绑定角色，请联系平台管理员!"),
		USER_NULL("error.user.nullError","用户名不能为空值!"),
		PWD_NULL("error.pwd.nullError","登录密码不能为空值!"),
		CAPTCHA_NULL("error.captcha.nullError","验证码不能为空值!"),
		CAPTCHA_ERROR("error.captcha.error","验证码错误!"),
		USER_STATUS_ERROR("error.user.errorStatus","用户状态不正常！"),
		USER_TYPE_ERROR("error.user.errorUserType","该用户不能登录该平台！"),
		USER_DEPT_ERROR("error.user.errorDeptStatus","该用户所属组织渠道状态异常，不能登录！"),
		SUCCESS("0000","成功"),
		
		/** 登陆超时 **/
		NO_USER_INFO("0001","登陆超时"),//	登陆超时
		
		/** 请求解析异常 **/
		UNRECOGNIZED_REQUEST("0002","请求解析异常"),//	请求解析异常
		
		/** 返回解析异常 **/
		UNRECOGNIZED_RESPONSE("0003","返回解析异常"),//	返回解析异常
		
		/** 未知错误 **/
		UNKNOW_ERROR("9999","未知错误"),//	未知错误
		
		/** 预料到的错误 **/
		EXPECT_ERROR("9998","预料到的错误"),//	预料到的错误
		
		/** 预料到的错误 **/
		SERVER_ERROR("8999","服务器到的错误");//	服务器到的错误

		private String code;//错误编码，请毋必按规范命名
		private String msg;//错误返回信息，以!结尾
		private ErrorType(String code,String msg){
			this.code=code;
			this.msg = msg;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		/**根据编码获得错误类型*/
		public static ErrorType getByCode(String code){
			for(ErrorType e:values()){
				if(e.getCode().equalsIgnoreCase(code)){
					return e;
				}
			}
			return null;
		}
		
	}
}

