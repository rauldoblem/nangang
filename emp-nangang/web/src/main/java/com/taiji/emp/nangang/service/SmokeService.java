package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.feign.SmokeClient;
import com.taiji.emp.nangang.vo.SmokeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author yhcookie
 * @date 2018/12/11 10:07
 */
@Service
public class SmokeService {

    @Autowired
    private SmokeClient client;

    public SmokeVo getSmoke() {
        ResponseEntity<SmokeVo> result = client.getSmoke();
        SmokeVo smokeVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return smokeVo;
    }

    public RestPageImpl<SmokeVo> findPage() {
        ResponseEntity<RestPageImpl<SmokeVo>> resultVo = client.findPage();
        RestPageImpl<SmokeVo> vo = ResponseEntityUtils.achieveResponseEntityBody(resultVo);
        return vo;
    }
}
