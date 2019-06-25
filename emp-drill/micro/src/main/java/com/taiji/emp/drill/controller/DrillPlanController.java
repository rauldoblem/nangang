package com.taiji.emp.drill.controller;

import com.taiji.emp.drill.entity.DrillPlan;
import com.taiji.emp.drill.feign.IDrillPlanRestService;
import com.taiji.emp.drill.mapper.DrillPlanMapper;
import com.taiji.emp.drill.searchVo.DrillPlanSearchVo;
import com.taiji.emp.drill.service.DrillPlanService;
import com.taiji.emp.drill.vo.DrillPlanVo;
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
@RequestMapping("/api/drillPlan")
public class DrillPlanController extends BaseController implements IDrillPlanRestService {

    DrillPlanMapper mapper;
    DrillPlanService service;

    /**
     * 新增演练计划
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanVo> create(
            @Validated
            @NotNull(message = "vo不能为空")
            @RequestBody DrillPlanVo vo) {
        DrillPlan entity = mapper.voToEntity(vo);
        DrillPlan result = service.create(entity);
        DrillPlanVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id删除一条记录
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
     * 更新演练计划
     * @param vo
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DrillPlanVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DrillPlan entity = mapper.voToEntity(vo);
        DrillPlan result = service.update(entity);
        DrillPlanVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据id获取演练计划信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        DrillPlan entity = service.findOne(id);
        DrillPlanVo resultVo = mapper.entityToVo(entity);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 根据条件查询演练计划列表——分页
     * @param searchVo
     * @return
     */
    @Override
    public ResponseEntity<RestPageImpl<DrillPlanVo>> findPage(
            @RequestBody DrillPlanSearchVo searchVo) {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("page",searchVo.getPage());
        params.add("size",searchVo.getSize());
        Pageable page = PageUtils.getPageable(params);
        Page<DrillPlan> pageResult = service.findPage(searchVo,page);
        RestPageImpl<DrillPlanVo> voPage = mapper.entityPageToVoPage(pageResult,page);
        return ResponseEntity.ok(voPage);
    }

    /**
     * 上报/下发演练计划——更改状态
     * @param vo
     * @return
     */
    @Override
    public ResponseEntity<DrillPlanVo> updateStatusById(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody DrillPlanVo vo) {
        DrillPlan entity = mapper.voToEntity(vo);
        DrillPlan result = service.updateStatusById(entity);
        DrillPlanVo resultVo = mapper.entityToVo(result);
        return ResponseEntity.ok(resultVo);
    }
}
