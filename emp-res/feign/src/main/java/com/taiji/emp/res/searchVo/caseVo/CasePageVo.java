package com.taiji.emp.res.searchVo.caseVo;

import com.taiji.emp.res.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



public class CasePageVo extends BasePageVo {

    @Getter@Setter
    private String title;
    @Getter@Setter
    private List<String> eventTypeIds;
    @Getter@Setter
    private String eventGradeId;
    @Getter@Setter
    private String sourceFlag;
    /**
     * 开始时间
     */
    @Getter@Setter
    private String occurStartTime;
    /**
     * 结束时间
     */
    @Getter@Setter
    private String occurEndTime;
    @Getter@Setter
    private String createOrgId;

}
