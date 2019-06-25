package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author yhcookie
 * @date 2018/11/30 14:02
 */
@Setter
@Getter
@NoArgsConstructor
public class BayonetVehiclePassStateVo {

    @Length(max = 50 ,message = "卡口名称字段最大长度50")
    private String bayonetName;

    @Length(max = 36 , message = "入园车辆数量字段最大长度36")
    private String inNumber;

    @Length(max = 36 , message = "出园车辆数量字段最大长度36")
    private String outNumber;

}
