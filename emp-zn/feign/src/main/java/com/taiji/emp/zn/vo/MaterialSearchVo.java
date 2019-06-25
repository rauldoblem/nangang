package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 统计板块的查询Vo
 */
public class MaterialSearchVo {

    public MaterialSearchVo() {}

    @Getter
    @Setter
    private List<String> orgCodes;
}
