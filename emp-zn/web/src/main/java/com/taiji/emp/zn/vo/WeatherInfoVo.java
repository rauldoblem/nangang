package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/21 23:35
 */
@Getter
@Setter
@NoArgsConstructor
public class WeatherInfoVo {

    @Length(max = 50,message = "meteorology字段最大长度50")
    private String meteorology;

    @Length(max = 50,message = "type字段最大长度50")
    private String type;

    @Length(max = 50,message = "minAir字段最大长度50")
    private String minAir;

    @Length(max = 50,message = "maxAir字段最大长度50")
    private String maxAir;

    @Length(max = 50,message = "windCondition最大长度50")
    private String windCondition;

    @Length(max = 50,message = "icon1最大长度50")
    private String icon1;

    @Length(max = 50,message = "icon2最大长度50")
    private String icon2;

}
