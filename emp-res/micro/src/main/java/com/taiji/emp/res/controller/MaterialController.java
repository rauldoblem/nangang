package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Material;
import com.taiji.emp.res.feign.IMaterialRestService;
import com.taiji.emp.res.mapper.MaterialMapper;
import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.material.MaterialPageVo;
import com.taiji.emp.res.service.MaterialService;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.emp.zn.vo.MaterialStatVo;
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
@RequestMapping("/api/materials")
public class MaterialController extends BaseController implements IMaterialRestService {

    MaterialService service;
    MaterialMapper mapper;

    /**
     * 新增应急物资MaterialVo，MaterialVo不能为空
     * @param vo
     * @return ResponseEntity<MaterialVo>
     */
    @Override
    public ResponseEntity<MaterialVo> create(
            @Validated
            @NotNull(message = "ExpertVo 不能为null")
            @RequestBody MaterialVo vo){
        Material entity = mapper.voToEntity(vo);
        Material result = service.createOrUpdate(entity);
        MaterialVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据id 获取应急物资MaterialVo
     * @param id id不能为空
     * @return ResponseEntity<MaterialVo>
     */
    @Override
    public ResponseEntity<MaterialVo> findOne(
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Material result = service.findOne(id);
        MaterialVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 更新应急物资MaterialVo，MaterialVo不能为空
     * @param vo,
     * @param id 要更新MaterialVo id
     * @return ResponseEntity<MaterialVo>
     */
    @Override
    public ResponseEntity<MaterialVo> update(
            @Validated
            @NotNull(message = "MaterialVo 不能为null")
            @RequestBody MaterialVo vo,
            @NotEmpty(message = "id 不能为空")
            @PathVariable(value = "id") String id) {
        Material entity = mapper.voToEntity(vo);
        Material result = service.createOrUpdate(entity);
        MaterialVo resultVo = mapper.entityToVo(result);
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
     * 根据参数获取MaterialVo多条记录,分页信息
     * 参数key为 name,unit,repertoryIds、positionIds、resTypeIds(数组),createOrgId
     *          page,size
     *  @param materialPageVo
     *  @return ResponseEntity<RestPageImpl<MaterialVo>>
     */
    @Override
    public ResponseEntity<RestPageImpl<MaterialVo>> findPage(@RequestBody MaterialPageVo materialPageVo) {

        MultiValueMap<String,Object> map = new LinkedMultiValueMap<>();
        map.add("page",materialPageVo.getPage());
        map.add("size",materialPageVo.getSize());
        Pageable pageable = PageUtils.getPageable(map);

        Page<Material> pageResult = service.findPage(materialPageVo,pageable);
        RestPageImpl<MaterialVo> resultVo = mapper.entityPageToVoPage(pageResult,pageable);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 根据参数获取MaterialVo多条记录
     *参数key为 name,unit,repertoryIds、positionIds、resTypeIds(数组),createOrgId
     *  @param materialPageVo
     *  @return ResponseEntity<List<MaterialVo>>
     */
    @Override
    public ResponseEntity<List<MaterialVo>> findList(@RequestBody MaterialListVo materialPageVo) {
        List<Material> result = service.findList(materialPageVo);
        List<MaterialVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    /**
     * 去重获取物资类型大类ID多条记录
     * @return
     */
    @Override
    public ResponseEntity<List<MaterialVo>> findGroupList(@RequestBody List<String> listCode) {
        List<Material> result = service.findGroupList(listCode);
        List<MaterialVo> resultVo = mapper.entityListToVoList(result);
        return new ResponseEntity<>(resultVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MaterialStatVo>> findInfo(@RequestBody MaterialSearchVo vo) {
        List<MaterialStatVo> result = service.findInfo(vo);
        return ResponseEntity.ok(result);
    }

    /**
     * 通过schemeId应急物质信息
     * @param schemeId
     * @return
     */
    @Override
    public ResponseEntity<List<MaterialVo>> findBySchemeId(@NotNull @RequestBody String schemeId) {
        List<MaterialVo> resultVoList = service.findBySchemeId(schemeId);
        return new ResponseEntity<>(resultVoList, HttpStatus.OK);
    }

}
