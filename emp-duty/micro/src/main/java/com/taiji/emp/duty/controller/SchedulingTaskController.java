package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.Scheduling;
import com.taiji.emp.duty.feign.ISchedulingTaskRestService;
import com.taiji.emp.duty.mapper.SchedulingMapper;
import com.taiji.emp.duty.service.SchedulingService;
import com.taiji.emp.duty.vo.SchedulingVo;
import com.taiji.micro.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/scheduling/task")
public class SchedulingTaskController extends BaseController implements ISchedulingTaskRestService {

    SchedulingService service;
    SchedulingMapper mapper;

    @Override
    public ResponseEntity<List<SchedulingVo>> findNextTimesList(@Validated @NotNull(message = "vo不能为空") @RequestBody SchedulingVo schedulingVo) {
        //获取下个班次值班人员信息
        List<Scheduling> list = service.findNextTimesInfo(schedulingVo);
        List<SchedulingVo> voResult = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 根据当前时间获取当前班次Id
     * @param  localDateTime
     * @return
     */
    @Override
    public ResponseEntity<SchedulingVo> findShiftPattId(
            @Validated
            @NotNull(message = "localDateTime不能为空")
            @RequestBody LocalDateTime localDateTime) {
        Scheduling entity = service.findShiftPattId(localDateTime);
        SchedulingVo schedulingVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(schedulingVo);
    }


}
