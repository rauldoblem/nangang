package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/11/30 14:18
 */
@Getter
@Setter
@NoArgsConstructor
public class BayonetFlowProportionVo {

    @Length(max = 50 ,message = "卡口名称字段最大长度50")
    private String bayonetName;

    /**
     * 流量（输出小数 or 百分数）
     */
    @Length(max = 50 ,message = "流量字段最大长度50")
    private String flow;
}
