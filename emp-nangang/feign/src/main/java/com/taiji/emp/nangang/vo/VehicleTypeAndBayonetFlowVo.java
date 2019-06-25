package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/6 14:51
 */
@Setter
@Getter
@NoArgsConstructor
public class VehicleTypeAndBayonetFlowVo {

    @Length(max = 50,message = "车辆类型字段长度最大50")
    private String vehicleType;

    private List<BayonetFlowVo> bayonetFlowVo;
}
