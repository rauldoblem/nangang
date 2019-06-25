package com.taiji.emp.drill.feign;

import com.taiji.emp.drill.searchVo.DrillSchemeReceiveSearchVo;
import com.taiji.emp.drill.vo.DrillSchemeReceiveVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 演练方案转发接收 feign 接口服务类
 * @author qzp-pc
 * @date 2018年11月05日11:12:18
 */
@FeignClient(value = "micro-drill-drillSchemeReceive")
public interface IDrillSchemeReceiveRestService {

    /**
     * 根据条件查询演练方案接收列表——分页
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<DrillSchemeReceiveVo>> findPage(@RequestBody DrillSchemeReceiveSearchVo searchVo);

    /**
     * 上报/下发演练方案
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DrillSchemeReceiveVo>  create(DrillSchemeReceiveVo vo);

    /**
     * 根据演练方案ID获取 演练方案接收信息
     * @param drillSchemeId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{drillSchemeId}")
    @ResponseBody
    ResponseEntity<DrillSchemeReceiveVo> findByDrillSchemeId(@PathVariable(value = "drillSchemeId") String drillSchemeId);

    /**
     * 接收演练方案
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/status/{id}")
    @ResponseBody
    ResponseEntity<DrillSchemeReceiveVo>  updateStatus(@RequestBody DrillSchemeReceiveVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据条件查询 方案接受部门的 接受状态信息列表
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<DrillSchemeReceiveVo>> findList(@RequestBody DrillSchemeReceiveSearchVo searchVo);

    /**
     * 判断要上报、下发是否已存在
     * @param searchVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/exist")
    @ResponseBody
    ResponseEntity<List<DrillSchemeReceiveVo>> findIsExist(@RequestBody DrillSchemeReceiveSearchVo searchVo);
}
