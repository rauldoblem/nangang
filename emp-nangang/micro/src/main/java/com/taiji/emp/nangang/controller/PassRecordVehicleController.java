package com.taiji.emp.nangang.controller;

import com.netflix.discovery.converters.Auto;
import com.taiji.emp.nangang.entity.PassRecordVehicle;
import com.taiji.emp.nangang.feign.IPassRecordVehicleService;
import com.taiji.emp.nangang.mapper.PassRecordVehicleMapper;
import com.taiji.emp.nangang.service.PassRecordVehicleService;
import com.taiji.emp.nangang.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author yhcookie
 * @date 2018/12/6 14:14
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/passRecordVehicles")
public class PassRecordVehicleController implements IPassRecordVehicleService {

    @Autowired
    private PassRecordVehicleService service;
    @Autowired
    private PassRecordVehicleMapper mapper;
    @Override
    public ResponseEntity<TotalVehicleTypeAndFlowVo> getVehicleTypeAndFlow() {
        List<PassRecordVehicle> entitys = service.getVehicleTypeAndFlow();
        TotalVehicleTypeAndFlowVo vos = mapper.entityToVehicleTypeAndFlowVoList(entitys);
        return ResponseEntity.ok(vos);
    }

    @Override
    public ResponseEntity<List<BayonetVehiclePassStateVo>> getBayonetVehiclePassState() {
        List<PassRecordVehicle> entitys = service.getBayonetVehiclePassState();
        List<BayonetVehiclePassStateVo> vos = mapper.entityToBayonetVehiclePassStateVoList(entitys);
        return ResponseEntity.ok(vos);
    }

    @Override
    public ResponseEntity<TotalVehicleTypeAndBayonetFlowVo> getVehicleTypeAndBayonetFlow() {
        Map<String, PassRecordVehicle> entitys = service.getVehicleTypeAndBayonetFlow();
        TotalVehicleTypeAndBayonetFlowVo vos = mapper.entityToVehicleTypeAndBayonetFlowVoList(entitys);
        return ResponseEntity.ok(vos);
    }

    @Override
    public ResponseEntity<List<BayonetFlowProportionVo>> getBayonetFlowProportion() {
        List<PassRecordVehicle> entitys = service.getBayonetFlowProportion();
        List<BayonetFlowProportionVo> vos = mapper.entityToBayonetFlowProportionVoList(entitys);
        return ResponseEntity.ok(vos);
    }

    @Override
    public ResponseEntity<List<VehicleNumberOfCompanyVo>> getVehicleByCompany() {
        List<VehicleNumberOfCompanyVo> vos = service.getVehicleByCompany();
        return ResponseEntity.ok(vos);
    }
}
