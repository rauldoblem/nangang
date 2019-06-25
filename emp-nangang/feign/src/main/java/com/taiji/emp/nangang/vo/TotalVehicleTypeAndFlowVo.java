package com.taiji.emp.nangang.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * @author yhcookie  VehicleTypeAndFlowVo
 * @date 2018/12/11 17:45
 */
@Setter
@Getter
@NoArgsConstructor
public class TotalVehicleTypeAndFlowVo {

    private List<String> legendData = Arrays.asList("驶出", "驶入", "存量");

    private List<String> yAxisData ;

    private List<String> totalNumber ;

    private List<String> comeInNum ;

    private List<String> goOutNum ;

}
