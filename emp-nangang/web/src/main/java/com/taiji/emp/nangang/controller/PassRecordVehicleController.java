package com.taiji.emp.nangang.controller;

import com.taiji.emp.nangang.service.PassRecordVehicleService;
import com.taiji.emp.nangang.vo.*;
import com.taiji.micro.common.entity.ResultEntity;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.ResultCodeEnum;
import com.taiji.micro.common.utils.ResultUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/6 14:16
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/passRecordVehicles")
public class PassRecordVehicleController extends BaseController{

    @Autowired
    private PassRecordVehicleService service;
    /**
     * 查询卡口名称以及车辆的出入量
     */
    @GetMapping("/getBayonetVehiclePassState")
    public ResultEntity updateCheckItem() {
        List<BayonetVehiclePassStateVo> resultVos = service.getBayonetVehiclePassState();
        return ResultUtils.success(resultVos);
    }

    /**
     * 查询车辆类型以及该类型车辆的出入量、存量
     */
    @GetMapping("/getVehicleTypeAndFlow")
    public ResultEntity getVehicleTypeAndFlow() {
        TotalVehicleTypeAndFlowVo resultVo = service.getVehicleTypeAndFlow();
        return ResultUtils.success(resultVo);
    }

    /**
     * 查询各卡口车流量占比（卡口名称，流量占比）
     */
    @GetMapping("/getBayonetFlowProportion")
    public ResultEntity getBayonetFlowProportion() {
        List<BayonetFlowProportionVo> resultVos = service.getBayonetFlowProportion();
        return ResultUtils.success(resultVos);
    }

    /**
     * 查询不同类型车辆各卡口出入量信息（车辆类型、卡口名称、流量）
     */
    @GetMapping("/getVehicleTypeAndBayonetFlow")
    public ResultEntity getVehicleTypeAndBayonetFlow() {
        TotalVehicleTypeAndBayonetFlowVo resultVos = service.getVehicleTypeAndBayonetFlow();
        return ResultUtils.success(resultVos);
    }

    /**
     * 查询危化品车辆在企业的分布 TopN型加其他企业
     */
    @GetMapping("/getVehicleByCompany")
    public ResultEntity getVehicleByCompany() {
        List<VehicleNumberOfCompanyVo> resultVos = service.getVehicleByCompany();
        if(!CollectionUtils.isEmpty(resultVos)){
            //结果集可以为null
            //TOP9+其他企业  9个或9个以内，直接返回结果集；9个以上取前9个
            if( 10 > resultVos.size()){
                return ResultUtils.success(resultVos);
            }else{
                List<VehicleNumberOfCompanyVo> vos = resultVos.subList(0,9);
                return ResultUtils.success(vos);
//                ArrayList<VehicleNumberOfCompanyVo> vos = new ArrayList<>();
//                VehicleNumberOfCompanyVo vehicleNumberOfCompanyVo = new VehicleNumberOfCompanyVo();
//              //  vehicleNumberOfCompanyVo.setName("其他企业");
//                int sum = 0;
//                for (int i = 0; i < resultVos.size(); i++) {
//                    if( i < 10){
//                        vos.add(resultVos.get(i));
//                    }else{
//                        //sum += Integer.parseInt(resultVos.get(i).getTotal());
//                        vos.add(resultVos.get(i));
//                    }
//                }
//                vehicleNumberOfCompanyVo.setTotal(String.valueOf(sum));
//                vos.add(vehicleNumberOfCompanyVo);
//                return ResultUtils.success(vos);
            }
        }else{
            return ResultUtils.fail(ResultCodeEnum.OPERATE_FAIL,"当前还没有企业车辆通行记录");
        }
    }
}
