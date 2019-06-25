package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Target;
import com.taiji.emp.res.feign.ITargetRestService;
import com.taiji.emp.res.mapper.TargetMapper;
import com.taiji.emp.res.searchVo.target.TargetSearchVo;
import com.taiji.emp.res.service.TargetService;
import com.taiji.emp.res.vo.TargetVo;
import com.taiji.micro.common.entity.utils.PageUtils;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/rcTarget")
public class TargetController extends BaseController implements ITargetRestService {

    TargetMapper mapper;
    TargetService service;

    /**
     * 根据参数获取RcTargetVo多条记录
     * @param vo 参数key为name(可选),
     * @return ResponseEntity<List<RcTargetVo>>
     */
    @Override
    public ResponseEntity<List<TargetVo>> findList(@RequestBody TargetSearchVo vo) {
        List<Target> list = service.findList(vo);
        List<TargetVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }

    /**
     * 根据参数获取RcTargetVo多条记录,分页信息
     * @param searchVo 参数key为name(可选),
     * @return ResponseEntity<RestPageImpl<RcTargetVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<TargetVo>> findPage(
            @RequestBody TargetSearchVo searchVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",searchVo.getPage());
        params.add("size",searchVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<Target> pageResult = service.findPage(searchVo, page);
        RestPageImpl<TargetVo> voPage = mapper.entityPageToVoPage(pageResult, page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 新增防护目标RcTargetVo，RcTargetVo不能为空
     * @param vo
     * @return ResponseEntity<RcTargetVo>
     */
    @Override
    public ResponseEntity<TargetVo> create(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody TargetVo vo) {
        Target entity = mapper.voToEntity(vo);
        Target result = service.create(entity);
        TargetVo voResult = mapper.entityToVo(result);
        return ResponseEntity.ok(voResult);
    }

    /**
     * 更新防护目标RcTargetVo，RcTargetVo不能为空
     * @param vo
     * @param id 要更新 RcTargetVo id
     * @return ResponseEntity<RcTargetVo>
     */
    @Override
    public ResponseEntity<TargetVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody TargetVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Target entity = mapper.voToEntity(vo);
        Target result = service.update(entity);
        TargetVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id 获取防护目标RcTargetVo
     * @param id id不能为空
     * @return ResponseEntity<RcTargetVo>
     */
    @Override
    public ResponseEntity<TargetVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        Target result = service.findOne(id);
        TargetVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id逻辑删除一条记录。
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
