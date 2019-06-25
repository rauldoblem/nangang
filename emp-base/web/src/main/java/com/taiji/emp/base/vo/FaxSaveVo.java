package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class FaxSaveVo extends BaseSaveVo {
    @Getter@Setter
    @NotNull(message = "FaxVo不能为null")
    private FaxVo fax;
}
