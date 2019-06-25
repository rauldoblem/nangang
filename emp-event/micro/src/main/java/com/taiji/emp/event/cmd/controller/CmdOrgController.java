package com.taiji.emp.event.cmd.controller;


import com.taiji.emp.event.cmd.entity.CmdOrg;
import com.taiji.emp.event.cmd.feign.ICmdOrgRestService;
import com.taiji.emp.event.cmd.mapper.CmdOrgMapper;
import com.taiji.emp.event.cmd.service.CmdOrgService;
import com.taiji.emp.event.cmd.vo.CmdOrgVo;
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
 *  应急处置--关联应急组织机构 接口服务实现类
 * @author qizhijie-pc
 * @date 2018年11月5日16:49:18
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/emgorgs")
public class CmdOrgController extends BaseController implements ICmdOrgRestService{

    @Autowired
    CmdOrgService service;
    @Autowired
    CmdOrgMapper mapper;

    /**
     * 新增单个应急组织机构
     *
     * @param vo
     * @return ResponseEntity<CmdOrgVo>
     */
    @Override
    public ResponseEntity<CmdOrgVo> createOne(
            @Validated
            @NotNull(message = "CmdOrgVo 不能为null")
            @RequestBody CmdOrgVo vo) {
        CmdOrg entity = mapper.voToEntity(vo);
        CmdOrg result = service.createOne(entity);
        CmdOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 获取单个应急组织机构信息
     *
     * @param id 应急组织机构id
     * @return ResponseEntity<CmdOrgVo>
     */
    @Override
    public ResponseEntity<CmdOrgVo> findOne(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id) {
        CmdOrg result = service.findOne(id);
        CmdOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新单个应急组织机构
     *
     * @param vo
     * @param id
     * @return ResponseEntity<CmdOrgVo>
     */
    @Override
    public ResponseEntity<CmdOrgVo> update(
            @Validated
            @NotNull(message = "CmdOrgVo 不能为null")
            @RequestBody CmdOrgVo vo,
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id) {
        CmdOrg entity = mapper.voToEntity(vo);
        CmdOrg result = service.update(entity,id);
        CmdOrgVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 删除应急组织机构（含该机构下关联的责任单位/人员）
     *
     * @param id 应急组织机构id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空字符串")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 查询应急组织机构树
     *
     * @param params
     * 参数key:schemeId,planId
     * @return ResponseEntity<List < CmdOrgVo>>
     */
    @Override
    public ResponseEntity<List<CmdOrgVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<CmdOrg> resultList = service.findList(params);
        List<CmdOrgVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 新增关联应急组织机构--- 启动预案，根据数字化批量生成
     *
     * @param vos
     * @return ResponseEntity<List < CmdOrgVo>>
     */
    @Override
    public ResponseEntity<List<CmdOrgVo>> createList(
            @NotNull(message = "List<CmdOrgVo> 不能为null")
            @RequestBody List<CmdOrgVo> vos) {
        List<CmdOrg> entityList = mapper.voListToEntityList(vos);
        List<CmdOrg> resultList = service.createList(entityList);
        List<CmdOrgVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
