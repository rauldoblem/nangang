package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/12/6 14:29
 */

@Setter
@Getter
@NoArgsConstructor
public class VehicleTypeAndFlowVo {

    @Length(max =50,message = "车辆类型字段最大长度50")
    private String vehicleType;

    @Length(max =50,message = "车辆出量字段最大长度50")
    private String out;

    @Length(max =50,message = "车辆入量字段最大长度50")
    private String in;

    @Length(max =50,message = "车辆存量字段最大长度50")
    private String exist;

}
