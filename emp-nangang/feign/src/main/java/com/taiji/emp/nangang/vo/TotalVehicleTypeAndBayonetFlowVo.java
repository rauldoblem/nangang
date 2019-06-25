package com.taiji.emp.nangang.vo;

import com.taiji.emp.nangang.common.constant.NangangGlobal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/13 16:40
 */
@Setter
@Getter
@NoArgsConstructor
public class TotalVehicleTypeAndBayonetFlowVo {

    //车辆类型集合
    private List<String> xAxis = NangangGlobal.VEHICLE_TYPE_LIST;

    private List<BayonetFlowForTotalVehicleVo> series;
}
