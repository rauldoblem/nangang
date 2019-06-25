package com.taiji.emp.base.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/29 15:16
 */
@Setter
@Getter
@NoArgsConstructor
public class ConditionSettingVo {

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

    @Length(max = 36,message = "eventGradeName字段最大长度4000")
    private String conditionSetting;
}
