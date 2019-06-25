package com.taiji.emp.zn.controller;

import com.taiji.emp.zn.entity.PushAlert;
import com.taiji.emp.zn.feign.IZNAlertRestService;
import com.taiji.emp.zn.mapper.ZNAlertMapper;
import com.taiji.emp.zn.service.ZNAlertService;
import com.taiji.emp.zn.vo.PushAlertVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yhcookie
 * @date 2018/12/22 20:52
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/alerts")
public class ZNAlertController implements IZNAlertRestService{

    @Autowired
    private ZNAlertService service;
    @Autowired
    private ZNAlertMapper mapper;

    @Override
    public ResponseEntity<PushAlertVo> savePushAlert(@RequestBody PushAlertVo pushAlertVo) {
        PushAlert pushAlert = mapper.voToEntity(pushAlertVo);
        if(null != pushAlert){
            pushAlert.setCreateBy("外部数据");
            pushAlert.setCreateOrgId(pushAlertVo.getDeviceOrgId());
            pushAlert.setCreateOrgName(pushAlertVo.getDeviceOrgName());
            pushAlert.setDelFlag("1");
        }
        PushAlert result = service.savePushAlert(pushAlert);
        PushAlertVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }
}
