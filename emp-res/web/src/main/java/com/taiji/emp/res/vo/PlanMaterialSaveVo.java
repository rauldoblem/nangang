package com.taiji.emp.res.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 预案的封装类
 * @author qzp
 * @date 2018/11/30 11:57:09
 */
public class PlanMaterialSaveVo {

    /**
     * 预案ID
     */
    @Getter
    @Setter
    private String planId;

    /**
     * 响应级别ID（事件等级ID）
     */
    @Getter@Setter
    @Length(max = 36,message = "响应级别ID（事件等级ID）eventGradeID字段最大长度36")
    private String eventGradeId;

    /**
     * 响应级别名称（事件等级名称）
     */
    @Getter@Setter
    @Length(max = 100,message = "响应级别名称（事件等级名称）eventGradeName字段最大长度100")
    private String eventGradeName;

    /**
     * 应急物资
     */
    @Getter
    @Setter
    List<PlanMaterialVo> materialList;


}
