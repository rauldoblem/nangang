package com.taiji.emp.duty.vo.dailylog;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 班次设置表 feign ShiftPatternVo
 */
public class ShiftPatternVo extends BaseVo<String> {

    public ShiftPatternVo() {}

    /**
     *  值班模式ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "值班模式ID patternId字段最大长度36")
    private String patternId;

    /**
     *  班次名称
     */
    @Getter
    @Setter
    @Length(max = 20,message = "班次名称 shiftName字段最大长度20")
    private String shiftName;

    /**
     *  本班次开始时间（格式 ：如08:00）
     */
    @Getter
    @Setter
    @Length(max = 10,message = "标注提示名称 startTime字段最大长度10")
    private String startTime;

    /**
     *  结束时间是否当日（0：当日；1：次日）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "结束时间是否当日 isToday字段最大长度1")
    private String isToday;

    /**
     *  本班次结束时间（格式 ：如08:00）
     */
    @Getter
    @Setter
    @Length(max = 10,message = "本班次结束时间 endTime字段最大长度10")
    private String endTime;

    /**
     *  显示顺序
     */
    @Getter
    @Setter
    @Length(max = 1,message = "显示顺序 shiftSeq字段最大长度1")
    private String shiftSeq;

}
