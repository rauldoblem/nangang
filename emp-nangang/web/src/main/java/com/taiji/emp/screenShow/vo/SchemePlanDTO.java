package com.taiji.emp.screenShow.vo;

import com.taiji.emp.event.cmd.vo.CmdPlanVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SchemePlanDTO {

    public SchemePlanDTO(){}
    public SchemePlanDTO(String id,String schemeName,String eventId,List<CmdPlanVo> ecPlans){
        this.id = id;
        this.schemeName = schemeName;
        this.eventId = eventId;
        this.ecPlans = ecPlans;
    }

    @Getter@Setter
    private String id;
    @Getter@Setter
    private String schemeName;
    @Getter@Setter
    private String eventId;
    @Getter@Setter
    private List<CmdPlanVo> ecPlans;

}
