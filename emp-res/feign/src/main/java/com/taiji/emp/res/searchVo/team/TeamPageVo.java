package com.taiji.emp.res.searchVo.team;

import com.taiji.emp.res.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TeamPageVo extends BasePageVo {

    @Getter@Setter
    private String teamName;
    @Getter@Setter
    private List<String> teamTypeIds;
    @Getter@Setter
    private List<String> teamIds;
    @Getter@Setter
    private String teamPropertyId;
    @Getter@Setter
    private String unit;
    @Getter@Setter
    private String teamSpecialty;
    @Getter@Setter
    private String createOrgId;
    //已选队伍IDS，在结果列表中，将这些队伍去掉（预案数字化时使用）
    @Getter@Setter
    private List<String> selectedTeamIds;

}
