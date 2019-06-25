package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MaterialStatVo {

    public MaterialStatVo() {
    }

    /**
     * 板块ID
     */
    @Getter
    @Setter
    private String orgId;

    /**
     * 物资类型大类ID
     */
    @Getter
    @Setter
    private String resTypeId;

    /**
     * 物资类型大类名称
     */
    @Getter
    @Setter
    private String resTypeName;

    /**
     * 本物资类型大类下各业务板块物资数量
     */
    @Getter
    @Setter
    private List<Integer> totalNum;

    @Getter
    @Setter
    private String orgCode;

}
