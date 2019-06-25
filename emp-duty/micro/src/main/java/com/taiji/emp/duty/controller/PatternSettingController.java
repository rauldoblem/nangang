package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.PatternSetting;
import com.taiji.emp.duty.entity.Scheduling;
import com.taiji.emp.duty.feign.IPatternSettingRestService;
import com.taiji.emp.duty.mapper.PatternSettingMapper;
import com.taiji.emp.duty.mapper.SchedulingMapper;
import com.taiji.emp.duty.service.PatternSettingService;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/patternSetting")
public class PatternSettingController extends BaseController implements IPatternSettingRestService {

    PatternSettingService service;
    PatternSettingMapper mapper;
    SchedulingMapper schedulingMapper;

    /**
     * 根据条件查询值班模式设置列表
     * @param orgId
     * @return
     */
    @Override
    public ResponseEntity<List<PatternSettingVo>> findAll(
            @PathVariable(value = "id") String orgId) {
        List<PatternSetting> list = service.findList(orgId);
        List<PatternSettingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 模式设置批量插入
     * @param  patternSettingList
     * @return ResponseEntity<List<PatternSettingVo>>
     */
    @Override
    public ResponseEntity<List<PatternSettingVo>> createBatch(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody List<PatternSettingVo> patternSettingList) {
        List<PatternSetting> list = service.createBatch(patternSettingList);
        List<PatternSettingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据所属单位ID,日期类型编码，获取模式设置
     * @param patternSettingVo
     * @return
     */
    @Override
    public ResponseEntity<PatternSettingVo> findOne(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody PatternSettingVo patternSettingVo) {
        PatternSetting entity = service.findOne(patternSettingVo);
        PatternSettingVo vo = mapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }

    /**
     * 根据当前时间获取班次名称，供交接班使用
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findCurrentShiftName(String orgId) {
        Scheduling entity = service.findCurrentShiftName(orgId);
        SchedulingVo vo = schedulingMapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }
}
