package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.CmdMaterial;
import com.taiji.emp.event.cmd.feign.ICmdMaterialRestService;
import com.taiji.emp.event.cmd.mapper.CmdMaterialMapper;
import com.taiji.emp.event.cmd.service.CmdMaterialService;
import com.taiji.emp.event.cmd.vo.CmdMaterialVo;
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
 *  应急处置--关联应急物资 接口服务实现类
 * @author qizhijie-pc
 * @date 2018年11月8日10:44:12
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/materials")
public class CmdMaterialController extends BaseController implements ICmdMaterialRestService{

    @Autowired
    CmdMaterialService service;
    @Autowired
    CmdMaterialMapper mapper;


    /**
     * 新增单个应急物资关联信息
     *
     * @param vo
     * @return ResponseEntity<CmdMaterialVo>
     */
    @Override
    public ResponseEntity<CmdMaterialVo> createOne(
            @Validated
            @NotNull(message = "CmdMaterialVo 不能为null")
            @RequestBody CmdMaterialVo vo) {
        CmdMaterial entity = mapper.voToEntity(vo);
        CmdMaterial result = service.createOne(entity);
        CmdMaterialVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 新增多个应急物资关联信息
     *
     * @param vos
     * @return ResponseEntity<List < CmdMaterialVo>>
     */
    @Override
    public ResponseEntity<List<CmdMaterialVo>> createList(
            @Validated
            @NotNull(message = "List<CmdMaterialVo> 不能为null")
            @RequestBody
            List<CmdMaterialVo> vos) {
        List<CmdMaterial> entityList = mapper.voListToEntityList(vos);
        List<CmdMaterial> resultList = service.createList(entityList);
        List<CmdMaterialVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 删除应急物资关联信息
     *
     * @param id 应急物资关联表Id
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
     * 根据条件查询处置方案已关联的应急物资信息
     *
     * @param params 参数key:schemeId,name,resTypeName
     * @return ResponseEntity<List < CmdMaterialVo>>
     */
    @Override
    public ResponseEntity<List<CmdMaterialVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<CmdMaterial> resultList = service.findList(params);
        List<CmdMaterialVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
