package com.taiji.emp.duty.searchVo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class CalSchedulingForOrg {

    public CalSchedulingForOrg() {
    }

    /**
     *  所属单位ID
     */
    @Getter
    @Setter
    @Length(max = 36,message = "所属单位ID orgId字段最大长度36")
    private String orgId;

    /**
     *  所属单位名称
     */
    @Getter
    @Setter
    @Length(max = 50,message = "所属单位名称 orgName字段最大长度50")
    private String orgName;

    /**
     *  当天本组织机构下的所有值班人员姓名、移动电话
     */
    @Getter
    @Setter
    private List<DutyMan> dutyManList;

}
