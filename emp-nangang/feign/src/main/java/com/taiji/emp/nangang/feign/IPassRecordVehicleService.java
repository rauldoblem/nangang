package com.taiji.emp.nangang.feign;

import com.taiji.emp.nangang.vo.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/6 15:02
 */
@FeignClient(value = "micro-nangang-passRecordVehicles")
public interface IPassRecordVehicleService {

    @RequestMapping(method = RequestMethod.GET ,path = "/getVehicleTypeAndFlow")
    @ResponseBody
    ResponseEntity<TotalVehicleTypeAndFlowVo> getVehicleTypeAndFlow();

    @RequestMapping(method = RequestMethod.GET ,path = "/getBayonetVehiclePassState")
    @ResponseBody
    ResponseEntity<List<BayonetVehiclePassStateVo>> getBayonetVehiclePassState();

    @RequestMapping(method = RequestMethod.GET ,path = "/getVehicleTypeAndBayonetFlow")
    @ResponseBody
    ResponseEntity<TotalVehicleTypeAndBayonetFlowVo> getVehicleTypeAndBayonetFlow();

    @RequestMapping(method = RequestMethod.GET ,path = "/getBayonetFlowProportion")
    @ResponseBody
    ResponseEntity<List<BayonetFlowProportionVo>> getBayonetFlowProportion();

    @RequestMapping(method = RequestMethod.GET ,path = "/getVehicleByCompany")
    @ResponseBody
    ResponseEntity<List<VehicleNumberOfCompanyVo>> getVehicleByCompany();
}
