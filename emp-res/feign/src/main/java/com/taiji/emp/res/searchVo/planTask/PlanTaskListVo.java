package com.taiji.emp.res.searchVo.planTask;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanTaskListVo {

    @Getter@Setter
    private String principalName;
    @Getter@Setter
    private String title;
    @Getter@Setter
    private String planId;
    @Getter@Setter
    private String eventGradeId;
    @Getter@Setter
    private List<String> princiOrgIds;
    @Getter@Setter
    private List<String> planIds;
}
