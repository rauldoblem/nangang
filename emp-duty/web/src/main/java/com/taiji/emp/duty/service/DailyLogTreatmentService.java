package com.taiji.emp.duty.service;

import com.taiji.emp.duty.feign.DailyLogTreatmentClient;
import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DailyLogTreatmentService  extends BaseService {

    @Autowired
    private DailyLogTreatmentClient dailyLogTreatmentClient;

    /**
     * 添加值班日志办理信息
     * @param vo
     */
    public void create(DailyLogTreatmentVo vo){
        dailyLogTreatmentClient.create(vo);
    }

    /**
     * 根据值班日志ID获取办理状态列表
     * @param dlogId
     * @return
     */
    public List<DailyLogTreatmentVo> findByDlogId(String dlogId) {
        Assert.notNull(dlogId,"dlogId不能为null");
        ResponseEntity<List<DailyLogTreatmentVo>> list = dailyLogTreatmentClient.findByDlogId(dlogId);
        List<DailyLogTreatmentVo> voList = ResponseEntityUtils.achieveResponseEntityBody(list);
        return voList;
    }
}
