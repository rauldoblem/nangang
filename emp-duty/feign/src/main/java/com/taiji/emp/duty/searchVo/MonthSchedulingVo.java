package com.taiji.emp.duty.searchVo;

import com.taiji.emp.duty.vo.SchedulingVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MonthSchedulingVo {

    /**
     *  排班标识
     */
    @Getter
    @Setter
    private String schedulingFlag;

    /**
     *  值排班
     */
    @Getter
    @Setter
    private List<SchedulingSearchVo> calScheduling;

}
