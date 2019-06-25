package com.taiji.emp.res.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 预案责任人、单位管理
 */
public class PlanOrgResDetVo extends BaseVo<String>{

    public PlanOrgResDetVo(){}

    /**
     * vo
     */
    @Getter@Setter
    private PlanOrgResponVo orgRespons;


}
