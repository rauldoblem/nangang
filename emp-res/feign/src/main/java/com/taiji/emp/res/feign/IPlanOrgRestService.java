package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planOrg.PlanOrgListVo;
import com.taiji.emp.res.vo.PlanOrgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案组织机构管理 feign 接口服务类
 */
@FeignClient(value = "micro-res-planOrg")
public interface IPlanOrgRestService {

    /**
     * 根据参数获取PlanOrgVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planOrgListVo
     *  @return ResponseEntity<List<PlanOrgVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanOrgVo>> findList(@RequestBody PlanOrgListVo planOrgListVo);

    /**
     * 根据参数获取PlanOrgVo多条记录
     * 查询参数为 planIds
     *  @param planOrgListVo
     *  @return ResponseEntity<List<PlanOrgVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanIds")
    @ResponseBody
    ResponseEntity<List<PlanOrgVo>> findListByPlanIds(@RequestBody PlanOrgListVo planOrgListVo);

    /**
     * 新增预案组织机构管理PlanOrgVo，PlanOrgVo不能为空
     * @param vo
     * @return ResponseEntity<PlanOrgVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanOrgVo> create(@RequestBody PlanOrgVo vo);

    /**
     * 更新预案组织机构管理PlanOrgVo，PlanOrgVo不能为空
     * @param vo,
     * @param id 要更新PlanOrgVo id
     * @return ResponseEntity<PlanOrgVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanOrgVo> update(@RequestBody PlanOrgVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案组织机构管理PlanOrgVo
     * @param id id不能为空
     * @return ResponseEntity<PlanOrgVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanOrgVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

}
