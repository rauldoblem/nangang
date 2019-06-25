package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlanMainOrgVo {
    public PlanMainOrgVo() {
    }

    @Getter
    @Setter
    private String orgName;

    @Getter
    @Setter
    private List<PlanOrgResponVo> ecOrgRespons;
}
