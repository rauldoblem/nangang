package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PlanBaseSaveVo {

    /**
     * 预案管理基础vo对象
     */
    @Getter
    @Setter
    @NotNull(message = "PlanBaseVo不能为null")
    private PlanBaseVo plan;

    /**
     * 附件对象id串(待赋值list)
     */
    @Getter@Setter
    private List<String> fileIds;

    /**
     * 附件对象id串(待删除list)
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;
}
