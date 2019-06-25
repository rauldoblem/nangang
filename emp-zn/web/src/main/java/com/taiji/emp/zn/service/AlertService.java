package com.taiji.emp.zn.service;

import com.taiji.emp.zn.feign.ZNAlertClient;
import com.taiji.emp.zn.vo.PushAlertVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author yhcookie
 * @date 2018/12/22 20:27
 */
@Service
public class AlertService {

    @Autowired
    private ZNAlertClient client;

    public void savePushAlert(PushAlertVo pushAlertVo) {
        client.savePushAlert(pushAlertVo);
    }
}
