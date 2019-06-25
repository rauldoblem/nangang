package com.taiji.emp.drill.feign;

import com.taiji.emp.drill.searchVo.DrillPlanReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillPlanReceiveVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 演练计划转发接收 feign 接口服务类
 * @author qzp-pc
 * @date 2018年11月01日11:12:18
 */
@FeignClient(value = "micro-drill-drillReceive")
public interface IDrillPlanReceiveRestService {

    /**
     * 上报/下发演练计划
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DrillPlanReceiveVo> create(@RequestBody DrillPlanReceiveVo vo);

    /**
     * 接收演练计划
     * @param drillPlanId
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<DrillPlanReceiveVo> update(@RequestBody String drillPlanId);


    /**
     * 根据条件查询演练计划接收列表——分页
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DrillPlanReceiveVo>> findPage(@RequestBody DrillPlanReceiveSearchVo searchVo);

    /**
     * 根据条件查询 计划接收部门的 接收状态信息列表
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<DrillPlanReceiveVo>> findList(@RequestBody DrillPlanReceiveSearchVo searchVo);

    /**
     * 根据演练计划ID获取 演练计划接收信息
     * @param receiveVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/drillPlanId")
    @ResponseBody
    ResponseEntity<DrillPlanReceiveVo> findByDrillPlanId(@RequestBody DrillPlanReceiveVo receiveVo);


    /**
     * 接收演练计划
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/status/{id}")
    @ResponseBody
    ResponseEntity<DrillPlanReceiveVo> updateStatus(@RequestBody DrillPlanReceiveVo vo, @PathVariable(value = "id")String id);

    /**
     * 判断要上报、下发是否已存在
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/exist")
    @ResponseBody
    ResponseEntity<List<DrillPlanReceiveVo>> findIsExist(@RequestBody DrillPlanReceiveSearchVo vo);
}
