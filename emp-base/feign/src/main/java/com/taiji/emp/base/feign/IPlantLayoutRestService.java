package com.taiji.emp.base.feign;

import com.taiji.emp.base.searchVo.PlantLayoutSearchVo;
import com.taiji.emp.base.vo.PlantLayoutVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 厂区平面图 feign 接口服务类
 * @author qzp-pc
 * @date 2019年01月17日18:22:37
 */
@FeignClient(value = "micro-base-plantLayout")
public interface IPlantLayoutRestService {

    /**
     * 新增厂区平面图
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<PlantLayoutVo> create(@RequestBody PlantLayoutVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改厂区平面图信息
     * @param plantLayoutVo
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<PlantLayoutVo> update(@RequestBody PlantLayoutVo plantLayoutVo);

    /**
     * 根据id获取某条厂区平面图信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlantLayoutVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 查询厂区平面图信息列表------分页
     * @param plantLayoutVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PlantLayoutVo>> findPage(@RequestBody PlantLayoutSearchVo plantLayoutVo);

    /**
     * 查询厂区平面图信息列表------不分页
     * @param plantLayoutVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlantLayoutVo>> findList(@RequestBody PlantLayoutSearchVo plantLayoutVo);
}
