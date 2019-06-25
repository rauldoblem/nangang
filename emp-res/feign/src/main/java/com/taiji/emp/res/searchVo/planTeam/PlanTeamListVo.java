package com.taiji.emp.res.searchVo.planTeam;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanTeamListVo {

    @Getter@Setter
    private String planId;
    @Getter@Setter
    private String eventGradeId;
    @Getter@Setter
    private List<String> planIds;
}
