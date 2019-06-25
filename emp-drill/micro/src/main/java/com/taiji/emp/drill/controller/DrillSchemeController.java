package com.taiji.emp.drill.controller;

import com.taiji.emp.drill.entity.DrillScheme;
import com.taiji.emp.drill.feign.IDrillSchemeRestService;
import com.taiji.emp.drill.mapper.DrillSchemeMapper;
import com.taiji.emp.drill.searchVo.DrillSchemeSearchVo;
import com.taiji.emp.drill.service.DrillSchemeService;
import com.taiji.emp.drill.vo.DrillSchemeVo;
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

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/drillScheme")
public class DrillSchemeController extends BaseController implements IDrillSchemeRestService {

    DrillSchemeService service;
    DrillSchemeMapper mapper;

    /**
     * 新增演练方案
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillSchemeVo vo) {
        DrillScheme entity = mapper.voToEntity(vo);
        DrillScheme result = service.create(entity);
        DrillSchemeVo entityToVo = mapper.entityToVo(result);
        return ResponseEntity.ok(entityToVo);
    }

    /**
     * 根据id删除演练方案信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Void> deleteLogic(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id")String id) {
        service.deleteLogic(id, DelFlagEnum.DELETE);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     * 根据id修改演练方案信息
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeVo> update(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillSchemeVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DrillScheme entity = mapper.voToEntity(vo);
        DrillScheme result = service.update(entity);
        DrillSchemeVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取一条演练方案信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DrillScheme entity = service.findOne(id);
        DrillSchemeVo vo = mapper.entityToVo(entity);
        return ResponseEntity.ok(vo);
    }

    /**
     * 根据条件查询演练方案列表——分页
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<DrillSchemeVo>> findPage(
            @RequestBody DrillSchemeSearchVo searchVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",searchVo.getPage());
        params.add("size",searchVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<DrillScheme> pageResult = service.findPage(searchVo,page);
        RestPageImpl<DrillSchemeVo> voPage = mapper.entityPageToVoPage(pageResult, page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 上报/下发演练方案——更改状态
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DrillSchemeVo> updateStatusById(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DrillSchemeVo vo) {
        DrillScheme entity = mapper.voToEntity(vo);
        DrillScheme result = service.updateStatusById(entity);
        DrillSchemeVo entityToVo = mapper.entityToVo(result);
        return ResponseEntity.ok(entityToVo);
    }
}
