package com.taiji.emp.base.controller;

import com.netflix.discovery.converters.Auto;
import com.taiji.emp.base.entity.OrgTeam;
import com.taiji.emp.base.entity.OrgTeamMid;
import com.taiji.emp.base.feign.IOrgTeamRestService;
import com.taiji.emp.base.mapper.OrgTeamMapper;
import com.taiji.emp.base.mapper.OrgTeamMidMapper;
import com.taiji.emp.base.service.OrgTeamService;
import com.taiji.emp.base.vo.OrgTeamMidVo;
import com.taiji.emp.base.vo.OrgTeamVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 10:50
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/orgTeam")
public class OrgTeamController implements IOrgTeamRestService{

    @Autowired
    private OrgTeamService service;
    @Autowired
    private OrgTeamMapper mapper;
    @Autowired
    private OrgTeamMidMapper orgTeamMidmapper;
    /**
     * 创建组织分组
     * @param orgTeamVo
     * @return
     */
    @Override
    public ResponseEntity<OrgTeamVo> create(
            @Validated
            @NotNull(message = "orgTeamVo不能为null")
            @RequestBody OrgTeamVo orgTeamVo) {
        OrgTeam orgTeam = mapper.voToEntity(orgTeamVo);
        OrgTeam resultOrgTeam = service.create(orgTeam);
        OrgTeamVo resultOrgTeamVo = mapper.entityToVo(resultOrgTeam);
        return ResponseEntity.ok(resultOrgTeamVo);
    }

    /**
     * 更新一条分组信息
     * @param orgTeamVo
     * @return
     */
    @Override
    public ResponseEntity<OrgTeamVo> update(
            @Validated
            @NotNull(message = "orgTeamVo不能为null")
            @RequestBody OrgTeamVo orgTeamVo) {
        OrgTeam orgTeam = mapper.voToEntity(orgTeamVo);
        OrgTeam resultOrgTeam = service.update(orgTeam);
        OrgTeamVo resultOrgTeamVo = mapper.entityToVo(resultOrgTeam);
        return ResponseEntity.ok(resultOrgTeamVo);
    }

    /**
     * 查找一条分组
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<OrgTeamVo> findOne(
            @Validated
            @NotNull(message = "id不能为null")
            @PathVariable(name = "id") String id) {
        OrgTeam resultOrgTeam = service.findOne(id);
        OrgTeamVo resultOrgTeamVo = mapper.entityToVo(resultOrgTeam);
        return ResponseEntity.ok(resultOrgTeamVo);
    }

    /**
     * 删除一条分组，并删除该分组在中间表的记录
     * @param id
     * @return
     */
    @Override
    public ResponseEntity deleteOne(
            @Validated
            @NotNull(message = "id不能为null")
            @PathVariable(name = "id") String id) {
        service.deleteOne(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 根据userId查询所有分组
     * @param createUserId
     * @return
     */
    @Override
    public ResponseEntity<List<OrgTeamVo>> findAll(
            @Validated
            @NotNull(message = "createUserId不能为null")
            @PathVariable(name = "createUserId") String createUserId) {
        List<OrgTeam> resultEntity =service.findAllByCreateUserId(createUserId);
        List<OrgTeamVo> orgTeamVos = mapper.entityListToVoList(resultEntity);
        return ResponseEntity.ok(orgTeamVos);
    }

    /**
     * 根据teamId 删除该id下的所有中间表信息
     * @param teamId
     * @return
     */
    @Override
    public ResponseEntity deleteOrgTeamMidsByTeamId(
            @Validated
            @NotNull(message = "teamId不能为null")
            @PathVariable(name = "teamId") String teamId) {
        service.deleteOrgTeamMidsByTeamId(teamId);
        return ResponseEntity.ok(null);
    }

    /**
     * 创建分组内组织信息
     * @param orgTeamMidVos
     * @return
     */
    @Override
    public ResponseEntity createOrgTeamMids(
            @Validated
            @NotNull(message = "orgTeamMidVos不能为null")
            @RequestBody List<OrgTeamMidVo> orgTeamMidVos) {
        List<OrgTeamMid> orgTeamMids = orgTeamMidmapper.voListToEntityList(orgTeamMidVos);
        service.createOrgTeamMids(orgTeamMids);
        return ResponseEntity.ok(null);
    }

    /**
     * 根据分组ID获取分组下的组织机构信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<List<OrgTeamMidVo>> searchOrgsByTeamId(
            @Validated
            @NotNull(message = "teamId不能为null")
            @PathVariable(name = "id") String id) {
        List<OrgTeamMid> entitys = service.searchOrgsByTeamId(id);
        List<OrgTeamMidVo> orgTeamMidVos = orgTeamMidmapper.entityListToVoList(entitys);
        return ResponseEntity.ok(orgTeamMidVos);
    }
}
