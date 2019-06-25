package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
public class TotalSurfaceWaterVo {

    public TotalSurfaceWaterVo(String name , String uint){
        this.name = name;
        this.unit = uint;
    }
    @Length(max = 50 ,message = "name字段最大长度50")
    private String name;

    @Length(max = 50 ,message = "value字段最大长度50")
    private String value;

    @Length(max = 50 ,message = "单位字段最大长度50")
    private String unit;
}
