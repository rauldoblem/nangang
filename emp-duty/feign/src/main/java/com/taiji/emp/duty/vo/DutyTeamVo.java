package com.taiji.emp.duty.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 值班人员分组表  feign DutyTeamVo
 */
public class DutyTeamVo extends BaseVo<String> {

    public DutyTeamVo() {}

    /**
     *  分组名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "分组名称 teamName字段最大长度50")
    private String teamName;

    /**
     *  分组所属单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "分组所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  分组所属单位名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "分组所属单位名称 orgName字段最大长度50")
    private String orgName;

    /**
     *  是否交接班，0否，1是
     */
    @Getter
    @Setter
    @Length(max = 1,message = "是否交接班 isShift字段最大长度1")
    private String isShift;

    /**
     *  排序字段
     */
    @Getter
    @Setter
    @Min(value=0,message = "排序编码最小为0")
    @Max(value=9999,message = "排序编码最大为9999")
    private Integer orderTeam;

    /**
     *  分组ids
     */
    @Getter
    @Setter
    private List<String> dutyTeamIds;

}
