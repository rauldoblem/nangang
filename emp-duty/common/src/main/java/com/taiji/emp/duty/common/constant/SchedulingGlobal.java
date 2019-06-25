package com.taiji.emp.duty.common.constant;

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

    public static final int SCHEDULING_ZERO = 0;

    public static final Integer SCHEDULING_Monday = 1;  //周一
    public static final Integer SCHEDULING_Tuesday = 2; //周二
    public static final Integer SCHEDULING_Wednesday = 3;   //周三
    public static final Integer SCHEDULING_Thursday = 4;    //周四
    public static final Integer SCHEDULING_Friday = 5;  //周五
    public static final Integer SCHEDULING_Saturday = 6;    //周六
    public static final Integer SCHEDULING_Sunday = 0;  //周日

    public static final String SCHEDULING_FIRST_DAY = "01";  //当月的第一天

    public static final Integer SCHEDULING_ONE = 1;  //上个人还需要值班的1次
    public static final Integer SCHEDULING_TWO = 2; //上个人还需要值班的2次
    public static final Integer SCHEDULING_THREE = 3;   //上个人还需要值班的3次
    public static final Integer SCHEDULING_FOUR = 4;    //上个人还需要值班的4次
    public static final Integer SCHEDULING_FIVE = 5;  //上个人还需要值班的5次
    public static final Integer SCHEDULING_SIX = 6;    //上个人还需要值班的6次
    public static final Integer SCHEDULING_SEVEN = 7;    //上个人还需要值班的7次

    public static final String ITEM_DIC_CODE = "dayShiftPersons";

    /**
     * 首页工作检查单，固定班次名字
     */
    public static final String SHIFT_PATTERN_NAME = "白班";

    /**
     * 带班领导名字
     */
    public static final String DUTY_TEAM_NAME = "带班领导";

    /**
     * 值班领导班次名字
     */
    public static final String DUTY_TEAM_LEADER = "值班领导";

    /**
     * 值班干部班次名字
     */
    public static final String DUTY_TEAM_CADRE = "值班干部";


    /**
     * 值班人员列表导出名称
     */
    public static final String SCHEDULING_FILE_NAME = "值排班表";

    /**
     * 首页交接班时间范围可修改
     */
    public static final String SHIFT_TIME = "45";
}
