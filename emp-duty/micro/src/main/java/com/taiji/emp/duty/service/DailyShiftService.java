package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.dailyShift.DailyShift;
import com.taiji.emp.duty.repository.DailyShiftRepository;
import com.taiji.emp.duty.searchVo.DailyShiftPageVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Slf4j
@Service
@AllArgsConstructor
public class DailyShiftService extends BaseService<DailyShift,String> {

    @Autowired
    private DailyShiftRepository repository;

    public DailyShift create(DailyShift entity){
        Assert.notNull(entity,"DailyShift 对象不能为 null");
        DailyShift result = repository.save(entity);
        return result;
    }

    public DailyShift findOne(String id){
        Assert.hasText(id,"id不能为null或空字符串!");
        DailyShift result = repository.findOne(id);
        return result;
    }

    //提供给controller使用的 分页list查询方法
    public Page<DailyShift> findPage(DailyShiftPageVo dailyShiftPageVo, Pageable pageable){
        Page<DailyShift> result = repository.findPage(dailyShiftPageVo,pageable);
        return result;
    }

}
