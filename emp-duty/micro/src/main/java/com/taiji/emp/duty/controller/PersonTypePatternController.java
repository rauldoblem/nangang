package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.PersonTypePattern;
import com.taiji.emp.duty.feign.IPersonTypePatternRestService;
import com.taiji.emp.duty.mapper.PersonTypePatternMapper;
import com.taiji.emp.duty.service.PersonTypePatternService;
import com.taiji.emp.duty.vo.dailylog.PersonTypePatternVo;
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
@RequestMapping("/api/personTypePatterns")
public class PersonTypePatternController extends BaseController implements IPersonTypePatternRestService {

    PersonTypePatternService service;
    PersonTypePatternMapper mapper;

    /**
     * 新增值班人员设置信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<PersonTypePatternVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody PersonTypePatternVo vo) {
        PersonTypePattern entity = mapper.voToEntity(vo);
        PersonTypePattern result = service.create(entity);
        PersonTypePatternVo voResult = mapper.entityToVo(result);
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
     * 修改值班人员设置信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<PersonTypePatternVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PersonTypePatternVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PersonTypePattern entity = mapper.voToEntity(vo);
        PersonTypePattern result = service.update(entity);
        PersonTypePatternVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id查询某条值班人员设置信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<PersonTypePatternVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PersonTypePattern result = service.findOne(id);
        PersonTypePatternVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询值班人员设置列表
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<List<PersonTypePatternVo>> findAll(
            @NotEmpty(message = "id不能为空")
            @RequestParam(value = "id") String id) {
        List<PersonTypePattern> list = service.findList(id);
        List<PersonTypePatternVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
