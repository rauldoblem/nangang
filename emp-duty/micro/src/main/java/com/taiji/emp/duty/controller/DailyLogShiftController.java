package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.dailyShift.DailyLogShift;
import com.taiji.emp.duty.feign.IDailyLogShiftRestService;
import com.taiji.emp.duty.mapper.DailyLogShiftMapper;
import com.taiji.emp.duty.service.DailyLogShiftService;
import com.taiji.emp.duty.vo.dailyShift.DailyLogShiftVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/dailyLogShift")
public class DailyLogShiftController extends BaseController implements IDailyLogShiftRestService {

    DailyLogShiftService service;
    DailyLogShiftMapper mapper;

    /**
     * 新增交接班内容中间表
     * @param list
     * @return ResponseEntity<DailyLogShiftVo>
     */
    @Override
    public ResponseEntity<DailyLogShiftVo> create(
            @NotNull(message = "vo不能为null")
            @RequestBody List<DailyLogShiftVo> list) {
        List<DailyLogShift> entity = mapper.voListToEntityList(list);
        DailyLogShift result = service.create(entity);
        DailyLogShiftVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

}
