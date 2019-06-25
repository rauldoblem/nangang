package com.taiji.emp.base.controller;

import com.taiji.emp.base.entity.PlantLayout;
import com.taiji.emp.base.feign.IPlantLayoutRestService;
import com.taiji.emp.base.mapper.PlantLayoutMapper;
import com.taiji.emp.base.searchVo.PlantLayoutSearchVo;
import com.taiji.emp.base.service.PlantLayoutService;
import com.taiji.emp.base.vo.PlantLayoutVo;
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
@RequestMapping("/api/plantLayout")
public class PlantLayoutController extends BaseController implements IPlantLayoutRestService {
    PlantLayoutService service;
    PlantLayoutMapper mapper;


    /**
     * 新增厂区平面图
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<PlantLayoutVo> create(@Validated @NotNull(message = "vo不能为null") @RequestBody PlantLayoutVo vo) {
        PlantLayout entity = mapper.voToEntity(vo);
        PlantLayout result = service.create(entity);
        PlantLayoutVo plantLayoutVo = mapper.entityToVo(result);
        return ResponseEntity.ok(plantLayoutVo);
    }

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(@NotEmpty(message = "id不能为空") @PathVariable(value = "id") String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 修改厂区平面图信息
     * @param plantLayoutVo
     * @return
     */
    @Override
    public ResponseEntity<PlantLayoutVo> update(@Validated @NotNull(message = "plantLayoutVo不能为null") @RequestBody PlantLayoutVo plantLayoutVo) {
        PlantLayout entity = mapper.voToEntity(plantLayoutVo);
        PlantLayout result = service.update(entity);
        PlantLayoutVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取某条厂区平面图信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<PlantLayoutVo> findOne(@NotEmpty(message = "id不能为空") @PathVariable(value = "id") String id) {
        PlantLayout result = service.findOne(id);
        PlantLayoutVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 查询厂区平面图信息列表------分页
     * @param plantLayoutVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<PlantLayoutVo>> findPage(@RequestBody PlantLayoutSearchVo plantLayoutVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",plantLayoutVo.getPage());
        params.add("size",plantLayoutVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<PlantLayout> pageResult = service.findPage(plantLayoutVo, page);
        RestPageImpl<PlantLayoutVo> voPage = mapper.entityPageToVoPage(pageResult, page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 查询厂区平面图信息列表------不分页
     * @param plantLayoutVo
     * @return
     */
    @Override
    public ResponseEntity<List<PlantLayoutVo>> findList(@RequestBody PlantLayoutSearchVo plantLayoutVo) {
        List<PlantLayout> list = service.findList(plantLayoutVo);
        List<PlantLayoutVo> voList = mapper.entityListToVoList(list);
        return ResponseEntity.ok(voList);
    }
}
