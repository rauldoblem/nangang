package com.taiji.emp.duty.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 班次设置表 ShiftPattern
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_SHIFT_PATTERN")
public class ShiftPattern extends BaseEntity<String> implements DelFlag {

    public ShiftPattern() {}

    /**
     *  值班模式ID
     */
    @Getter
    @Setter
    @Column(name = "PATTERN_ID")
    @Length(max = 36,message = "值班模式ID patternId字段最大长度36")
    private String patternId;

    /**
     *  班次名称
     */
    @Getter
    @Setter
    @Column(name = "SHIFT_NAME")
    @Length(max = 20,message = "班次名称 shiftName字段最大长度20")
    private String shiftName;

    /**
     *  本班次开始时间（格式 ：如08:00）
     */
    @Getter
    @Setter
    @Column(name = "START_TIME")
    @Length(max = 10,message = "标注提示名称 startTime字段最大长度10")
    private String startTime;

    /**
     *  结束时间是否当日（0：当日；1：次日）
     */
    @Getter
    @Setter
    @Column(name = "IS_TODAY")
    @Length(max = 1,message = "结束时间是否当日 isToday字段最大长度1")
    private String isToday;

    /**
     *  本班次结束时间（格式 ：如08:00）
     */
    @Getter
    @Setter
    @Column(name = "END_TIME")
    @Length(max = 10,message = "本班次结束时间 endTime字段最大长度10")
    private String endTime;

    /**
     *  显示顺序
     */
    @Getter
    @Setter
    @Column(name = "SHIFT_SEQ")
    @Length(max = 1,message = "显示顺序 shiftSeq字段最大长度1")
    private String shiftSeq;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
