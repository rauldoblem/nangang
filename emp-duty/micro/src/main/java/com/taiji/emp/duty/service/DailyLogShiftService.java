package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.dailyShift.DailyLogShift;
import com.taiji.emp.duty.repository.DailyLogShiftRepository;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class DailyLogShiftService extends BaseService<DailyLogShift,String> {

    @Autowired
    private DailyLogShiftRepository repository;

    public DailyLogShift create(List<DailyLogShift> entity){
        Assert.notNull(entity,"DailyLogShift 对象不能为 null");
        List<DailyLogShift> result = repository.save(entity);
        return result.get(0);
    }

}
