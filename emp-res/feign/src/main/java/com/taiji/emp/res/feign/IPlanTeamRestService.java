package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planExpert.PlanExpertListVo;
import com.taiji.emp.res.searchVo.planExpert.PlanExpertPageVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamListVo;
import com.taiji.emp.res.searchVo.planTeam.PlanTeamPageVo;
import com.taiji.emp.res.vo.PlanExpertVo;
import com.taiji.emp.res.vo.PlanTeamVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案团队管理基础 feign 接口服务类
 */
@FeignClient(value = "micro-res-planTeam")
public interface IPlanTeamRestService {

    /**
     * 根据参数获取PlanTeamVo多条记录
     * 查询参数为 planId(可选),eventGradeID(可选)
     *  @param planTeamListVo
     *  @return ResponseEntity<List<PlanTeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanTeamVo>> findList(@RequestBody PlanTeamListVo planTeamListVo);

    /**
     * 根据参数获取PlanTeamVo多条记录,分页信息
     * 查询参数为 查询参数为 planId(可选),eventGradeID(可选)
     *          page,size
     *  @param planTeamPageVo
     *  @return ResponseEntity<RestPageImpl<PlanTeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PlanTeamVo>> findPage(@RequestBody PlanTeamPageVo planTeamPageVo);

    /**
     * 新增预案团队管理基础PlanTeamVo，PlanTeamVo不能为空
     * @param vo
     * @return ResponseEntity<PlanTeamVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanTeamVo> create(@RequestBody PlanTeamVo vo);

    /**
     * 更新预案团队管理基础PlanTeamVo，PlanTeamVo不能为空
     * @param vo
     * @param id 要更新PlanTeamVo id
     * @return ResponseEntity<PlanTeamVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanTeamVo> update(@RequestBody PlanTeamVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案团队管理基础PlanTeamVo
     * @param id id不能为空
     * @return ResponseEntity<PlanTeamVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanTeamVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据参数物理删除一条记录。PlanTeamVo，PlanTeamVo不能为空
     * planId、eventGradeID、teamId
     * @param vo
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    @ResponseBody
    ResponseEntity<Void> deletePhysical(@RequestBody PlanTeamVo vo);

    /**
     * 根据参数获取TeamVo多条记录
     * 查询参数为 planIds
     *  @param planTeamListVo
     *  @return ResponseEntity<List<TeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanIds")
    @ResponseBody
    ResponseEntity<List<TeamVo>> findByPlanIds(@RequestBody PlanTeamListVo planTeamListVo);

}
