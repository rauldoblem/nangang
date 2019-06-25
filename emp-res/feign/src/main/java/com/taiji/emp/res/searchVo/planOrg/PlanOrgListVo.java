package com.taiji.emp.res.searchVo.planOrg;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanOrgListVo {

    @Getter
    @Setter
    private String planId;

    @Getter
    @Setter
    private String eventGradeId;

    @Getter
    @Setter
    private List<String> planIds;

    @Getter
    @Setter
    private String isMain;
}
