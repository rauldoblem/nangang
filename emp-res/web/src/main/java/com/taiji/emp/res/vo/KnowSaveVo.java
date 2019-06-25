package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class KnowSaveVo {

    /**
     * 应急知识vo对象
     */
    @Getter@Setter
    @NotNull(message = "KnowledgeVo不能为null")
    private KnowledgeVo knowledge;

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
