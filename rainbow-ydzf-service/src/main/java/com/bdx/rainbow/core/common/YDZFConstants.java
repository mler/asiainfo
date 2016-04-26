package com.bdx.rainbow.core.common;

public class YDZFConstants {

	/**
	 * 监督管理常量
	 * 
	 * @author fox
	 *
	 */
	public static class MONITOR {
		// 日常监督分类
		public static enum CHECKPLANTYPE {
			// 日常，专项，突击，抽查
			DAILY("0", "RC"), SPECIAL("1", "ZX"), SUDDEN("2", "TJ"), RANDOM(
					"3", "CC");
			private String code;
			private String abb;

			private CHECKPLANTYPE(String code, String abb) {
				this.code = code;
				this.abb = abb;
			}

			public String getCode() {
				return code;
			}

			public String getAbb() {
				return abb;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public void setAbb(String abb) {
				this.abb = abb;
			}

			/** 根据编码获得缩写 */
			public static CHECKPLANTYPE getByCode(String code) {
				for (CHECKPLANTYPE e : values()) {
					if (e.getCode().equalsIgnoreCase(code)) {
						return e;
					}
				}
				return null;
			}

		}

		// 日常监督状态－－0：待执行
		public static final String PLAN_STATUS_EXECUTE_WAIT = "0";
		// 日常监督状态－－1:正在执行
		public static final String PLAN_STATUS_EXECUTE_ING = "1";
		// 日常监督状态－－9:完成
		public static final String PLAN_STATUS_EXECUTE_FINISH = "9";

		// 日常监管管理对象状态－－：待执行
		public static final String PLAN_REL_STATUS_EXECUTE_WAIT = "0";
		// 日常监管管理对象状态－－9:完成
		public static final String PLAN_REL_STATUS_EXECUTE_FINISH = "9";

		// 监督任务详情表状态－－：0:未填写
		public static final String TASK_STATUS_EXECUTE_INIT = "0";

		// 监督任务详情表状态－－1:已保存
		public static final String TASK_STATUS_EXECUTE_SAVE = "1";

		// 监督任务详情表状态－－9:已提交
		public static final String TASK_STATUS_EXECUTE_SUBMIT = "9";

	}

	/**
	 * 稽查案件常量
	 * 
	 * @author fox
	 *
	 */
	public static class INSPECTCASE {
		// 稽查案件执行状态－－0：待执行
		public static final String INSPECT_STATUS_WAIT = "0";
		// 稽查案件执行状态－－1：调查取证
		public static final String INSPECT_STATUS_EVIDENCE = "1";
		// 稽查案件执行状态－－2：拟处罚
		public static final String INSPECT_STATUS_READY_PUNISH = "2";
		// 稽查案件执行状态－－3：案件审核
		public static final String INSPECT_STATUS_AUDIT = "3";
		// 稽查案件执行状态－－4：听证
		public static final String INSPECT_STATUS_WITNESSES = "4";
		// 稽查案件执行状态－－5：执行处罚(保存)
		public static final String INSPECT_STATUS_EXECUTE_PUNISH_SAVE = "5";
		// 稽查案件执行状态－－6：执行处罚(提交)
		public static final String INSPECT_STATUS_EXECUTE_PUNISH_SUBMIT = "6";
		// 稽查案件执行状态－－7：结案
		public static final String INSPECT_STATUS_FINISH = "7";
		// 执行处罚状态－－1:已保存
		public static final String EXECUTE_PUNISH_STATUS_SAVE = "1";
		// 执行处罚状态－－9:已提交
		public static final String EXECUTE_PUNISH_STATUS_SUBMIT = "9";
		//执行方式:	1:立即执行,2:短信告知，3：电话通知
		public static final String EXECUTE_METHOD_NOW="1";
		//执行方式:	1:立即执行,2:短信告知，3：电话通知
		public static final String EXECUTE_METHOD_SMS="2";
		//执行方式:	1:立即执行,2:短信告知，3：电话通知
		public static final String EXECUTE_METHOD_PHONE="3";
	

	}

	public static class PLATFORM {
		// 平台id
		public static final int YDZF_PLAT_ID = 6;
	}

	public static class SYSTEM {
		// 无效
		public static final String STATUS_INVALID = "0";
		// 生效
		public static final String STATUS_VALID = "1";
		// 未删除
		public static final String IS_NOT_DEL = "0";
		// 以删除
		public static final String IS_DEL = "1";
		// 文件失效
		public static final String FILE_INVALID = "0";
		// 文件导入成功
		public static final String FILE_SUC = "1";
		// 文件待导入
		public static final String FILE_STANDBY = "2";
		// 文件导入失败
		public static final String FILE_ERROR = "3";
	}
	
	public static void main(String[] args) {
		for (YDZFConstants.MONITOR.CHECKPLANTYPE e : YDZFConstants.MONITOR.CHECKPLANTYPE.values()) {
			System.out.println(e.getCode());
		}
	}

}
