package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.ShiftPattern;
import com.taiji.emp.duty.feign.IShiftPatternRestService;
import com.taiji.emp.duty.mapper.ShiftPatternMapper;
import com.taiji.emp.duty.service.ShiftPatternService;
import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/shiftPatterns")
public class ShiftPatternController extends BaseController implements IShiftPatternRestService {

    ShiftPatternService service;
    ShiftPatternMapper mapper;

    /**
     * 新增班次设置信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<ShiftPatternVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody ShiftPatternVo vo) {
        ShiftPattern entity = mapper.voToEntity(vo);
        ShiftPattern result = service.create(entity);
        ShiftPatternVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 修改班次设置信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ShiftPatternVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody ShiftPatternVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        ShiftPattern entity = mapper.voToEntity(vo);
        ShiftPattern result = service.update(entity);
        ShiftPatternVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id查询某条班次设置信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<ShiftPatternVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        ShiftPattern result = service.findOne(id);
        ShiftPatternVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询班次设置列表
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<List<ShiftPatternVo>> findAll(
            @NotEmpty(message = "patterId不能为空")
            @RequestParam(value = "patterId") String id) {
        List<ShiftPattern> list = service.findList(id);
        List<ShiftPatternVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
