package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.Scheme;
import com.taiji.emp.event.cmd.feign.ISchemeRestService;
import com.taiji.emp.event.cmd.mapper.SchemeMapper;
import com.taiji.emp.event.cmd.service.SchemeService;
import com.taiji.emp.event.cmd.vo.SchemeVo;
import com.taiji.emp.event.infoDispatch.entity.Event;
import com.taiji.emp.event.infoDispatch.mapper.EventMapper;
import com.taiji.emp.event.infoDispatch.vo.EventVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/schemes")
public class SchemeController extends BaseController implements ISchemeRestService{

    @Autowired
    SchemeService service;
    @Autowired
    SchemeMapper mapper;
    @Autowired
    EventMapper eventMapper;

    /**
     * 新增处置方案SchemeVo
     *
     * @param vo
     * @return ResponseEntity<SchemeVo>
     */
    @Override
    public ResponseEntity<SchemeVo> create(
            @Validated
            @NotNull(message = "SchemeVo 不能为null")
            @RequestBody SchemeVo vo) {
        Scheme entity = mapper.voToEntity(vo);
        Scheme result = service.create(entity);
        SchemeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据事件id 获取处置方案SchemeVo
     *
     * @param eventId 不能为空
     * @return ResponseEntity<SchemeVo>
     */
    @Override
    public ResponseEntity<SchemeVo> findByEventId(
            @NotEmpty(message = "eventId 不能为空字符串")
            @RequestParam(value = "eventId")String eventId) {
        Scheme result = service.findByEventId(eventId);
        SchemeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新处置方案SchemeVo
     *
     * @param vo
     * @param id 要更新SchemeVo id
     * @return ResponseEntity<SchemeVo>
     */
    @Override
    public ResponseEntity<SchemeVo> update(
            @Validated
            @NotNull(message = "SchemeVo 不能为null")
            @RequestBody SchemeVo vo,
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id")String id) {
        Scheme entity = mapper.voToEntity(vo);
        Scheme result = service.update(entity,id);
        SchemeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据schemeId 获取事件信息EventVo
     *
     * @param schemeId 不能为空
     * @return ResponseEntity<EventVo>
     */
    @Override
    public ResponseEntity<EventVo> findEventBySchemeId(
            @NotEmpty(message = "schemeId 不能为空字符串")
            @RequestParam(value = "schemeId") String schemeId) {
        Event result = service.findEventBySchemeId(schemeId);
        EventVo resultVo = eventMapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    @Override
    public ResponseEntity<SchemeVo> findOne(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Scheme entity = service.findOne(id);
        SchemeVo schemeVo = mapper.entityToVo(entity);
        return new ResponseEntity<>(schemeVo, HttpStatus.OK);
    }
}
