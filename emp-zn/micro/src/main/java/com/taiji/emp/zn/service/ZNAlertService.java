package com.taiji.emp.zn.service;

import com.netflix.discovery.converters.Auto;
import com.taiji.emp.zn.entity.PushAlert;
import com.taiji.emp.zn.repository.ZNAlertRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yhcookie
 * @date 2018/12/22 20:54
 */
@Slf4j
@Service
@AllArgsConstructor
public class ZNAlertService {

    @Autowired
    private ZNAlertRepository repository;

    public PushAlert savePushAlert(PushAlert pushAlert) {
        return repository.save(pushAlert);
    }
}
