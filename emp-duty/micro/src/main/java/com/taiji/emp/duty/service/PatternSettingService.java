package com.taiji.emp.duty.service;

import com.taiji.emp.duty.entity.CalenSetting;
import com.taiji.emp.duty.entity.PatternSetting;
import com.taiji.emp.duty.entity.Scheduling;
import com.taiji.emp.duty.repository.PatternSettingRepository;
import com.taiji.emp.duty.repository.SchedulingRepository;
import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import com.taiji.micro.common.service.BaseService;
import com.taiji.micro.common.service.UtilsService;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PatternSettingService extends BaseService<PatternSetting,String> {

    @Autowired
    PatternSettingRepository repository;

    @Autowired
    private SchedulingRepository schedulingRepository;

    @Autowired
    UtilsService utilsService;

    
    /**
     * 根据条件查询模式设置信息列表
     * @param orgId
     * @return
     */
    public List<PatternSetting> findList(String orgId) {
        List<PatternSetting> list = repository.findList(orgId);
        return list;
    }

    /**
     * 模式设置批量插入
     * @param  patternSettingList
     * @return ResponseEntity<List<PatternSettingVo>>
     */
    public List<PatternSetting> createBatch(List<PatternSettingVo> patternSettingList) {
        Assert.notNull(patternSettingList,"patternSettingList对象不能为空");
        List<PatternSetting>  result = repository.saveBatch(patternSettingList);
        return result;
    }

    /**
     * 根据所属单位ID,日期类型编码，获取模式设置
     * @param patternSettingVo
     * @return
     */
    public PatternSetting findOne(PatternSettingVo patternSettingVo) {
        Assert.notNull(patternSettingVo,"patternSettingVo对象不能为空");
        PatternSetting  result = repository.findPatternSetting(patternSettingVo);
        return result;
    }

    /**
     *  根据当前时间获取班次名称，供交接班使用
     * @return
     */
    public Scheduling findCurrentShiftName(String orgId) {

        //当前时间
        Scheduling entity = schedulingRepository.findCurrentShiftName(utilsService.now(),orgId);
        return entity;
    }
}
