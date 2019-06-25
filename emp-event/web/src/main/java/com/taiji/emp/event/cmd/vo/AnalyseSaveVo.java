package com.taiji.emp.event.cmd.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AnalyseSaveVo {

    /**
     * 应急处置研判Vo对象 AnalyseVo
     */
    @Getter
    @Setter
    @NotNull(message = "AnalyseVo不能为null")
    private AnalyseVo eventAnalyse;

    /**
     * 附件对象id串(待赋值list)
     */
    @Getter
    @Setter
    private List<String> fileIds;

    /**
     * 附件对象id串(待删除list)
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;

}
