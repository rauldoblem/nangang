package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class BaseSaveVo {
    /**
     * 附件对象id串
     */
    @Getter
    @Setter
    @NotNull(message = "fileIds不能为null")
    private List<String> fileIds;
    /**
     * 附件对象id串(待删除list)
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;
}
