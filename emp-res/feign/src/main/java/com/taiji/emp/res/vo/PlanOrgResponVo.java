package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import java.util.List;

/**
 * 预案责任人、单位管理
 */
public class PlanOrgResponVo extends BaseVo<String>{

    public PlanOrgResponVo(){}

    /**
     * 应急组织
     */
    @Getter@Setter
    @Length(max = 36,message = "应急组织ID planOrgId字段最大长度36")
    private String planOrgId;

    /**
     * 应急组织名称
     */
    @Getter@Setter
    @Length(max = 100,message = "应急组织名称planOrgName字段最大长度100")
    private String planOrgName;

    /**
     * 责任主体类型:0个人，1单位
     */
    @Getter@Setter
    @Length(max = 1,message = "责任主体类型subjectType字段最大长度1")
    private String subjectType;

    /**
     * 应急职务
     */
    @Getter@Setter
    @Length(max = 200,message = "应急职务duty字段最大长度200")
    private String duty;

    /**
     *  职责
     */
    @Getter@Setter
    @Length(max = 2000,message = "职责responsibility字段最大长度2000")
    private String responsibility;

    @Getter
    @Setter
    private List<PlanOrgResponDetailVo> details;

    @Getter
    @Setter
    private List<String> planOrgIds;

}
