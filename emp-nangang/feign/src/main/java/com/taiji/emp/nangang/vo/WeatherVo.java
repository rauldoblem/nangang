package com.taiji.emp.nangang.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/11/19 15:15
 */
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherVo{

    @JsonProperty("date")
    @Length(max =50,message = "date字段最大长度50")
    private String weatherDate;

    @Length(max =50,message = "空气湿度字段最大长度50")
    private String humidity;

    @Length(max =50,message = "空气温度字段最大长度50")
    private String temperature;

    @Length(max =50,message = "最高气温字段最大长度50")
    private String high;

    @Length(max =50,message = "最低气温字段最大长度50")
    private String low;

    @Length(max =50,message = "空气指数字段最大长度50")
    private String aqi;

    @JsonProperty("fx")
    @Length(max =50,message = "风向字段最大长度50")
    private String windDirection;

    @JsonProperty("fl")
    @Length(max =50,message = "风力字段最大长度50")
    private String windPower;

    @JsonProperty("type")
    @Length(max =50,message = "天气类型字段最大长度50")
    private String weatherType;

}
