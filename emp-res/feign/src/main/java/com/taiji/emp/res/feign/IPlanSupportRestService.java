package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planSupport.PlanSupportListVo;
import com.taiji.emp.res.searchVo.planSupport.PlanSupportPageVo;
import com.taiji.emp.res.vo.PlanSupportVo;
import com.taiji.emp.res.vo.SupportVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案社会依托资源管理基础 feign 接口服务类
 */
@FeignClient(value = "micro-res-planSupport")
public interface IPlanSupportRestService {

    /**
     * 根据参数获取PlanSupportVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planSupportListVo
     *  @return ResponseEntity<List<PlanSupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanSupportVo>> findList(@RequestBody PlanSupportListVo planSupportListVo);

    /**
     * 根据参数获取PlanSupportVo多条记录,分页信息
     * 查询参数为 查询参数为 planId(可选),eventGradeID(可选)
     *          page,size
     *  @param planSupportPageVo
     *  @return ResponseEntity<RestPageImpl<PlanSupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PlanSupportVo>> findPage(@RequestBody PlanSupportPageVo planSupportPageVo);

    /**
     * 新增预案社会依托资源管理基础PlanSupportVo，PlanSupportVo不能为空
     * @param vo
     * @return ResponseEntity<PlanSupportVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanSupportVo> create(@RequestBody PlanSupportVo vo);

    /**
     * 更新预案社会依托资源管理基础PlanSupportVo，PlanSupportVo不能为空
     * @param vo
     * @param id 要更新PlanSupportVo id
     * @return ResponseEntity<PlanSupportVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanSupportVo> update(@RequestBody PlanSupportVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取社会依托资源管理基础PlanSupportVo
     * @param id id不能为空
     * @return ResponseEntity<PlanSupportVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanSupportVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据参数物理删除一条记录。PlanSupportVo，PlanSupportVo不能为空
     * planId、eventGradeID、expertId
     * @param vo,
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    @ResponseBody
    ResponseEntity<Void> deletePhysical(@RequestBody PlanSupportVo vo);

    /**
     * 根据参数获取SupportVo多条记录
     * 查询参数为 planIds
     *  @param planSupportListVo
     *  @return ResponseEntity<List<SupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanIds")
    @ResponseBody
    ResponseEntity<List<SupportVo>> findByPlanIds(@RequestBody PlanSupportListVo planSupportListVo);
}
