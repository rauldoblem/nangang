package com.taiji.emp.nangang.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class ConventionalFactorVo {
    @Length(max = 50 ,message = "二氧化硫字段最大长度50")
    private String so2;
    @Length(max = 50 ,message = "PM10字段最大长度50")
    private String pm10;
    @Length(max = 50 ,message = "二氧化氮字段最大长度50")
    private String no2;
    @Length(max = 50 ,message = "一氧化碳字段最大长度50")
    private String co;
    @Length(max = 50 ,message = "PM2.5字段最大长度50")
    private String pm2_5;
    @Length(max = 50 ,message = "臭氧字段最大长度50")
    private String o3;
    @Length(max = 50 ,message = "创建时间最大长度50")
    private String createTime;

    //下边三个属性是其他表组装过来的
//    @Length(max = 50 ,message = "大气压力字段最大长度50")
//    private String atmosphericPressure;
//
//    @Length(max = 50 ,message = "主导风向字段最大长度50")
//    private String windDirection;
//
//    @Length(max = 50 ,message = "雨量字段最大长度50")
//    private String rainfall;

}
