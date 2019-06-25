package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;

public abstract class BasePageVo {
    @Getter
    @Setter
    private int page;
    @Getter@Setter
    private int size;
}
