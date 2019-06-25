package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.PassRecordVehicleClient;
import com.taiji.emp.nangang.vo.*;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/6 14:24
 */
@Service
public class PassRecordVehicleService {

    @Autowired
    private PassRecordVehicleClient client;
    /**
     * 查询车辆类型以及该类型车辆的出入量、存量
     */
    public TotalVehicleTypeAndFlowVo getVehicleTypeAndFlow() {
        ResponseEntity<TotalVehicleTypeAndFlowVo> result = client.getVehicleTypeAndFlow();
        TotalVehicleTypeAndFlowVo vo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vo;
    }

    /**
     * 查询卡口名称以及车辆的出入量
     */
    public List<BayonetVehiclePassStateVo> getBayonetVehiclePassState() {
        ResponseEntity<List<BayonetVehiclePassStateVo>> result = client.getBayonetVehiclePassState();
        List<BayonetVehiclePassStateVo> vos = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vos;
    }

    /**
     * 查询不同类型车辆各卡口出入量信息（车辆类型、卡口名称、流量）
     */
    public TotalVehicleTypeAndBayonetFlowVo getVehicleTypeAndBayonetFlow() {
        ResponseEntity<TotalVehicleTypeAndBayonetFlowVo> result = client.getVehicleTypeAndBayonetFlow();
        TotalVehicleTypeAndBayonetFlowVo vos = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vos;
    }

    /**
     * 查询各卡口车流量占比（卡口名称，流量占比）
     */
    public List<BayonetFlowProportionVo> getBayonetFlowProportion() {
        ResponseEntity<List<BayonetFlowProportionVo>> result = client.getBayonetFlowProportion();
        List<BayonetFlowProportionVo> vos = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vos;
    }
    /**
     * 查询危化品车辆在企业的分布
     */
    public List<VehicleNumberOfCompanyVo> getVehicleByCompany() {
        ResponseEntity<List<VehicleNumberOfCompanyVo>> result = client.getVehicleByCompany();
        List<VehicleNumberOfCompanyVo> vos = ResponseEntityUtils.achieveResponseEntityBody(result);
        return vos;
    }
}
