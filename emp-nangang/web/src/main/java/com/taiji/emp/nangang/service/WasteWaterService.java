package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.WasteWaterClient;
import com.taiji.emp.nangang.vo.WasteWaterVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author yhcookie
 * @date 2018/12/11 14:18
 */
@Service
public class WasteWaterService {
    @Autowired
    private WasteWaterClient client;

    public WasteWaterVo getWasteWater() {
        ResponseEntity<WasteWaterVo> result = client.getWasteWater();
        WasteWaterVo wasteWaterVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return wasteWaterVo;
    }
}
