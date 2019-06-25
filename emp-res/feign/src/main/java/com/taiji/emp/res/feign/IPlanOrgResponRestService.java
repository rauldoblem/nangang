package com.taiji.emp.res.feign;

import com.taiji.emp.res.vo.PlanOrgResponVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  预案责任人、单位管理 feign 接口服务类
 */
@FeignClient(value = "micro-res-planOrgRespon")
public interface IPlanOrgResponRestService {

    /**
     * 新增预案责任人、单位PlanOrgResponVo，PlanOrgResponVo不能为空
     * @param vo
     * @return ResponseEntity<PlanOrgResponVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanOrgResponVo> create(@RequestBody PlanOrgResponVo vo);

    /**
     * 根据id 获取预案责任人、单位PlanOrgResponVo
     * @param id id不能为空
     * @return ResponseEntity<PlanOrgResponVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanOrgResponVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新预案责任人、单位PlanOrgResponVo，PlanOrgResponVo不能为空
     * @param vo,
     * @param id 要更新PlanOrgResponVo id
     * @return ResponseEntity<PlanOrgResponVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanOrgResponVo> update(@RequestBody PlanOrgResponVo vo, @PathVariable(value = "id") String id);

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
     * 根据参数获取PlanOrgResponVo多条记录
     * 参数key为 planOrgId
     *  @param planOrgResponVo
     *  @return ResponseEntity<List<PlanOrgResponVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanOrgResponVo>> findList(@RequestBody PlanOrgResponVo planOrgResponVo);

    /**
     * 根据参数获取PlanOrgResponVo多条记录
     * 参数key为 planOrgIds
     *  @param planOrgResponVo
     *  @return ResponseEntity<List<PlanOrgResponVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findByPlanOrgIds")
    @ResponseBody
    ResponseEntity<List<PlanOrgResponVo>> findByPlanOrgIds(@RequestBody PlanOrgResponVo planOrgResponVo);
}
