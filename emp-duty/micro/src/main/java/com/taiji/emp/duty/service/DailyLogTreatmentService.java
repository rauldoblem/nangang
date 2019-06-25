package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.dailylog.DailyLogTreatment;
import com.taiji.emp.duty.repository.DailyLogTreatmentRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DailyLogTreatmentService extends BaseService<DailyLogTreatment,String> {

    DailyLogTreatmentRepository repository;

    /**
     * 添加值班日志办理信息
     * @param entity
     * @return
     */
    public DailyLogTreatment create(DailyLogTreatment entity) {
        Assert.notNull(entity,"entity对象不能为空");
        DailyLogTreatment result = repository.save(entity);
        return result;
    }

    /**
     * 根据值班日志ID获取办理状态列表
     * @param dlogId
     * @return
     */
    public List<DailyLogTreatment> findByDlogId(String dlogId) {
        List<DailyLogTreatment> list = repository.findByDlogId(dlogId);
        return list;
    }
}
