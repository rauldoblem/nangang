package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 重点防护目标按目标类型统计结果类Vo
 * @author qizhijie-pc
 * @date 2018年12月20日10:57:07
 */
public class TargetTypeStatVo {

    public TargetTypeStatVo(){}

    @Getter@Setter
    private String targetTypeId;

    @Getter@Setter
    private String targetTypeName;

    @Getter@Setter
    private int totalNum;

}
