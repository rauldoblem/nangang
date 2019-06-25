package com.taiji.emp.nangang.service;

import com.taiji.emp.nangang.entity.DailyCheckItems;
import com.taiji.emp.nangang.repository.DailyCheckDailyLogRepository;
import com.taiji.emp.nangang.repository.DailyCheckItemsRepository;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DailyCheckItemsService {

    @Autowired
    private DailyCheckItemsRepository repository;

    @Autowired
    private DailyCheckDailyLogRepository logRepository;

    public DailyCheckItems update(DailyCheckItems entity) {
        Assert.notNull(entity,"DailyCheckItems对象不能为null");
        entity.setDelFlag(DelFlagEnum.NORMAL.getCode());
        DailyCheckItems result = repository.save(entity);
        return result;
    }

    public List<DailyCheckItems> findCheckId(String id) {
        List<DailyCheckItems> result = repository.findAll(id);
        return result;
    }

    /**
     * 根据dailyCheckItemsVoId查询对应的dailyLogId
     * （从中间表ed_dailycheck_dailylog查）
     * @param dailyCheckItemsVoId
     * @return
     */
    public String findDailyLogId(String dailyCheckItemsVoId) {
        boolean notBlank = StringUtils.isNotBlank(dailyCheckItemsVoId);
        Assert.isTrue(notBlank,"id不能为null或空字符串!");
        String result = logRepository.findDailyLogId(dailyCheckItemsVoId);
        return result;
    }
}
