package com.taiji.emp.base.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * @author yhcookie
 * @date 2018/12/29 15:09
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Setter
@Getter
@Entity
@Table(name = "BS_CONDITION_SETTING")
public class ConditionSetting {

    @Id
    @GenericGenerator(
            name = "customUUIDGenerator",
            strategy = "com.taiji.micro.common.id.CustomSortUUIDGenerator"
    )
    @GeneratedValue(
            generator = "customUUIDGenerator"
    )
    @Length(max = 36,message = "id字段最大长度36")
    public String id;

    @Length(max = 36,message = "eventTypeId字段最大长度36")
    private String eventTypeId;

    @Length(max = 36,message = "eventTypeName字段最大长度100")
    private String eventTypeName;

    @Length(max = 36,message = "eventGradeId字段最大长度36")
    private String eventGradeId;

    @Length(max = 36,message = "eventGradeName字段最大长度100")
    private String eventGradeName;

    @Length(max = 4000,message = "eventGradeName字段最大长度4000")
    @Column(name="CONDITIONG_SET")
    private String conditionSetting;
}
