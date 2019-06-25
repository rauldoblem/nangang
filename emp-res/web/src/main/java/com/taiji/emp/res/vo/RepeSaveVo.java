package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RepeSaveVo {

    /**
     * 应急储备库vo对象
     */
    @Getter@Setter
    @NotNull(message = "RepertoryVo不能为null")
    private RepertoryVo repertory;


}
