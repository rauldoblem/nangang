package com.taiji.emp.res.controller;

import com.taiji.emp.res.entity.Material;
import com.taiji.emp.res.entity.PlanMaterial;
import com.taiji.emp.res.feign.IPlanMaterialRestService;
import com.taiji.emp.res.mapper.MaterialMapper;
import com.taiji.emp.res.mapper.PlanMaterialMapper;
import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.service.PlanMaterialService;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/planMaterials")
public class PlanMaterialController extends BaseController implements IPlanMaterialRestService {

    PlanMaterialService service;
    PlanMaterialMapper mapper;
    MaterialMapper materialMapper;

    /**
     * 根据参数获取PlanMaterialVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planMaterialListVo
     *  @return ResponseEntity<List<PlanMaterialVo>>
     */
    @Override
    public ResponseEntity<List<PlanMaterialVo>> findList(@RequestBody PlanMaterialListVo planMaterialListVo) {
        List<PlanMaterial> list = service.findList(planMaterialListVo);
        List<PlanMaterialVo> voList = mapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }

    /**
     * 新增预案物资管理基础PlanMaterialVo，PlanMaterialVo不能为空
     * @param voList
     * @return ResponseEntity<PlanMaterialVo>
     */
    @Override
    public ResponseEntity<List<PlanMaterialVo>> create(
            @Validated
            @NotNull(message = "list不能为null")
            @RequestBody List<PlanMaterialVo> voList,
            @RequestParam(value = "planId") String planId) {
        List<PlanMaterial> list = mapper.voListToEntityList(voList);
        List<PlanMaterial> resultList = service.create(list,planId);
        List<PlanMaterialVo> resultVo = mapper.entityListToVoList(resultList);
        return ResponseEntity.ok(resultVo);
    }

    /**
     * 更新预案物资管理基础PlanMaterialVo，PlanMaterialVo不能为空
     * @param vo
     * @param id 要更新PlanMaterialVo id
     * @return ResponseEntity<PlanMaterialVo>
     */
    @Override
    public ResponseEntity<PlanMaterialVo> update(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanMaterialVo vo,
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanMaterial entity = mapper.voToEntity(vo);
        //service.createforPlanMaterial(entity,id);
        PlanMaterialVo resultVo = mapper.entityToVo(entity);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据id 获取预案物资管理基础PlanMaterialVo
     * @param id id不能为空
     * @return ResponseEntity<PlanMaterialVo>
     */
    @Override
    public ResponseEntity<PlanMaterialVo> findOne(
            @NotEmpty(message = "id不能为空")
            @PathVariable(value = "id") String id) {
        PlanMaterial result = service.findOne(id);
        PlanMaterialVo resultVo = mapper.entityToVo(result);
        return new ResponseEntity<>(resultVo,HttpStatus.OK);
    }

    /**
     * 根据参数物理删除一条记录。PlanMaterialVo，PlanMaterialVo不能为空
     * planId、eventGradeID、expertId
     * @param vo,
     * @return ResponseEntity<Void>
     */
    @Override
    public ResponseEntity<Void> deletePhysical(
            @Validated
            @NotNull(message = "vo不能为null")
            @RequestBody PlanMaterialVo vo)  {
        service.deletePhysical(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据参数获取MaterialVo多条记录
     * 查询参数为 planIds
     *  @param planMaterialListVo
     *  @return ResponseEntity<List<MaterialVo>>
     */
    @Override
    public ResponseEntity<List<MaterialVo>> findByPlanIds(@RequestBody PlanMaterialListVo planMaterialListVo) {
        List<Material> list = service.findMaterialsByPlanIds(planMaterialListVo);
        List<MaterialVo> voList = materialMapper.entityListToVoList(list);
        return new ResponseEntity<>(voList, HttpStatus.OK);
    }
}
