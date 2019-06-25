package com.taiji.base.common.constant.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于消息组件，msgType配置类，与数据库对应
 * */
public class MsgSendGlobal {

    public final static Map<String,MsgConfig> msgTypeMap = new HashMap<>();

    /**
     * 通知公告
     */
    public final static String MSG_TYPE_NOTICE_NEWS_ID = "0001";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("通知公告");
        msgConfig.setCode("emg_notice_news");
        msgConfig.setPath("emg_notice");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_NOTICE_NEWS_ID,msgConfig);
    }
    public final static String MSG_TYPE_NOTICE_UNDO_ID = "0002";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("通知公告");
        msgConfig.setCode("emg_notice_todo");
        msgConfig.setPath("emg_notice");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_NOTICE_UNDO_ID,msgConfig);
    }

    /**
     * 预警通知
     */
    public final static String MSG_TYPE_ALERT_NEWS_ID = "0003";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("预警通知");
        msgConfig.setCode("emg_alert_news");
        msgConfig.setPath("emg_alert");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_ALERT_NEWS_ID,msgConfig);
    }
    public final static String MSG_TYPE_ALERT_UNDO_ID = "0004";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("预警通知");
        msgConfig.setCode("emg_alert_todo");
        msgConfig.setPath("emg_alert");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_ALERT_UNDO_ID,msgConfig);
    }

    /**
     * 信息接报
     */
    public final static String MSG_TYPE_INFO_NEWS_ID = "0005";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("信息接报");
        msgConfig.setCode("emg_infomags_news");
        msgConfig.setPath("emg_infomags");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_INFO_NEWS_ID,msgConfig);
    }
    public final static String MSG_TYPE_INFO_UNDO_ID = "0006";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("信息接报");
        msgConfig.setCode("emg_alert_todo");
        msgConfig.setPath("emg_infomags");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_INFO_UNDO_ID,msgConfig);
    }

    /**
     * 任务管理
     */
    public final static String MSG_TYPE_TASK_NEWS_ID = "0007";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("任务管理");
        msgConfig.setCode("emg_taskmags_news");
        msgConfig.setPath("emg_taskmags");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_TASK_NEWS_ID,msgConfig);
    }
    public final static String MSG_TYPE_TASK_UNDO_ID = "0008";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("任务管理");
        msgConfig.setCode("emg_taskmags_todo");
        msgConfig.setPath("emg_taskmags");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_TASK_UNDO_ID,msgConfig);
    }

    /**
     * 演练计划
     */
    public final static String MSG_TYPE_DRILL_PLAN_NEWS_ID = "0009";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("演练计划");
        msgConfig.setCode("emg_drillplan_news");
        msgConfig.setPath("emg_drillplan");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_DRILL_PLAN_NEWS_ID,msgConfig);
    }
    public final static String MSG_TYPE_DRILL_PLAN_UNDO_ID = "0010";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("演练计划");
        msgConfig.setCode("emg_drillplan_todo");
        msgConfig.setPath("emg_drillplan");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_DRILL_PLAN_UNDO_ID,msgConfig);
    }

    /**
     * 演练方案
     */
    public final static String MSG_TYPE_DRILL_SCHEME_NEWS_ID = "0011";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("演练方案");
        msgConfig.setCode("emg_drillscheme_news");
        msgConfig.setPath("emg_drillscheme");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_DRILL_SCHEME_NEWS_ID,msgConfig);
    }
    public final static String MSG_TYPE_DRILL_SCHEME_UNDO_ID = "0012";
    static {
        MsgConfig msgConfig = new MsgConfig();
        msgConfig.setModuleName("演练方案");
        msgConfig.setCode("emg_drillscheme_todo");
        msgConfig.setPath("emg_drillscheme");
        msgConfig.setIcon("");
        msgTypeMap.put(MSG_TYPE_DRILL_SCHEME_UNDO_ID,msgConfig);
    }

    /**
     * 接报信息分发消息标题
     */
    public final static String MSG_INFO_TITLE_SEND = "接报信息";
    /**
     * 接报信息退回标题
     */
    public final static String MSG_INFO_TITLE_RETURN = "接报信息退回";

    /**
     * 通知公告信息标题
     */
    public final static String MSG_INFO_NOTICE_TITLE_SEND = "通知公告";

    /**
     * 通知公告信息反馈标题
     */
    public final static String MSG_INFO_NOTICE_TITLE_FEEDBACK = "通知公告反馈";

    /**
     * 演练计划信息标题
     */
    public final static String MSG_INFO_PLAN_TITLE = "演练计划";

    /**
     * 演练方案信息标题
     */
    public final static String MSG_INFO_SCHEME_TITLE = "演练方案";

    /**
     * 调度任务信息标题
     */
    public final static String MSG_INFO_TASK_TITLE = "调度任务";

    /**
     * 调度任务信息标题
     */
    public final static String MSG_INFO_FEEDBACK_TITLE = "任务反馈";

    /**
     * 预警通知下发消息标题
     */
    public final static String MSG_INFO_ALARM_NOTICE_TITLE = "预警通知";

    /**
     * 预警通知反馈消息标题
     */
    public final static String MSG_INFO_ALARM_NOTICE_FB_TITLE = "预警通知反馈";

    /**
     * 生成新事件发消息标题
     */
    public final static String MSG_INFO_NEW_TITLE_SEND = "生成新事件";
}
