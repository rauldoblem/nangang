package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CaseSaveVo {

    /**
     * 案例管理 vo对象
     */
    @Getter@Setter
    @NotNull(message = "CaseEntityVo 不能为null")
    private CaseEntityVo caseEntityVo;

    /**
     *
     */
    @Getter@Setter
    private List<String> fileIds;

    /**
     *
     */
    @Getter
    @Setter
    private List<String> fileDeleteIds;
}
