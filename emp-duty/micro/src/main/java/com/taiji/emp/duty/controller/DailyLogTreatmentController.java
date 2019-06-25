package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.dailylog.DailyLogTreatment;
import com.taiji.emp.duty.feign.IDailyLogTreatmentRestService;
import com.taiji.emp.duty.mapper.DailyLogTreatmentMapper;
import com.taiji.emp.duty.service.DailyLogTreatmentService;
import com.taiji.emp.duty.vo.dailylog.DailyLogTreatmentVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
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
@RequestMapping("api/dailyLogTreatment")
public class DailyLogTreatmentController extends BaseController implements IDailyLogTreatmentRestService {

    DailyLogTreatmentService service;
    DailyLogTreatmentMapper mapper;

    /**
     * 添加值班日志办理信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DailyLogTreatmentVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DailyLogTreatmentVo vo) {
        DailyLogTreatment entity = mapper.voToEntity(vo);

        DailyLogTreatment result = service.create(entity);
        DailyLogTreatmentVo resultVo = mapper.entityToVo(result);

        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据值班日志ID获取办理状态列表
     * @param dlogId
     * @return
     */
    @Override
    public ResponseEntity<List<DailyLogTreatmentVo>> findByDlogId(
            @NotEmpty(message = "dlogId不能为空")
            @PathVariable(value = "dlogId") String dlogId) {
        List<DailyLogTreatment> list = service.findByDlogId(dlogId);
        List<DailyLogTreatmentVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
