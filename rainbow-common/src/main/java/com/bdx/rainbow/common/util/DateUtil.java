package com.bdx.rainbow.common.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * Created by fusj on 16/1/22.
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 12个月每个月的天数
     */
    private static int[] DAYS = { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /**
     * 校验字符串是否符合yyyyMMdd
     * @param date
     * @return
     */
    public static boolean isValidDate(String date) {
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            if (year <= 0) {
                return false;
            }

            int month = Integer.parseInt(date.substring(4, 6));
            if (month <= 0 || month > 12) {
                return false;
            }

            int day = Integer.parseInt(date.substring(6, 8));
            if (day <= 0 || day > DAYS[month]) {
                return false;
            }

            if (month == 2 && day == 29 && !isGregorianLeapYear(year)) {
                return false;
            }
        } catch (Exception e) {
            logger.error(String.format("%s:[%s]", e.getMessage(), date), e);
            return false;
        }

        return true;
    }

    /**
     * 判断是否闰年
     * @param year
     * @return
     */
    public static final boolean isGregorianLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
    
    /** yyyy-MM-dd HH:mm:ss **/
	public static final String  DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**yyyy-MM-dd **/
	public static final String  DATE_FORMAT2 = "yyyy-MM-dd";
	/** yyyy	 */
	public static final String  DATE_FORMAT3 = "yyyy";
	/** yyyyMMdd	 */
	public static final String  DATE_FORMAT4 = "yyyyMMdd";
	/** yyyyMM	 */
	public static final String  DATE_FORMAT5 = "yyyyMM";
	/** MMdd	 */
	public static final String  DATE_FORMAT6 = "MMdd";
    /** HH:mm:ss */
    public static final String  DATE_FORMAT7 = "HH:mm:ss";
	
	private static final SimpleDateFormat format_yyyyMMddHH24Miss=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat format_yyyyMMdd=new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat format_yyyyMM=new SimpleDateFormat("yyyyMM");
	private static final SimpleDateFormat format_MMdd=new SimpleDateFormat("MMdd");
	private static final SimpleDateFormat format_yyyy=new SimpleDateFormat("yyyy");
	
	
	private static final SimpleDateFormat format_dd=new SimpleDateFormat("dd");
	
	
	public static Timestamp getCurrent() {
		return new Timestamp(getTime().getTimeInMillis());
	}
	
	/**
	 * <p>描述: 获得任意格式日期字符串</p> 
	 * @param pattern
	 * @return:       String   
	 * @author        Zhengwj 
	 * @Date          2013-5-27 上午10:13:05
	 */
	public static String getDateString(String pattern) {
		DateFormat dfmt = new SimpleDateFormat(pattern);
		return dfmt.format(new Date());
	}
	
	 /**
     * 得到当前日期字符串,默认为格式为：yyyy-MM-dd HH:mm:ss
     * @param dateFormat
     * @return
     */
    public static String getTime(String dateFormat){        
      Calendar now=Calendar.getInstance();     
      return format_yyyyMMddHH24Miss.format(now.getTime()); 
    }
   
    /**
     * 日期按dateFormat的格式，格式化成字符串
     * @param timeMillis
     * @param dateFormat
     * @return
     */
    public static String parse(long timeMillis,String dateFormat) {
    	SimpleDateFormat sf=new SimpleDateFormat(dateFormat);
    	return sf.format(new Date(timeMillis));
    }

    /**
     * 把date字符串按照指定日期格式进行格式化
     * @param date
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    public static String parse(String dateStr, String sourceFormat, String targetFormat) throws ParseException {
        SimpleDateFormat source = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat target = new SimpleDateFormat(targetFormat);

        Date date = source.parse(dateStr);

        return target.format(date);
    }

    public static Date formatDate(String date,String dateFormat) throws ParseException
    {
    	SimpleDateFormat sf=new SimpleDateFormat(dateFormat);
    	return sf.parse(date);
    }

    public static Calendar getTime() {
    	Calendar time=Calendar.getInstance();
    	return time;
    }
    
    public static Calendar getTime(long timeMillis) {
    	Calendar time=getTime();
    	time.setTimeInMillis(timeMillis);
    	return time;
    }
    public static Date getDate() {
    	return getTime().getTime();
    }
    public static Date getDate(long timeMillis) {
    	return getTime(timeMillis).getTime();
    }
    public static String getYYYY() {
    	return format_yyyy.format(getDate());
    }
    
    public static String getYYYY(long timeMillis) {
    	return format_yyyy.format(getDate(timeMillis));
    }
    
    public static String getYYYMMDD() {
    	return format_yyyyMMdd.format(getDate());
    }
    public static String getYYYYMMDD(long time) {
    	return format_yyyyMMdd.format(getDate(time));
    }
    public static String getYYYYMM() {
    	return format_yyyyMM.format(getDate());
    }
    public static String getYYYYMM(long timeMillis) {
    	return format_yyyyMM.format(getDate(timeMillis));
    }
    public static String getMMDD() {
    	return format_MMdd.format(getTime());
    }
    public static String getMMDD(long timeMillis) {
    	return format_MMdd.format(getDate(timeMillis));
    }
    public static String getDD(long timeMillis) {
    	return format_dd.format(getDate(timeMillis));
    }
    
    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }
    
    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }
    
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
  
    public static Timestamp getTimestamp(String time) {
    	  Timestamp ts = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			ts = new Timestamp(format.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return ts;
    }

    public static Timestamp getTimestamp(String time, String formatString) {
        Timestamp ts = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            ts = new Timestamp(format.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ts;
    }
    
    public static Timestamp getTimestampNext(String time) {
  	  Timestamp ts = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.setTime(format.parse(time));
	        c.add(Calendar.DATE, 1);
			ts = new Timestamp(c.getTimeInMillis());
		} catch (ParseException e) {
			e.printStackTrace();
		}
  	return ts;
  }
    
    public static String getStryyyyMMddHH24Miss(Timestamp time) {
    	return format_yyyyMMddHH24Miss.format(time);
    }

    /**
     * 获得某月的第一天
     * @param time
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Timestamp getMonthFirst(String time,String pattern) throws ParseException{
    	//规定返回日期格式
    	SimpleDateFormat sf=new SimpleDateFormat(pattern);
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(sf.parse(time));
    	//设置为第一天
    	calendar.set(Calendar.DATE, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获得某月的最后一天
     * @param time
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Timestamp getMonthLast(String time,String pattern) throws ParseException{
    	//规定返回日期格式
    	SimpleDateFormat sf=new SimpleDateFormat(pattern);
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(sf.parse(time));
    	//设置为第一天
    	calendar.set(Calendar.DATE, calendar.getActualMaximum(calendar.DATE));
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获得最近的几个月 列表
     * @param pattern
     * @param num
     * @return
     * @throws ParseException
     */
    public static List<String> getLastMonthList(String pattern,int num) throws ParseException{
    	SimpleDateFormat sf = new SimpleDateFormat(pattern);
    	Calendar now = Calendar.getInstance();
    	Calendar calendar = Calendar.getInstance();
    	List<String> monthlList = new ArrayList<String>();
    	String tmpMonth = null;
    	for (int i = 0; i < num; i++) {
    		calendar.setTime(now.getTime());
    		calendar.add(Calendar.MONTH, -i);
    		tmpMonth = sf.format(calendar.getTime());
    		monthlList.add(tmpMonth);
		}
    	return monthlList;
    }
   public static void main(String[] args) {
	try {
		System.out.println(getMonthFirst("201602", "yyyyMM"));

		System.out.println(getMonthLast("201602", "yyyyMM"));

		List<String> monthlList = getLastMonthList("yyyyMM",6);
		for (String ss:monthlList) {
			System.out.println(ss);
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
    public static String getNextMonth() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); //制定日期格式
        Calendar c=Calendar.getInstance();
        Date date=new Date();
        c.setTime(date);
        c.add(Calendar.MONTH,1); //将当前日期加一个月
        String validityDate = df.format(c.getTime()); //返回String型的时间

        return validityDate;
    }
}
