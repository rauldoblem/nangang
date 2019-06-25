package com.taiji.emp.event.cmd.searchVo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 根据数字化信息生成任务信息Vo
 * @author SunYi
 */

public class BuildTaskVo {

    /**
     * 应急任务ID
     */
    @Getter
    @Setter
    @NotNull(message = "planIds不能为null")
    private List<String> planIds;

    /**处置方案*/
    @Getter@Setter
    private String schemeId;

    /**处置方案*/
    @Getter@Setter
    private String schemeName;

    /**事件*/
    @Getter@Setter
    private String eventId;

    /**事件*/
    @Getter@Setter
    private String eventName;
}
