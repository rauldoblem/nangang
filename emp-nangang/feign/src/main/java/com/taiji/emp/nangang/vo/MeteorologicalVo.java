package com.taiji.emp.nangang.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter
public class MeteorologicalVo {
    @Length(max = 50 ,message = "温度字段最大长度50")
    private String temperature;
    @Length(max = 50 ,message = "大气压力字段最大长度50")
    private String atmosphericPressure;
    @Length(max = 50 ,message = "湿度字段最大长度50")
    private String humidity;
    @Length(max = 50 ,message = "风速字段最大长度50")
    private String windSpeed;
    @Length(max = 50 ,message = "主导风向字段最大长度50")
    private String windDirection;
    @Length(max = 50 ,message = "创建时间最大长度50")
    private String createTime;
    //从ConventionalFactor中组装四个数据进来
    @Length(max = 50 ,message = "PM2.5字段最大长度50")
    private String pm2_5;
    @Length(max = 50 ,message = "PM10字段最大长度50")
    private String pm10;
    @Length(max = 50 ,message = "二氧化氮字段最大长度50")
    private String no2;
    @Length(max = 50 ,message = "一氧化碳字段最大长度50")
    private String co;
}
