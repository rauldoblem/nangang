package com.taiji.emp.res.searchVo.hazard;

import com.taiji.emp.res.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class HazardPageVo extends BasePageVo {
    @Getter@Setter
    private String name;
    @Getter@Setter
    private List<String> danTypeIds;
    @Getter@Setter
    private String danGradeId;
    @Getter@Setter
    private String unit;
    @Getter@Setter
    private String majorHazard;
    @Getter@Setter
    private String createOrgId;
}
