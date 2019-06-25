package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planTask.PlanTaskListVo;
import com.taiji.emp.res.searchVo.planTask.PlanTaskPageVo;
import com.taiji.emp.res.vo.PlanTaskVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案任务设置管理 feign 接口服务类
 */
@FeignClient(value = "micro-res-planTask")
public interface IPlanTaskRestService {

    /**
     * 根据参数获取PlanTaskVo多条记录
     * 查询参数为 princiOrgName(可选),title(可选),planId(可选),eventGradeId(可选),princiOrgId(可选)
     *  @param planTaskListVo
     *  @return ResponseEntity<List<PlanTaskVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanTaskVo>> findList(@RequestBody PlanTaskListVo planTaskListVo);

    /**
     * 根据参数获取PlanTaskVo多条记录,分页信息
     * 查询参数为 princiOrgName(可选),title(可选),planId(可选),eventGradeId(可选),princiOrgId(可选)
     *          page,size
     *  @param planTaskPageVo
     *  @return ResponseEntity<RestPageImpl<PlanTaskVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PlanTaskVo>> findPage(@RequestBody PlanTaskPageVo planTaskPageVo);

    /**
     * 新增预案任务设置管理PlanTaskVo，PlanTaskVo不能为空
     * @param vo
     * @return ResponseEntity<PlanTaskVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanTaskVo> create(@RequestBody PlanTaskVo vo);

    /**
     * 更新预案任务设置管理PlanTaskVo，PlanTaskVo不能为空
     * @param vo,
     * @param id 要更新PlanTaskVo id
     * @return ResponseEntity<PlanTaskVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanTaskVo> update(@RequestBody PlanTaskVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案任务设置管理PlanTaskVo
     * @param id id不能为空
     * @return ResponseEntity<PlanTaskVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanTaskVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据参数获取PlanTaskVo多条记录
     * 查询参数为 planIds
     *  @param planTaskListVo
     *  @return ResponseEntity<List<PlanTaskVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanIds")
    @ResponseBody
    ResponseEntity<List<PlanTaskVo>> findByPlanIds(@RequestBody PlanTaskListVo planTaskListVo);

}
