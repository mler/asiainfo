package com.bdx.rainbow.spsy.web;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by core on 16/3/10.
 */
public class DateUtil {
    public static String dateDiff(String fromDate) {
        if (fromDate != null && StringUtils.isNotEmpty(fromDate)){
            Calendar aCalendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date from=dateFormat.parse(fromDate,new ParsePosition(0));
            Long time=(from.getTime()-System.currentTimeMillis()) /(1000*3600*24);

            if (time<=0){
                return "-1";
            }else if (time<=31&&time>=1){
                return "0";
            }
            return "1";
        }
       return "";
    }
    
}
