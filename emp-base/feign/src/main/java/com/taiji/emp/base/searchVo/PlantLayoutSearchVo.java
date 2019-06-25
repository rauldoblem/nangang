package com.taiji.emp.base.searchVo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

public class PlantLayoutSearchVo extends BasePageVo {

    public PlantLayoutSearchVo() {}

    /**
     * 外部地图组织机构编码
     */
    @Getter
    @Setter
    @Length(max = 60,message = "外部地图组织机构编码 gisOrgId字段最大长度60")
    private String gisOrgId;
}
