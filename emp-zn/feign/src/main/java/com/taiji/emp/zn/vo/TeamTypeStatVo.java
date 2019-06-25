package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 按应急队伍类型统计结果类Vo
 * @author qizhijie-pc
 * @date 2018年12月20日10:57:07
 */
public class TeamTypeStatVo {

    public TeamTypeStatVo(){}

    @Getter@Setter
    private String teamTypeId;

    @Getter@Setter
    private String teamTypeName;

    @Getter@Setter
    private int totalNum;

}
