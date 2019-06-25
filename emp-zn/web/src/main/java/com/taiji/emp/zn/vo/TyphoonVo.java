package com.taiji.emp.zn.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.NumberFormat;

/**
 * @author yhcookie
 * @date 2018/12/21 23:43
 */
@Setter
@Getter
@NoArgsConstructor
public class TyphoonVo {

    @Length(max = 50,message = "台风标识字段最大长度50")
    private String typhoonid;

    @Length(max = 50,message = "台风名字字段最大长度50")
    private String typhoonname;

    @Length(max = 50,message = "台风时间字段最大长度50")
    private String typhoontime;

    //纬度
    private Double lat;

    //经度
    private Double lon;

    @Length(max = 50,message = "风力字段最大长度50")
    private String windpower;

    //风速
    private Double windspeed;

    //气压
    private Double airpressure;

    @Length(max = 50,message = "预报时间最大长度50")
    private String predictiontime;



}
