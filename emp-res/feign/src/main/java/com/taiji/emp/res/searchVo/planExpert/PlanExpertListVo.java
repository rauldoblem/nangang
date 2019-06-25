package com.taiji.emp.res.searchVo.planExpert;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanExpertListVo {

    @Getter@Setter
    private String planId;
    @Getter@Setter
    private String eventGradeId;
    @Getter@Setter
    private List<String> planIds;

}
