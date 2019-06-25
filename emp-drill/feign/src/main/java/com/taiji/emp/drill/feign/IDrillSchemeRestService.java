package com.taiji.emp.drill.feign;

import com.taiji.emp.drill.searchVo.DrillSchemeSearchVo;
import com.taiji.emp.drill.vo.DrillPlanVo;
import com.taiji.emp.drill.vo.DrillSchemeVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 演练方案 feign 接口服务类
 * @author qzp-pc
 * @date 2018年11月05日11:12:18
 */
@FeignClient(value = "micro-drill-drillScheme")
public interface IDrillSchemeRestService {

    /**
     * 新增演练方案
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DrillSchemeVo> create(@RequestBody DrillSchemeVo vo);

    /**
     * 根据id删除演练方案信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据id修改演练方案信息
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DrillSchemeVo>  update(@RequestBody DrillSchemeVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id获取一条演练方案信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DrillSchemeVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询演练方案列表——分页
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DrillSchemeVo>> findPage(@RequestBody DrillSchemeSearchVo searchVo);


    /**
     * 上报/下发演练方案——更改状态
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<DrillSchemeVo> updateStatusById(@RequestBody DrillSchemeVo vo);
}
