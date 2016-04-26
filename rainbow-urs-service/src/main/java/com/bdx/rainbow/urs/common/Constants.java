package com.bdx.rainbow.urs.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("urs")
public class Constants {
	
	public static final String SESSION_FRAME = "SYS_USER";
	/**
	 * 默认密码
	 */
	public static String LOGPWD = "123456";
	/**
	 * 用户状态：正常状态1，禁用状态2，失效状态3
	 */
	public static String USER_STATUS_NORMAL_1 = "1";
	
	public static String USER_STATUS_DISABLED_2 = "2";
	
	public static String USER_STATUS_INVALID_3 = "3";

	/**
	 * urs的用户类型: 0 普通用户 1 食药监用户 2 企业用户 9 超级管理员用户 
	 */
	public static String USERTYPES_PT_0 = "0";//0 普通用户
	
	public static String USERTYPES_SYJ_1 = "1";//1 食药监用户
	
	public static String USERTYPES_QY_2 = "2";//2 企业用户

	public static String USERTYPES_ADMIN_9 = "9";//9 管理员用户

	/**
	 * 系统的手机端doman 配置
	 */
	public static String P_MENUDOMAN = "";

	/**
	 * 组织机构状态 1：正常，
	 */
	public static final String DEPT_STATUS_NORMAL_1 = "1";
	/**
	 * 组织机构状态 2：禁用 人工禁用
	 */
	public static final String DEPT_STATUS_DISABLED_2 = "2";
	/**
	 * 组织机构状态  3 无效 自动失效
	 */
	public static final String DEPT_STATUS_INVALID_3 = "3";

    /**
     * 平台状态 1：正常，
     */
    public static final String PLAT_STATUS_NORMAL_1 = "1";
    /**
     * 平台状态 2：禁用 人工禁用
     */
    public static final String PLAT_STATUS_DISABLED_2 = "2";
    /**
     * 平台状态  3 无效 自动失效
     */
    public static final String PLAT_STATUS_INVALID_3 = "3";

    /**
     * 菜单状态 1：正常，
     */
    public static final String MENU_STATUS_NORMAL_1 = "1";
    /**
     * 菜单状态 2：禁用 失效
     */
    public static final String MENU_STATUS_DISABLED_2 = "2";

    public static final String USER_ADMIN_FLAG="is_Admin";
	
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
		USER_DEPT_ERROR("error.user.errorDeptStatus","该用户所属组织渠道状态异常，不能登录！");

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
