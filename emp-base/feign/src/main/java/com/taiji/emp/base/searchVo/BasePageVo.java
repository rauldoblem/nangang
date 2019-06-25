package com.taiji.emp.base.searchVo;

import lombok.Getter;
import lombok.Setter;

public abstract class BasePageVo {
    @Getter
    @Setter
    private int page;
    @Getter@Setter
    private int size;
}
