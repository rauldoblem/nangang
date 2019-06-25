package com.taiji.emp.duty.entity.dailyShift;

import com.taiji.micro.common.entity.IdEntity;
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
 * 交接班，值班日志 管理实体类 DailyLogShift
 * @author SunYi
 * @date 2018年11月1日
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_DAILYLOG_SHIFT")
public class DailyLogShift extends IdEntity<String>{

    public DailyLogShift(){}

    /**
     * 交接班ID
     */
    @Getter
    @Setter
    @Column(name = "DAILYSHIFT_ID")
    @Length(max = 36,message = "交接班ID dailyShiftId 字段最大长度36")
    private String dailyShiftId;

    /**
     * 值班日志ID
     */
    @Getter
    @Setter
    @Column(name = "DAILYLOG_ID")
    @Length(max = 36,message = "值班日志ID dailyLogId 字段最大长度36")
    private String dailyLogId;

}
