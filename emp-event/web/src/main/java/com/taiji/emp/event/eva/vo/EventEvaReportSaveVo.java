package com.taiji.emp.event.eva.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 事件评估报告  EventEvaReportSaveVo
 * @author qzp-pc
 * @date 2018年11月06日16:45:18
 */
public class EventEvaReportSaveVo {

    public EventEvaReportSaveVo() {}

    /**
     * 提交标识，暂存0，评估完成1
     */
    @Getter
    @Setter
    private String saveFlag;

    /**
     * 数据
     */
    @Getter
    @Setter
    private EventEvaReportVo data;
}
