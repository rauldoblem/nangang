package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planMaterial.PlanMaterialListVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.res.vo.PlanMaterialVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案物资管理基础 feign 接口服务类
 */
@FeignClient(value = "micro-res-planMaterial")
public interface IPlanMaterialRestService {

    /**
     * 根据参数获取PlanMaterialVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planMaterialListVo
     *  @return ResponseEntity<List<PlanMaterialVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanMaterialVo>> findList(@RequestBody PlanMaterialListVo planMaterialListVo);

    /**
     * 新增预案物资管理基础PlanMaterialVo，PlanMaterialVo不能为空
     * @param materialList
     * @return ResponseEntity<PlanMaterialVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<List<PlanMaterialVo>> create(@RequestBody List<PlanMaterialVo> materialList,@RequestParam(value = "planId")  String planId);

    /**
     * 更新预案物资管理基础PlanMaterialVo，PlanMaterialVo不能为空
     * @param vo
     * @param id 要更新PlanMaterialVo id
     * @return ResponseEntity<PlanMaterialVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanMaterialVo> update(@RequestBody PlanMaterialVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案物资管理基础PlanMaterialVo
     * @param id id不能为空
     * @return ResponseEntity<PlanMaterialVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanMaterialVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据参数物理删除一条记录。PlanMaterialVo，PlanMaterialVo不能为空
     * planId、eventGradeID、expertId
     * @param vo,
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    @ResponseBody
    ResponseEntity<Void> deletePhysical(@RequestBody PlanMaterialVo vo);

    /**
     * 根据参数获取MaterialVo多条记录
     * 查询参数为 planIds
     *  @param planMaterialListVo
     *  @return ResponseEntity<List<MaterialVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanIds")
    @ResponseBody
    ResponseEntity<List<MaterialVo>> findByPlanIds(@RequestBody PlanMaterialListVo planMaterialListVo);

}
