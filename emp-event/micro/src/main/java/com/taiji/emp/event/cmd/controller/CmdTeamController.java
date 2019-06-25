package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.CmdExpert;
import com.taiji.emp.event.cmd.entity.CmdTeam;
import com.taiji.emp.event.cmd.feign.ICmdExpertRestService;
import com.taiji.emp.event.cmd.feign.ICmdTeamRestService;
import com.taiji.emp.event.cmd.mapper.CmdExpertMapper;
import com.taiji.emp.event.cmd.mapper.CmdTeamMapper;
import com.taiji.emp.event.cmd.service.CmdExpertService;
import com.taiji.emp.event.cmd.service.CmdTeamService;
import com.taiji.emp.event.cmd.vo.CmdExpertVo;
import com.taiji.emp.event.cmd.vo.CmdTeamVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *  应急处置--关联应急队伍 接口服务实现类
 * @author qizhijie-pc
 * @date 2018年11月7日11:26:20
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/teams")
public class CmdTeamController extends BaseController implements ICmdTeamRestService{

    CmdTeamService service;
    CmdTeamMapper mapper;


    /**
     * 新增单个应急队伍
     *
     * @param vo
     * @return ResponseEntity<CmdTeamVo>
     */
    @Override
    public ResponseEntity<CmdTeamVo> createOne(
            @Validated
            @NotNull(message = "CmdTeamVo 不能为null")
            @RequestBody CmdTeamVo vo) {
        CmdTeam entity = mapper.voToEntity(vo);
        CmdTeam result = service.createOne(entity);
        CmdTeamVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 新增多个应急队伍
     *
     * @param vos
     * @return ResponseEntity<List < CmdTeamVo>>
     */
    @Override
    public ResponseEntity<List<CmdTeamVo>> createList(
            @Validated
            @NotNull(message = "List<CmdTeamVo> 不能为null")
            @RequestBody List<CmdTeamVo> vos) {
        List<CmdTeam> entityList = mapper.voListToEntityList(vos);
        List<CmdTeam> resultList = service.createList(entityList);
        List<CmdTeamVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 删除应急队伍关联信息
     *
     * @param id 应急队伍关联表Id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据条件查询处置方案已关联的应急队伍信息
     *
     * @param params 参数key:schemeId,name,teamTypeName
     * @return ResponseEntity<List < CmdTeamVo>>
     */
    @Override
    public ResponseEntity<List<CmdTeamVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<CmdTeam> resultList = service.findList(params);
        List<CmdTeamVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
