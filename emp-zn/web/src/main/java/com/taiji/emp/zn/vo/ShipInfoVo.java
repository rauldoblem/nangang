package com.taiji.emp.zn.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/21 22:58
 */
@Getter
@Setter
@NoArgsConstructor
public class ShipInfoVo {

    @Length(max = 50,message = "船舶名字拼音字段最大长度50")
    private String name;

    @Length(max = 50,message = "船舶类型字段最大长度50")
    private String shiptype;

    @Length(max = 50,message = "船舶航行状态字段最大长度50")
    private String navistat;

    @Length(max = 50,message = "船长（分米）-> 米字段最大长度50")
    private String length1;

    @Length(max = 50,message = "船宽字段最大长度50")
    private String width;

    @Length(max = 50,message = "吃水字段最大长度50")
    private String draught;

    @Length(max = 50,message = "船舶航行标识字段最大长度50")
    private String shipSailingId;

    @Length(max = 50,message = "mmsi字段最大长度50")
    private String mmsi;

    @Length(max = 50,message = "呼号字段最大长度50")
    private String callsign;

    @Length(max = 50,message = "imo字段最大长度50")
    private String imo;

    @Length(max = 50,message = "纬度字段最大长度50")
    private String lat;

    @Length(max = 50,message = "经度字段最大长度50")
    private String lon;

    //@Length(max = 50,message = "航首向字段最大长度50")
    private Double hdg;

    //@Length(max = 50,message = "航向标识字段最大长度50")
    private Double cog;

    @Length(max = 50,message = "船速字段最大长度50")
    private String sog;

    @Length(max = 50,message = "目的港字段最大长度50")
    private String destStd;

    @Length(max = 50,message = "出发港字段最大长度50")
    private String portnameCn;

    @Length(max = 50,message = "预计到达时间字段最大长度50")
    private String etaStd;

    @Length(max = 50,message = "离开出发港时间时间字段最大长度50")
    private String atd;

    @Length(max = 50,message = "更新时间字段最大长度50")
    private String lasttime;

}
