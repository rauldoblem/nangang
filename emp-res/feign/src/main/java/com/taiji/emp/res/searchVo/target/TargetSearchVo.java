package com.taiji.emp.res.searchVo.target;

import com.taiji.emp.res.searchVo.BasePageVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 防护目标条件查询列表----分页
 * @author qzp-pc
 * @date 2018年11月19日14:35:04
 */
public class TargetSearchVo extends BasePageVo {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<String> targetTypeIds;

    @Getter
    @Setter
    private String unit;

    /**
     * 浙能需求新增字段
     */
    @Getter@Setter
    private String createOrgId;

}
