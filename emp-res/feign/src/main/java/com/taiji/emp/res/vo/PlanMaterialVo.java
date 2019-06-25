package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class PlanMaterialVo extends BaseVo<String> {

    /**
     * 预案ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "预案ID planId字段最大长度36")
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
     * 应急物资编码
     */
    @Getter
    @Setter
    private List<String> materialTypeIds;

    /**
     * 应急物资编码
     */
    @Getter
    @Setter
    @Length(max = 36,message = "应急物资编码materialTypeId字段最大长度36")
    private String materialTypeId;

    /**
     * 应急物资编码名称
     */
    @Getter
    @Setter
    @Length(max = 100,message = "应急物资编码名称materialTypeName字段最大长度100")
    private String itemName;
}
