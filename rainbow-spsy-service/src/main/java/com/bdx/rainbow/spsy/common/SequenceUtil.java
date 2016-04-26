package com.bdx.rainbow.spsy.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceUtil {

	/**
	 * 获取唯一编号
	 */
	
	private static AtomicLong ad = new AtomicLong(0);
	
	public static String getSequence() {
		StringBuffer fileid = new StringBuffer(20);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		fileid.append(formatter.format(new Date()));

		/*
		 * 为了防止在一毫秒内有并发，还需要带上两位序列号
		 */
		double idx = ad.getAndAdd(1);

		// SequenceUtil.getNextSequence("SEQ_FILEID");
		idx = idx % 99;
		DecimalFormat df = new DecimalFormat("00");
		fileid.append(df.format(idx));
		return fileid.toString();
	}
	
}
