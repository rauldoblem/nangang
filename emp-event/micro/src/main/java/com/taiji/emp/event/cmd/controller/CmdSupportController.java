package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.CmdSupport;
import com.taiji.emp.event.cmd.feign.ICmdSupportRestService;
import com.taiji.emp.event.cmd.mapper.CmdSupportMapper;
import com.taiji.emp.event.cmd.service.CmdSupportService;
import com.taiji.emp.event.cmd.vo.CmdSupportVo;
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
 *  应急处置--关联社会依托资源 接口服务实现类
 * @author qizhijie-pc
 * @date 2018年11月8日10:44:12
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/supports")
public class CmdSupportController extends BaseController implements ICmdSupportRestService{

    @Autowired
    CmdSupportService service;
    @Autowired
    CmdSupportMapper mapper;

    /**
     * 新增单个社会依托资源关联信息
     *
     * @param vo
     * @return ResponseEntity<CmdSupportVo>
     */
    @Override
    public ResponseEntity<CmdSupportVo> createOne(
            @Validated
            @NotNull(message = "CmdSupportVo 不能为null")
            @RequestBody CmdSupportVo vo) {
        CmdSupport entity = mapper.voToEntity(vo);
        CmdSupport result = service.createOne(entity);
        CmdSupportVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 新增多个社会依托资源关联信息
     *
     * @param vos
     * @return ResponseEntity<List < CmdSupportVo>>
     */
    @Override
    public ResponseEntity<List<CmdSupportVo>> createList(
            @Validated
            @NotNull(message = "List<CmdSupportVo> 不能为null")
            @RequestBody
            List<CmdSupportVo> vos) {
        List<CmdSupport> entityList = mapper.voListToEntityList(vos);
        List<CmdSupport> resultList = service.createList(entityList);
        List<CmdSupportVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 删除社会依托资源关联信息
     *
     * @param id 社会依托资源关联表Id
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
     * 根据条件查询处置方案已关联的社会依托资源
     *
     * @param params 参数key:schemeId,name,address
     * @return ResponseEntity<List < CmdSupportVo>>
     */
    @Override
    public ResponseEntity<List<CmdSupportVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<CmdSupport> resultList = service.findList(params);
        List<CmdSupportVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
