package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/24 23:33
 */
@Setter
@Getter
@NoArgsConstructor
public class MaterialStatInfoVo {
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
    private Integer totalNum;
}
