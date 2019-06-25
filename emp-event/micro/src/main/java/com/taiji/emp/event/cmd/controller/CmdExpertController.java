package com.taiji.emp.event.cmd.controller;

import com.taiji.emp.event.cmd.entity.CmdExpert;
import com.taiji.emp.event.cmd.feign.ICmdExpertRestService;
import com.taiji.emp.event.cmd.mapper.CmdExpertMapper;
import com.taiji.emp.event.cmd.service.CmdExpertService;
import com.taiji.emp.event.cmd.vo.CmdExpertVo;
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
 *  应急处置--关联应急专家 接口服务实现类
 * @author qizhijie-pc
 * @date 2018年11月7日11:26:20
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cmd/experts")
public class CmdExpertController extends BaseController implements ICmdExpertRestService{

    @Autowired
    CmdExpertService service;
    @Autowired
    CmdExpertMapper mapper;

    /**
     * 新增单个应急专家关联信息
     *
     * @param vo
     * @return ResponseEntity<CmdExpertVo>
     */
    @Override
    public ResponseEntity<CmdExpertVo> createOne(
            @Validated
            @NotNull(message = "CmdExpertVo 不能为null")
            @RequestBody CmdExpertVo vo) {
        CmdExpert entity = mapper.voToEntity(vo);
        CmdExpert result = service.createOne(entity);
        CmdExpertVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 新增多个应急专家关联信息
     *
     * @param vos
     * @return ResponseEntity<List < CmdExpertVo>>
     */
    @Override
    public ResponseEntity<List<CmdExpertVo>> createList(
            @Validated
            @NotNull(message = "List<CmdExpertVo> 不能为null")
            @RequestBody List<CmdExpertVo> vos) {
        List<CmdExpert> entityList = mapper.voListToEntityList(vos);
        List<CmdExpert> resultList = service.createList(entityList);
        List<CmdExpertVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }

    /**
     * 删除应急专家关联信息
     *
     * @param id 应急专家关联表Id
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
     * 根据条件查询处置方案已关联的应急专家信息
     *
     * @param params 参数key:schemeId,name,specialty
     * @return ResponseEntity<List < CmdExpertVo>>
     */
    @Override
    public ResponseEntity<List<CmdExpertVo>> findList(@RequestParam MultiValueMap<String, Object> params) {
        List<CmdExpert> resultList = service.findList(params);
        List<CmdExpertVo> resultVoList = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVoList);
    }
}
