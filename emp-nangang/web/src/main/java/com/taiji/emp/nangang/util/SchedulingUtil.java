package com.taiji.emp.nangang.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;

public class SchedulingUtil {

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
