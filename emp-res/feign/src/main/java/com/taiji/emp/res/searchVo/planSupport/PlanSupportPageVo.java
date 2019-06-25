package com.taiji.emp.res.searchVo.planSupport;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanSupportPageVo {

    @Getter@Setter
    private int page;
    @Getter@Setter
    private int size;
    @Getter@Setter
    private String planId;
    @Getter@Setter
    private String eventGradeId;
}
