package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/25 0:53
 */
@Getter
@Setter
@NoArgsConstructor
public class HazardStatInfoVo {

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
    private Integer totalNums;
}
