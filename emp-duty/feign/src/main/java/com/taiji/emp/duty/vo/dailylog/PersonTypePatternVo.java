package com.taiji.emp.duty.vo.dailylog;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 值班人员设置表 feign PersonTypePatternVo
 */
public class PersonTypePatternVo extends BaseVo<String> {

    public PersonTypePatternVo() {}

    /**
     *  值班模式ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "值班模式ID patternId字段最大长度36")
    private String patternId;

    /**
     *  每日值班类型ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "每日值班类型ID teamId字段最大长度36")
    private String teamId;

    /**
     *  值班分组名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "值班分组名称 teamName字段最大长度50")
    private String teamName;

    /**
     *  值班分组的值班类型编码（0：按班次值班，1：按天值班）
     */
    @Getter
    @Setter
    @Length(max = 1,message = "值班分组的值班类型编码 dutyTypeCode字段最大长度1")
    private String dutyTypeCode;

}
