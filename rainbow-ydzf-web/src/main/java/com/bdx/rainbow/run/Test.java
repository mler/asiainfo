package com.bdx.rainbow.run;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.bdx.rainbow.common.util.DateUtil;
import com.bdx.rainbow.core.common.YDZFConstants;
import com.bdx.rainbow.entity.ydzf.TYdzfInspectCase;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
public static void main(String[] args) throws ParseException {
	
	System.out.println(DateUtil.getStryyyyMMddHH24Miss(DateUtil.getMonthFirst("2016-04-01 00:00:00", "yyyy-MM-dd HH:mm:ss")));
	System.out.println(Test.getTimeDifference(DateUtil.getCurrent(), DateUtil.getMonthFirst("2016-04-03 00:00:00", "yyyy-MM-dd HH:mm:ss")));

}

public static void a()
{
	TYdzfInspectCase ydzfInspectCase= new TYdzfInspectCase();
	ydzfInspectCase.setInspectCaseId(1);
	ydzfInspectCase.setCreateDate(DateUtil.getCurrent());
	Integer insepctCaseId =ydzfInspectCase.getInspectCaseId();
	Map<String,List<Map<String,String>>> resultMap = new HashMap<String,List<Map<String,String>>>();
	List<Map<String,String>> resultList= new ArrayList<Map<String,String>>();
	Map<String,String> waitMap= new HashMap<String,String>();
	waitMap.put("status", YDZFConstants.INSPECTCASE.INSPECT_STATUS_WAIT);
	waitMap.put("id", insepctCaseId.toString());
	waitMap.put("datetime", DateUtil.getStryyyyMMddHH24Miss(ydzfInspectCase.getCreateDate()));
	resultList.add(waitMap);
	resultMap.put("status_info", resultList);
	ObjectMapper mapper = new ObjectMapper();
	  try
        {
		  System.out.println(mapper.writeValueAsString(resultMap)); ;
        } catch (IOException e)
        {
        }}

/**
 * 计算两个日期的时间差
 * @param formatTime1
 * @param formatTime2
 * @return
 */
public static String getTimeDifference(Timestamp formatTime1, Timestamp formatTime2) {
       SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
       long t1 = 0L;
       long t2 = 0L;
       try {
           t1 = timeformat.parse(getTimeStampNumberFormat(formatTime1)).getTime();
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       try {
           t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2)).getTime();
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       //因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
       int days=(int) ((t1 - t2)/3600000/24);
       int hours=(int) ((t1 - t2)/3600000);
       int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
       int second=(int) ((t1 - t2)/1000-hours*3600-minutes*60);
       return ""+days+"天"+hours+"小时"+minutes+"分"+second+"秒";
   }
/**
 * 格式化时间
 * Locale是设置语言敏感操作
 * @param formatTime
 * @return
 */
public static String getTimeStampNumberFormat(Timestamp formatTime) {
       SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
       return m_format.format(formatTime);
   }
}
