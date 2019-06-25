package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/23 17:48
 */
@Setter
@Getter
@NoArgsConstructor
public class TotalWeatherVo {
    @Length(max = 50,message = "状态码字段最大长度50")
    private String code;

    @Length(max = 50,message = "信息字段最大长度50")
    private String msg;

    private WeatherVo data;
}
