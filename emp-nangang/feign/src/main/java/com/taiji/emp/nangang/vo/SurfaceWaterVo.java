package com.taiji.emp.nangang.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter
public class SurfaceWaterVo extends BaseVo<String> {
    @Length(max = 50 ,message = "水温字段最大长度50")
    private String waterTemperature;
    @Length(max = 50 ,message = "PH值字段最大长度50")
    private String ph;
    @Length(max = 50 ,message = "电导率字段最大长度50")
    private String conductivity;
    @Length(max = 50 ,message = "浊度字段最大长度50")
    private String turbidity;
    @Length(max = 50 ,message = "溶解氧字段最大长度50")
    private String dissolvedOxygen;
    @Length(max = 50 ,message = "氨氮字段最大长度50")
    private String ammoniaNitrogen;
    @Length(max = 50 ,message = "总磷字段最大长度50")
    private String totalPhosphorus;
    @Length(max = 50 ,message = "总氮字段最大长度50")
    private String totalNitrogen;
    @Length(max = 50 ,message = "总有机氮字段最大长度50")
    private String totalOrganicNitrogen;
}
