package com.taiji.emp.drill.feign;

import com.taiji.emp.drill.searchVo.DrillPlanSearchVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 演练计划 feign 接口服务类
 * @author qzp-pc
 * @date 2018年11月01日11:12:18
 */
@FeignClient(value = "micro-drill-drillPlan")
public interface IDrillPlanRestService {

    /**
     * 新增演练计划
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DrillPlanVo> create(@RequestBody DrillPlanVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 更新演练计划
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DrillPlanVo> update(@RequestBody DrillPlanVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id获取演练计划信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DrillPlanVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询演练计划列表——分页
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DrillPlanVo>> findPage(@RequestBody DrillPlanSearchVo searchVo);

    /**
     * 上报/下发演练计划——更改状态
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<DrillPlanVo>  updateStatusById(@RequestBody DrillPlanVo vo);
}
