package com.bdx.rainbow.core.utils;

import java.text.DecimalFormat;

/**
 * 计算工具类
 * 
 * @author fox
 *
 */
public class CalculateUtil {
	/**
	 * 100以上会有问题，因为没有小数。
	 * @param x
	 * @param total
	 * @return
	 */
	public static String getPercent(int x, int total) {
		if (x == 0 || total == 0) {
			return "0%";
		}
		String result = "";// 接受百分比的值
		double x_double = x * 1.0;
		double tempresult = x / (total * 1.0);
		DecimalFormat df1 = new DecimalFormat("0%"); 
		result = df1.format(tempresult);
		return result;
	}

	public static void main(String[] args) {
		CalculateUtil.getPercent(79, 80);
	}
}
