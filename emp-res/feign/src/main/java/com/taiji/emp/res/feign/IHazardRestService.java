package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.hazard.HazardPageVo;
import com.taiji.emp.res.vo.HazardVo;
import com.taiji.emp.zn.vo.HazardStatVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 危险源 feign 接口服务类
 * @author sun yi
 * @date 2018年10月16日
 */
@FeignClient(value = "micro-res-hazard")
public interface IHazardRestService {

    /**
     * 根据参数获取HazardVo多条记录
     *  @param hazardPageVo 带查询条件的Vo
     *  @return ResponseEntity<List<HazardVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<HazardVo>> findList(@RequestBody HazardPageVo hazardPageVo);

    /**
     * 根据参数获取HazardVo多条记录,分页信息
     *  @param hazardPageVo 带查询条件的Vo
     *  @return ResponseEntity<RestPageImpl<HazardVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<HazardVo>> findPage(@RequestBody HazardPageVo hazardPageVo);

    /**
     * 新增危险源HazardVo，HazardVo不能为空
     * @param vo
     * @return ResponseEntity<HazardVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<HazardVo> create(@RequestBody HazardVo vo);

    /**
     * 更新危险源HazardVo，HazardVo不能为空
     * @param vo,
     * @param id 要更新HazardVo id
     * @return ResponseEntity<HazardVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<HazardVo> update(@RequestBody HazardVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取危险源HazardVo
     * @param id id不能为空
     * @return ResponseEntity<HazardVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<HazardVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 危险源级别ID集合
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/group/list")
    @ResponseBody
    ResponseEntity<List<HazardVo>> findGroupList(@RequestBody List<String> listCode);

    @RequestMapping(method = RequestMethod.POST, path = "/find/info")
    @ResponseBody
    ResponseEntity<List<HazardStatVo>> findInfo(@RequestBody MaterialSearchVo vo);
}
