package com.taiji.emp.res.searchVo.expert;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ExpertListVo {

    @Getter@Setter
    private String name;
    @Getter@Setter
    private String unit;
    @Getter@Setter
    private List<String> eventTypeIds;
    //预案数字化查询相应的专家信息
    @Getter@Setter
    private List<String> expertIds;
    @Getter@Setter
    private String specialty;
    @Getter@Setter
    private String createOrgId;
    //已选专家IDS，在结果列表中，将这些专家去掉（预案数字化时使用）
    @Getter@Setter
    private List<String> selectedExpertIds;
}
