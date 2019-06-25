package com.taiji.emp.event.infoConfig.controller;

import com.taiji.emp.event.infoConfig.entity.AcceptRule;
import com.taiji.emp.event.infoConfig.feign.IAcceptRuleService;
import com.taiji.emp.event.infoConfig.mapper.AcceptRuleMapper;
import com.taiji.emp.event.infoConfig.service.AccRuleService;
import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/acceptRules")
public class AcceptRuleController extends BaseController implements IAcceptRuleService{

    AccRuleService service;
    AcceptRuleMapper mapper;

    /**
     * 新增接报要求AcceptRuleVo
     *
     * @param vo
     * @return ResponseEntity<AcceptRuleVo>
     */
    @Override
    public ResponseEntity<AcceptRuleVo> create(
            @Validated
            @NotNull(message = "acceptRuleVo 不能为null")
            @RequestBody AcceptRuleVo vo) {
        AcceptRule entity = mapper.voToEntity(vo);
        AcceptRule result = service.create(entity);
        AcceptRuleVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新接报要求AcceptRuleVo
     *
     * @param vo
     * @param id 要更新AcceptRuleVo id
     * @return ResponseEntity<AcceptRuleVo>
     */
    @Override
    public ResponseEntity<AcceptRuleVo> update(
            @Validated
            @NotNull(message = "acceptRuleVo 不能为null")
            @RequestBody AcceptRuleVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        AcceptRule entity = mapper.voToEntity(vo);
        AcceptRule result = service.update(entity,id);
        AcceptRuleVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据查询条件单个AcceptRuleVo
     * 参数keys：eventTypeId
     * @param params
     * @return ResponseEntity<AcceptRuleVo>
     */
    @Override
    public ResponseEntity<AcceptRuleVo> getRuleSetting(
            @RequestParam MultiValueMap<String,Object> params) {
        AcceptRule result = service.getRuleSetting(params);
        AcceptRuleVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 获取一键事故的描述信息
     * @param eventTypeId
     * @return
     */
    @Override
    public ResponseEntity<List<AcceptRuleVo>> eventDesc(
            @NotEmpty(message = "eventTypeId 不能为空字符串")
            @RequestParam(value = "eventTypeId") String eventTypeId) {
        List<AcceptRule> entity = service.eventDesc(eventTypeId);
        List<AcceptRuleVo> restVo= mapper.entityListToVoList(entity);
        return ResponseEntity.ok(restVo);
    }
}
