package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Support;
import com.taiji.emp.res.feign.ISupportRestService;
import com.taiji.emp.res.mapper.SupportMapper;
import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.service.SupportService;
import com.taiji.emp.res.vo.SupportVo;
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
@RequestMapping("/api/supports")
public class SupportController extends BaseController implements ISupportRestService {

    SupportService service;
    SupportMapper mapper;

    /**
     * 新增社会依托资源SupportVo，SupportVo不能为空
     * @param vo
     * @return ResponseEntity<SupportVo>
     */
    @Override
    public ResponseEntity<SupportVo> create(
            @Validated
            @NotNull(message = "SupportVo 不能为null")
            @RequestBody SupportVo vo){
        Support entity = mapper.voToEntity(vo);
        Support result = service.createOrUpdate(entity);
        SupportVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id 获取应急社会依托资源SupportVo
     * @param id id不能为空
     * @return ResponseEntity<SupportVo>
     */
    @Override
    public ResponseEntity<SupportVo> findOne(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Support result = service.findOne(id);
        SupportVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新应急社会依托资源SupportVo，SupportVo不能为空
     * @param vo,
     * @param id 要更新SupportVo id
     * @return ResponseEntity<SupportVo>
     */
    @Override
    public ResponseEntity<SupportVo> update(
            @Validated
            @NotNull(message = "SupportVo 不能为null")
            @RequestBody SupportVo vo,
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Support entity = mapper.voToEntity(vo);
        Support result = service.createOrUpdate(entity);
        SupportVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取SupportVo多条记录,分页信息
     * 参数key为 name,suppTypeIds(数组),createOrgId
     * page,size
     *  @param supportPageVo
     *  @return ResponseEntity<RestPageImpl<SupportVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<SupportVo>> findPage(@RequestBody SupportPageVo supportPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",supportPageVo.getPage());
        map.add("size",supportPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<Support> pageResult = service.findPage(supportPageVo,pageable);
        RestPageImpl<SupportVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据参数获取SupportVo多条记录
     *参数key为 name,suppTypeIds(数组),createOrgId
     *  @param supportListVo
     *  @return ResponseEntity<List<SupportVo>>
     */
    @Override
    public ResponseEntity<List<SupportVo>> findList(@RequestBody SupportListVo supportListVo) {
        List<Support> result = service.findList(supportListVo);
        List<SupportVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 通过schemeId应急社会依托资源信息
     * @param schemeId
     * @return
     */
    @Override
    public ResponseEntity<List<SupportVo>> findBySchemeId(@NotNull @RequestBody String schemeId) {
        List<SupportVo> resultVoList = service.findBySchemeId(schemeId);
        return new ResponseEntity<>(resultVoList, HttpStatus.OK);
    }

}
