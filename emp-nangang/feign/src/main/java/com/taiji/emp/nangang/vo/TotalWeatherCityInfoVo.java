package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/11/19 16:50
 */
@Setter
@Getter
@NoArgsConstructor
public class TotalWeatherCityInfoVo {

    @Length(max =50,message = "city字段最大长度50")
    private String city;

    @Length(max =50,message = "cityId字段最大长度50")
    private String cityId;

    @Length(max =50,message = "parent字段最大长度50")
    private String parent;

    @Length(max =50,message = "updateTime字段最大长度50")
    private String updateTime;
}
