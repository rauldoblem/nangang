package com.taiji.emp.alarm.common.constant;

/**
 * Alarm 微服务全局常量类
 * */
public class AlarmGlobal {

    //预警处理状态
    /**
     * 未处理
     */
    public static final String ALAERT_NOT_HANDLE = "0";
    /**
     * 已忽略
     */
    public static final String ALAERT_IGNORED = "1";
    /**
     * 处置中
     */
    public static final String ALAERT_NOTICED = "2";
    /**
     * 已办结
     */
    public static final String ALAERT_FINISHED = "3";

    //最新反馈状态（0：未开始；1：进行中；2：已完成）
    /**
     * 未开始
     */
    public static final String ALAERT_FB_NOT_START = "0";
    /**
     * 进行中
     */
    public static final String ALAERT_FB_DOING = "1";
    /**
     * 已完成
     */
    public static final String ALAERT_FB_COMPLETED = "2";

    //接收状态（0：未接收；1：已接收）
    /**
     * 未接收
     */
    public static final String Alert_Notice_NOT_ACCEPT = "0";
    /**
     * 已接收
     */
    public static final String Alert_Notice_ACCEPTED = "1";

    //Alert字段IS_NOTICE的状态 是否通知（0：已通知 ，1：未通知）
    /**
     * 已通知
     */
    public static final String Alert_IS_NOTICE_YES = "0";
    /**
     * 未通知
     */
    public static final String Alert_IS_NOTICE_NO = "1";

    /**
     * 要过滤的用户名
     */
    public static final String INFO_USER_NAME = "yjzhzxdp";

}
