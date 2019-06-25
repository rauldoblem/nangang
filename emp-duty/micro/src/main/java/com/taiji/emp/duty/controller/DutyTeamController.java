package com.taiji.emp.duty.controller;

import com.taiji.emp.duty.entity.DutyTeam;
import com.taiji.emp.duty.feign.IDutyTeamRestService;
import com.taiji.emp.duty.mapper.DutyTeamMapper;
import com.taiji.emp.duty.service.DutyTeamService;
import com.taiji.emp.duty.vo.DutyTeamVo;
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
@RequestMapping("/api/dutyTeam")
public class DutyTeamController extends BaseController implements IDutyTeamRestService {

    DutyTeamService service;
    DutyTeamMapper mapper;

    /**
     * 新增值班人员分组信息
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DutyTeamVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DutyTeamVo vo) {
        DutyTeam entity = mapper.voToEntity(vo);
        DutyTeam result = service.create(entity);
        DutyTeamVo voResult = mapper.entityToVo(result);
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
     * 修改值班人员分组信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DutyTeamVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DutyTeamVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DutyTeam entity = mapper.voToEntity(vo);
        DutyTeam result = service.update(entity);
        DutyTeamVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id查询某条值班人员分组信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DutyTeamVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DutyTeam result = service.findOne(id);
        DutyTeamVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询值班人员分组列表
     * @param orgId
     * @return
     */
    @Override
    public ResponseEntity<List<DutyTeamVo>> findList(
            @RequestParam(value = "orgId") String orgId) {
        List<DutyTeam> list = service.findList(orgId);
        List<DutyTeamVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据条件查询值班人员分组列表
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<List<DutyTeamVo>> findDutyList(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DutyTeamVo vo) {
        List<DutyTeam> list = service.findDutyList(vo);
        List<DutyTeamVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
