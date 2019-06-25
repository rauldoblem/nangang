package com.taiji.emp.res.searchVo.planBase;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanBasePageVo {

    @Getter@Setter
    private int page;
    @Getter@Setter
    private int size;
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String planTypeId;
    @Getter@Setter
    private List<String> eventTypeIds;
    @Getter@Setter
    private String createOrgId;
    @Getter@Setter
    private String planCaltreeId;
    @Getter@Setter
    private List<String> createOrgIds;
    @Getter@Setter
    private List<String> selectedPlanIds;
}
