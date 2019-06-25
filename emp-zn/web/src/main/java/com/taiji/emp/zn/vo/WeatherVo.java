package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/21 23:19
 */
@Getter
@Setter
@NoArgsConstructor
public class WeatherVo {

    @Length(max = 50,message = "省会城市字段最大长度50")
    private String provincialCity;

    @Length(max = 50,message = "城市字段最大长度50")
    private String city;

    @Length(max = 50,message = "时间字段最大长度50")
    private String time;

    @Length(max = 255,message = "描述字段最大长度50")
    private String description;

    @Length(max = 50,message = "温度字段最大长度50")
    private String temperature;

    private List<WeatherInfoVo> weathers;


}
