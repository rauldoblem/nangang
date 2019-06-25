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
 * 值班人员设置表 PersonTypePattern
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "ED_PERSON_TYPE_PATTERN")
public class PersonTypePattern extends BaseEntity<String> implements DelFlag {

    public PersonTypePattern() {}

    /**
     *  值班模式ID
     */
    @Getter
    @Setter
    @Column(name = "PATTERN_ID")
    @Length(max = 36,message = "值班模式ID patternId字段最大长度36")
    private String patternId;

    /**
     *  每日值班类型ID
     */
    @Getter
    @Setter
    @Column(name = "DUTY_TEAM_ID")
    @Length(max = 36,message = "每日值班类型ID teamId字段最大长度36")
    private String teamId;

    /**
     *  值班分组名称
     */
    @Getter
    @Setter
    @Column(name = "DUYT_TNAME")
    @Length(max = 50,message = "值班分组名称 teamName字段最大长度50")
    private String teamName;

    /**
     *  值班分组的值班类型编码（0：按班次值班，1：按天值班）
     */
    @Getter
    @Setter
    @Column(name = "T_TYPE_CODE")
    @Length(max = 1,message = "值班分组的值班类型编码 dutyTypeCode字段最大长度1")
    private String dutyTypeCode;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1,message = "删除标识delFlag字段最大长度1")
    private String delFlag;
}
