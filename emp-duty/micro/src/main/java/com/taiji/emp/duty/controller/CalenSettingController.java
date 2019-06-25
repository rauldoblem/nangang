package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.common.constant.SchedulingGlobal;
import com.taiji.emp.duty.entity.*;
import com.taiji.emp.duty.feign.ICalenSettingRestService;
import com.taiji.emp.duty.mapper.*;
import com.taiji.emp.duty.searchVo.CalenSettingListVo;
import com.taiji.emp.duty.service.*;
import com.taiji.emp.duty.util.SchedulingUtil;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.emp.duty.vo.dailylog.CalenSettingVo;
import com.taiji.emp.duty.vo.dailylog.PatternSettingVo;
import com.taiji.emp.duty.vo.dailylog.PersonTypePatternVo;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/calenSetting")
public class CalenSettingController extends BaseController implements ICalenSettingRestService {

    CalenSettingService service;
    CalenSettingMapper mapper;
    /**
     * 添加日历设置信息
     *
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<CalenSettingVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody CalenSettingListVo vo) {
        CalenSetting result = service.create(vo);
        CalenSettingVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 获取值班模式
     *
     * @param dateStr
     * @return
     */
    @Override
    public ResponseEntity<CalenSettingVo> findSettingDate(@Validated @NotNull(message = "dateStr不能为空") String dateStr, String orgId) {
        LocalDate currentDate = DateUtil.strToLocalDate(dateStr);
        CalenSetting entity = service.findSettingDate(currentDate, orgId);
        CalenSettingVo vo = mapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }

    /**
     * 根据条件查询日历设置列表
     *
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<CalenSettingVo>> findAll(
            @RequestBody CalenSettingVo vo) {
        List<CalenSetting> list = service.findList(vo);
        List<CalenSettingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 日历设置批量插入
     *
     * @param vo
     * @return ResponseEntity<List < CalenSettingVo>>
     */
    @Override
    public ResponseEntity<List<CalenSettingVo>> createBatch(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody CalenSettingListVo vo) {
        List<CalenSetting> list = service.createBatch(vo);
        List<CalenSettingVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
