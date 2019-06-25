package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/6 14:54
 */
@NoArgsConstructor
@Getter
@Setter
public class BayonetFlowVo {

    @Length(max = 50,message = "卡口名称字段长度最大50")
    private String bayonetName;

    @Length(max = 50,message = "流量字段长度最大50")
    private String flow;
}
