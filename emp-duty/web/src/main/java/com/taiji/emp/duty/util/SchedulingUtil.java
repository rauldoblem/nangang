package com.taiji.emp.duty.util;

import com.taiji.micro.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class SchedulingUtil {

    /**
     * 根据传入时间，获取calendar
     */
    public static Calendar dayForCalen(String time)  {

        Date tmpDate =  DateUtil.stringToDate(time,"yyyy-MM");

        Calendar cal = Calendar.getInstance();

        cal.setTime(tmpDate);

        return cal;
    }

    /**
     * 根据传入时间，获取calendar
     */
    public static Calendar dayForCalenTime(String time)  {

        Date tmpDate =  DateUtil.stringToDate(time,"yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        cal.set(tmpDate.getYear(), tmpDate.getMonth(),tmpDate.getDay());

        return cal;
    }

    /**
     * 传入日期，天数判断是否工作日或是节假日
     */
    public static Boolean weekDayOrWeekEnd(String time,int day)  {
        boolean flag = true;
        String str = day>=10?String.valueOf(day):"0"+String.valueOf(day);
        time = time+"-"+str;
        Date timeDate = DateUtil.stringToDate(time,"yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(timeDate);
        int weekDay = cal.get(7);

        if(weekDay==Calendar.SUNDAY||weekDay==Calendar.SATURDAY){
            flag = false;
        }
        return flag;
    }

    /**
     * 传入日期，天数返回LocalDate
     */
    public static LocalDate stringToLocalDate(String time, int day)  {

        String str = String.valueOf(day);
        if (str.length()==1){
            str = "0"+str;
        }
        time = time+"-"+str;
        LocalDate localDate = DateUtil.strToLocalDate(time);
        return localDate;
    }

    /**
     * 获取当月天数
     */
    public static int getMonthNumber(String date){
        if (StringUtils.isNotEmpty(date)){
            String[] split = date.split("-");
            Calendar calen = Calendar.getInstance();
            //当前天数
            calen.set(Integer.parseInt(split[0]),Integer.parseInt(split[1]),0);
            int number = calen.get(Calendar.DAY_OF_MONTH);
            return number;
        }else{
            return 0;
        }
    }

    /**
     * 根据传入code获取相应的name
     */
    public static String getName(Integer code) {
        String name = null;
        if (code != null) {
            if (code == 1) {
                name = "工作日";
            } else if (code == 2) {
                name = "双休日";
            } else if (code == 3) {
                name = "法定节假日";
            } else if (code == 4) {
                name = "特殊节假日";
            } else if (code == 5) {
                name = "其它";
            }
        }
        return name;
    }
}
