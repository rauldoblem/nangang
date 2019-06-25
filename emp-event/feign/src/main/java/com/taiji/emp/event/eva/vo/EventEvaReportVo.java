package com.taiji.emp.event.eva.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 事件评估报告 实体类 feign EventEvaReportVo
 * @author qzp-pc
 * @date 2018年11月06日16:21:18
 */
public class EventEvaReportVo{

    public EventEvaReportVo() {}

    /**
     * 事件评估报告
     */
    @Getter
    @Setter
    private EventEvaReportDataVo eventEvaReport;

    /**
     * 评估报告相关附件ID数组
     */
    @Getter
    @Setter
    private List<String> fileIds;

    /**
     * 删除的附件ID数组
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;
}
