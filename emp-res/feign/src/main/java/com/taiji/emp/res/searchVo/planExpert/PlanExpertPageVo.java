package com.taiji.emp.res.searchVo.planExpert;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanExpertPageVo {

    @Getter@Setter
    private int page;
    @Getter@Setter
    private int size;
    @Getter@Setter
    private String planId;
    @Getter@Setter
    private String eventGradeId;
}
