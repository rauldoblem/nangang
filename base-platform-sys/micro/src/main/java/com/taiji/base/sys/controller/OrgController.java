package com.taiji.base.sys.controller;

import com.taiji.base.sys.entity.Org;
import com.taiji.base.sys.feign.IOrgRestService;
import com.taiji.base.sys.mapper.OrgMapper;
import com.taiji.base.sys.service.OrgService;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>Title:OrgController.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/29 18:33</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/org")
public class OrgController extends BaseController implements IOrgRestService {

    OrgService service;

    OrgMapper mapper;

    /**
     * 根据参数获取OrgVo多条记录。
     * <p>
     * params参数key为parentId（可选），orgName（可选）。
     *
     * @param params
     * @return ResponseEntity<OrgVo>
     */
    @Override
    public ResponseEntity<List<OrgVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        String parentId = "";
        String orgName = "";

        if(params.containsKey("parentId")){
            parentId = params.getFirst("parentId").toString();
        }

        if(params.containsKey("orgName")){
            orgName = params.getFirst("orgName").toString();
        }

        List<Org>   list   = service.findAll(parentId, orgName);
        List<OrgVo> voList = mapper.entityListToVoList(list);

        return ResponseEntity.ok(voList);
    }

    /**
     * 根据OrgVoid获取一条记录。
     *
     * @param id
     * @return ResponseEntity<OrgVo>
     */
    @Override
    public ResponseEntity<OrgVo> find(
            @NotEmpty(message = "id不能为空")
            @PathVariable("id") String id) {
        Org   entity = service.findOne(id);
        OrgVo vo     = mapper.entityToVo(entity);

        return ResponseEntity.ok(vo);
    }

    /**
     * 新增OrgVo，OrgVo不能为空。
     *
     * @param vo
     * @return ResponseEntity<OrgVo>
     */
    @Override
    public ResponseEntity<OrgVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody OrgVo vo) {
        Org   tempEntity = mapper.voToEntity(vo);
        Org   entity     = service.create(tempEntity);
        OrgVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 更新OrgVo，OrgVo不能为空。
     *
     * @param vo
     * @param id 更新OrgVo Id
     * @return ResponseEntity<OrgVo>
     */
    @Override
    public ResponseEntity<OrgVo> update(
            @Validated
            @NotNull(message = "OrgVo不能为null")
            @RequestBody OrgVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")
                    String id) {
        Org   tempEntity = mapper.voToEntity(vo);
        Org   entity     = service.update(tempEntity, id);
        OrgVo tempVo     = mapper.entityToVo(entity);
        return ResponseEntity.ok(tempVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")
                    String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<OrgVo>> findOrgInfo(
            @RequestBody
            @Validated
            @NotNull(message = "orgIds不能为null")List<String> orgIds) {
        List<Org>   list   = service.findOrgInfo(orgIds);
        List<OrgVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据浙能组织机构编码 获取组织机构ID
     * @param znCode
     * @return
     */
    @Override
    public ResponseEntity<OrgVo> findIdByOrgZnCode(
            @Validated
            @NotEmpty(message = "znCode不能为空")
            @RequestParam(name = "znCode") String znCode) {
        Org resultEntity = service.findIdByOrgZnCode(znCode);
        OrgVo resultVo = mapper.entityToVo(resultEntity);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 传入板块OrgCode的前两位，例如01,返回所有以01打头的orgCode对应的orgId的list
     * @param code
     * @return
     */
    @Override
    public ResponseEntity<List<String>> findOrgIdList(
            @Validated
            @NotEmpty(message = "code不能为空")
            @PathVariable(name = "code") String code) {
        List<String> orgIds = service.findOrgIdListByCode(code);
        return ResponseEntity.ok(orgIds);
    }
}
