package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.CmdPlan;
import com.taiji.emp.event.cmd.feign.ICmdPlanRestService;
import com.taiji.emp.event.cmd.mapper.CmdPlanMapper;
import com.taiji.emp.event.cmd.service.CmdPlanService;
import com.taiji.emp.event.cmd.vo.CmdPlanVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/plans")
public class CmdPlanController extends BaseController implements ICmdPlanRestService{

    @Autowired
    CmdPlanService service;
    @Autowired
    CmdPlanMapper mapper;

    /**
     * 新增关联预案
     *
     * @param vos
     * @return ResponseEntity<List < CmdPlanVo>>
     */
    @Override
    public ResponseEntity<List<CmdPlanVo>> createList(
            @NotNull(message = "vos 不能为null")
            @RequestBody List<CmdPlanVo> vos) {
        List<CmdPlan> list = mapper.voListToEntityList(vos);
        List<CmdPlan> resultList = service.createList(list);
        List<CmdPlanVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 根据条件查询处置方案已关联的应急预案信息
     *
     * @param params 参数：schemeId 方案id
     * @return ResponseEntity<List < CmdPlanVo>>
     */
    @Override
    public ResponseEntity<List<CmdPlanVo>> searchAll(@RequestParam MultiValueMap<String, Object> params) {
        List<CmdPlan> resultList = service.searchAll(params);
        List<CmdPlanVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
