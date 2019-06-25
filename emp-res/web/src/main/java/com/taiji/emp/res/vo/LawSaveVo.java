package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class LawSaveVo extends BaseSaveVo{
    /**
     * 法律法规vo对象
     */
    @Getter@Setter
    @NotNull(message = "LawVo不能为null")
    private LawVo law;
}
