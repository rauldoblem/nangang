package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.*;
import com.taiji.emp.duty.feign.IScheduldeTaskRestService;
import com.taiji.emp.duty.mapper.SchedulingMapper;
import com.taiji.emp.duty.service.*;
import com.taiji.emp.duty.vo.SchedulingVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/schedulingTask")
public class ScheduledTaskController implements IScheduldeTaskRestService {

    SchedulingService service;
    SchedulingMapper mapper;

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
