package com.taiji.emp.nangang.common.constant;

public class SchedulingGlobal {

    public static final String INFO_SHIFT_CURRENT = "0";//当前班次
    public static final String INFO_SHIFT_PREVIOUS = "1";//上一班次
    public static final String INFO_SHIFT_NEXT = "2";//下一班次

    public static final String P_TYPE_CODE_TIMES = "0";//班次
    public static final String P_TYPE_CODE_DAY = "1";//天

    /**
     *  排班标识，0未排班，1已排班
     */
    public static final String SCHEDULING_FLAG_NO = "0";
    public static final String SCHEDULING_FLAG_YES = "1";

    /**
     *  班次结束时间是否当日（0：当日；1：次日）
     */
    public static final String SCHEDULING_IS_TODAY_NO = "1";
    public static final String SCHEDULING_IS_TODAY_YES = "0";

    /**
     *  是否交接班，0否，1是
     */
    public static final String SCHEDULING_IS_SHIFT_NO = "0";
    public static final String SCHEDULING_IS_SHIFT_YES = "1";

    /**
     *  日期类型编码（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    public static final Integer DATE_TYPE_CODE_WEEKDAY = 1;
    public static final Integer DATE_TYPE_CODE_WEEKEND = 2;
    public static final Integer DATE_TYPE_CODE_LEGAL_HOLIDAY = 3;
    public static final Integer DATE_TYPE_CODE_SPECIPAL_HOLIDAY = 4;
    public static final Integer DATE_TYPE_CODE_OTHER = 5;

    /**
     *  日期类型编码名称（1：工作日；2：双休日，3：法定节假日，4：特殊节假日，5：其它）
     */
    public static final String DATE_TYPE_NAME_WEEKDAY = "工作日";
    public static final String DATE_TYPE_NAME_WEEKEND = "双休日";
    public static final String DATE_TYPE_NAME_LEGAL_HOLIDAY = "法定节假日";
    public static final String DATE_TYPE_NAME_SPECIPAL_HOLIDAY = "特殊节假日";
    public static final String DATE_TYPE_NAME_OTHER = "其它";
}
