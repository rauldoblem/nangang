package com.taiji.emp.duty.searchVo;

import com.taiji.emp.duty.vo.SchedulingVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class SchedulingTimeForShow {
    public SchedulingTimeForShow() {
    }

    /**
     *  班次ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "班次ID shiftPatternId字段最大长度36")
    private String shiftPatternId;

    /**
     *  班次名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "班次名称 shiftPatternName字段最大长度20")
    private String shiftPatternName;

    /**
     *  班次排序
     */
    @Getter
    @Setter
    private String shiftSeq;

    /**
     *  值班开始时间 (yyyy-MM-dd HH:mm)
     */
    @Getter
    @Setter
    private String startTime;

    /**
     *  值班结束时间(yyyy-MM-dd HH:mm)
     */
    @Getter
    @Setter
    private String endTime;

    @Getter
    @Setter
    private List<SchedulingVo> SchedulingDetail;
}
