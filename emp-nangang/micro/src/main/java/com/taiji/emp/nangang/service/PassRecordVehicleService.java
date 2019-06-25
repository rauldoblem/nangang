package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.PassRecordVehicle;
import com.taiji.emp.nangang.repository.PassRecordVehicleRepository;
import com.taiji.emp.nangang.vo.VehicleNumberOfCompanyVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yhcookie
 * @date 2018/12/6 15:11
 */
@Slf4j
@Service
@AllArgsConstructor
public class PassRecordVehicleService {

    @Autowired
    private PassRecordVehicleRepository repository;

    public List<PassRecordVehicle> getVehicleTypeAndFlow() {
        return repository.getVehicleTypeAndFlow();
    }

    public List<PassRecordVehicle> getBayonetVehiclePassState() {
        return repository.getBayonetVehiclePassState();
    }

    public Map<String, PassRecordVehicle> getVehicleTypeAndBayonetFlow() {
        return repository.getVehicleTypeAndBayonetFlow();
    }

    public List<PassRecordVehicle> getBayonetFlowProportion() {
        return repository.getBayonetFlowProportion();
    }

    public List<VehicleNumberOfCompanyVo> getVehicleByCompany() {
        return repository.getVehicleByCompany();
    }
}
