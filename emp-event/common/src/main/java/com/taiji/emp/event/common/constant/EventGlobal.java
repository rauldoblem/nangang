package com.taiji.emp.event.common.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * event 微服务全局变量类
 */
public class EventGlobal {

    /**
     * 查询选择标签类型：00信息录入，01接收待办；10信息已办，11接收已办
     * TYPE为00，则IM_ACCEPT_DEAL中CREATE_ORG_ID为当前登录者所属单位ID，且DEAL_FLAG为0；
     * TYPE为01，则IM_ACCEPT_DEAL中DEAL_ORG_ID为当前登录者所属单位ID，且DEAL_FLAG为0；
     * TYPE为10，则IM_ACCEPT_DEAL中CREATE_ORG_ID为当前登录者所属单位ID，且DEAL_FLAG不等于0；
     * TYPE为11，则IM_ACCEPT_DEAL中DEAL_ORG_ID为当前登录者所属单位ID，且DEAL_DEAL_FLAG不等于0；
     * 获取ACCEPT_ID集合后再根据其他查询条件查询IM_ACCEPT表中对应的具体接报信息返回
     */
    public static final String INFO_DISPATCH_INFO_CREATE = "00"; //信息录入
    public static final String INFO_DISPATCH_ACCEPT_UNDO = "01"; //接收待办
    public static final String INFO_DISPATCH_INFO_DONE = "10"; //信息已办
    public static final String INFO_DISPATCH_ACCEPT_DONE = "11"; //接收已办

    /**
     * 接收后处理状态：0未发送，1已发送，2已退回，3已办结，4已生成事件
     */
    public static final String INFO_DISPATCH_UNSEND = "0";//未发送
    public static final String INFO_DISPATCH_SEND = "1";//已发送
    public static final String INFO_DISPATCH_RETURN = "2";//已退回
    public static final String INFO_DISPATCH_FINISH = "3";//已办结
    public static final String INFO_DISPATCH_GENERATE_EVENT = "4";//已生成事件

    /**
     * 接报操作编码： 发送1、退回2、办结3、生成事件4、更新事件5
     */
    public static final String INFO_DISPATCH_BUTTON_SEND = "1";//发送
    public static final String INFO_DISPATCH_BUTTON_RETURN = "2";//退回
    public static final String INFO_DISPATCH_BUTTON_FINISH = "3";//办结
    public static final String INFO_DISPATCH_BUTTON_GENERATE_EVENT = "4";//生成事件
    public static final String INFO_DISPATCH_BUTTON_UPDATE_EVENT = "5";//更新事件

    public static final Set<String> buttonTypeSet = new HashSet<>();
    static {
        buttonTypeSet.add(INFO_DISPATCH_BUTTON_SEND);
        buttonTypeSet.add(INFO_DISPATCH_BUTTON_RETURN);
        buttonTypeSet.add(INFO_DISPATCH_BUTTON_FINISH);
        buttonTypeSet.add(INFO_DISPATCH_BUTTON_GENERATE_EVENT);
        buttonTypeSet.add(INFO_DISPATCH_BUTTON_UPDATE_EVENT);
    }

    /**
     * 事件处置状态：0处置中 1处置结束  2 已评估 3 已归档
     */
    public static final String EVENT_HANDLE_DOING = "0"; //处置中
    public static final String EVENT_HANDLE_FINISH = "1"; //处置结束
    public static final String EVENT_HANDLE_EVA = "2"; //已评估
    public static final String EVENT_HANDLE_FILE = "3"; //已归档
    /**
     * 事件处置状态set：1处置结束  2 已评估 3 已归档
     */
    public static final Set<String> eventHandleTypeSet = new HashSet<>();
    static {
        eventHandleTypeSet.add(EVENT_HANDLE_FINISH);
        eventHandleTypeSet.add(EVENT_HANDLE_EVA);
        eventHandleTypeSet.add(EVENT_HANDLE_FILE);
    }

    /**
     * 处置跟踪-应急任务 状态：0.未下发1.已下发2.已完成
     */
    public static final String EVENT_TASK_UNSEND = "0"; //未下发0
    public static final String EVENT_TASK_SEND  = "1"; //已下发1
    public static final String EVENT_TASK_DONE  ="2"; //已完成2

    /**
     * 处置跟踪-时间轴 反馈任务是否包含附件：0.不包含附件1.包含
     */
    public static final String EVENT_TASK_FEEDBACK_NOT_COVER = "0"; //不包含附件0
    public static final String EVENT_TASK_FEEDBACK_COVER  = "1"; //包含附件1



    /**
     * 提交标识
     */
    public static final String SAVE_FLAG = "1";//评估完成


    /**
     * 过程在线节点创建 -- 系统创建
     */
    public static final String PROCESS_SYS_CREATE = "0";
    /**
     * 过程在线节点创建 -- 手动创建
     */
    public static final String PROCESS_HANDLE_CREATE = "1";
    /**
     * 导出文件模板路径
     */
    public static final String TEMPLPATH = "/opt/uploadFile_boot/滨海新区突发事件信息报告表.docx";

}
