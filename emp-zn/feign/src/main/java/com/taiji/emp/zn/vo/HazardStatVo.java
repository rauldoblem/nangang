package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class HazardStatVo {

    public HazardStatVo() {
    }

    /**
     * 板块ID
     */
    @Getter
    @Setter
    private String orgId;

    /**
     * 危险源级别ID
     */
    @Getter
    @Setter
    private String danGradeId;

    /**
     * 危险源级别名称
     */
    @Getter
    @Setter
    private String danGradeName;

    /**
     * 本危险源级别下各业务板块重大危险源数量
     */
    @Getter
    @Setter
    private List<Integer> totalNums;

    @Getter
    @Setter
    private String orgCode;
}
