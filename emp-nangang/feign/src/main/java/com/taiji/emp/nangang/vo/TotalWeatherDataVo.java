package com.taiji.emp.nangang.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/11/19 17:06
 */
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalWeatherDataVo {

    @JsonProperty("shidu")
    @Length(max =50,message = "空气湿度字段最大长度50")
    private String humidity;

    @JsonProperty("wendu")
    @Length(max =50,message = "空气温度字段最大长度50")
    private String temperature;

    private WeatherVo yesterday;

    private List<WeatherVo> forecast;
}
