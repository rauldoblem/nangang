package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.CalenSetting;
import com.taiji.emp.duty.repository.CalenSettingRepository;
import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CalenSettingService extends BaseService<CalenSetting,String> {

    @Autowired
    CalenSettingRepository repository;

    /**
     * 新增日历设置信息
     * @param vo
     * @return
     */
    public CalenSetting create(CalenSettingListVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        CalenSetting result = repository.save(vo);
        return result;
    }


    /**
     * 根据条件查询日历设置信息列表
     * @param calenSettingVo
     * @return
     */
    public List<CalenSetting> findList(CalenSettingVo calenSettingVo) {
        List<CalenSetting> list = repository.findList(calenSettingVo);
        return list;
    }

    /**
     * 获取值班模式
     * @param currentDate
     * @return
     */
    public CalenSetting findSettingDate(LocalDate currentDate,String orgId) {
        CalenSetting entity = repository.findSettingDate(currentDate,orgId);
        return entity;
    }

    /**
     * 日历设置批量插入
     * @param  vo
     * @return ResponseEntity<List<CalenSettingVo>>
     */
    public List<CalenSetting>  createBatch(CalenSettingListVo vo) {
        Assert.notNull(vo,"vo对象不能为空");
        List<CalenSetting>  result = repository.saveBatch(vo);
        return result;
    }
}
