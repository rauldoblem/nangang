package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class PlanSupportVo extends BaseVo<String> {

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
     * 社会依托资源ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "社会依托资源ID supportId字段最大长度36")
    private String supportId;

    /**
     * 社会依托资源id集合
     */
    @Getter
    @Setter
    private List<String> supportIds;
}
