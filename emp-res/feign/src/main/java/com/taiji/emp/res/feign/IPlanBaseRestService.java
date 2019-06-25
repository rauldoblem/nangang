package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planBase.PlanBaseListVo;
import com.taiji.emp.res.searchVo.planBase.PlanBasePageVo;
import com.taiji.emp.res.vo.PlanBaseVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *预案管理基础 feign 接口服务类
 */
@FeignClient(value = "micro-res-planBase")
public interface IPlanBaseRestService {

    /**
     * 根据参数获取PlanBaseVo多条记录
     * 查询参数为 name(可选),eventTypeId(可选),planTypeId(可选),createOrgId
     *  @param planBaseListVo
     *  @return ResponseEntity<List<PlanBaseVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanBaseVo>> findList(@RequestBody PlanBaseListVo planBaseListVo);

    /**
     * 根据参数获取PlanBaseVo多条记录,分页信息
     * 查询参数为 name(可选),eventTypeId(可选),planTypeId(可选),createOrgId
     *          page,size
     *  @param planBasePageVo
     *  @return ResponseEntity<RestPageImpl<PlanBaseVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<PlanBaseVo>> findPage(@RequestBody PlanBasePageVo planBasePageVo);

    /**
     * 新增预案管理基础PlanBaseVo，PlanBaseVo不能为空
     * @param vo
     * @return ResponseEntity<PlanBaseVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanBaseVo> create(@RequestBody PlanBaseVo vo);

    /**
     * 更新预案管理基础PlanBaseVo，PlanBaseVo不能为空
     * @param vo,
     * @param id 要更新PlanBaseVo id
     * @return ResponseEntity<PlanBaseVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanBaseVo> update(@RequestBody PlanBaseVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案管理基础PlanBaseVo
     * @param id id不能为空
     * @return ResponseEntity<PlanBaseVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanBaseVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

}
