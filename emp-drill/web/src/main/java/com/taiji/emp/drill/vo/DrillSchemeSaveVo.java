package com.taiji.emp.drill.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 演练方案 saveVo DrillSchemeSaveVo
 * @author qzp-pc
 * @date 2018年11月05日10:20:18
 */
public class DrillSchemeSaveVo {

    public DrillSchemeSaveVo() {}

    @Getter
    @Setter
    DrillSchemeVo drillSchemeVo;

    /**
     * 附件
     */
    @Getter
    @Setter
    List<String> fileIds;

    /**
     * 附件
     */
    @Getter
    @Setter
    List<String> fileDeleteIds;
}
