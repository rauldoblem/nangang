package com.taiji.emp.duty.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 值班表班次值班  feign SchedulingTimeVo
 */
public class SchedulingTimeVo extends IdVo<String> {

    public SchedulingTimeVo() {}


    /**
     *  班次ID
     */
    @Getter
    @Setter
    private String shiftPatternId;

    /**
     *  班次名称
     */
    @Getter
    @Setter
    private String shiftPatternName;

    /**
     *  班次顺序
     */
    @Getter
    @Setter
    private String shiftSeq;

    /**
     *  值班开始时间 (yyyy-MM-dd HH:mm:SS)
     */
    @Getter
    @Setter
    private String startTime;

    /**
     *  值班结束时间(yyyy-MM-dd HH:mm:SS)
     */
    @Getter
    @Setter
    private String endTime;

    /**
     * 值班人员姓名
     */
    @Getter
    @Setter
    private List<SchedulingVo> SchedulingDetail;
}
