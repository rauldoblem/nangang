package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.vo.ExpertVo;
import com.taiji.emp.res.vo.PlanExpertVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案专家管理基础 feign 接口服务类
 */
@FeignClient(value = "micro-res-planExpert")
public interface IPlanExpertRestService {

    /**
     * 根据参数获取PlanExpertVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planExpertListVo
     *  @return ResponseEntity<List<PlanExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanExpertVo>> findList(@RequestBody PlanExpertListVo planExpertListVo);

    /**
     * 根据参数获取PlanExpertVo多条记录,分页信息
     * 查询参数为 查询参数为 planId(可选),eventGradeID(可选)
     *          page,size
     *  @param planExpertPageVo
     *  @return ResponseEntity<RestPageImpl<PlanExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PlanExpertVo>> findPage(@RequestBody PlanExpertPageVo planExpertPageVo);

    /**
     * 新增预案专家管理基础PlanExpertVo，PlanExpertVo不能为空
     * @param vo
     * @return ResponseEntity<PlanExpertVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanExpertVo> create(@RequestBody PlanExpertVo vo);

    /**
     * 更新预案专家管理基础PlanExpertVo，PlanExpertVo不能为空
     * @param vo
     * @param id 要更新PlanExpertVo id
     * @return ResponseEntity<PlanExpertVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanExpertVo> update(@RequestBody PlanExpertVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案专家管理基础PlanExpertVo
     * @param id id不能为空
     * @return ResponseEntity<PlanExpertVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanExpertVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据参数物理删除一条记录。PlanExpertVo，PlanExpertVo不能为空
     * planId、eventGradeID、expertId
     * @param vo,
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    @ResponseBody
    ResponseEntity<Void> deletePhysical(@RequestBody PlanExpertVo vo);

    /**
     * 根据参数获取ExpertVo多条记录
     * 查询参数为 planIds
     *  @param planExpertListVo
     *  @return ResponseEntity<List<ExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanIds")
    @ResponseBody
    ResponseEntity<List<ExpertVo>> findByPlanIds(@RequestBody PlanExpertListVo planExpertListVo);

}
