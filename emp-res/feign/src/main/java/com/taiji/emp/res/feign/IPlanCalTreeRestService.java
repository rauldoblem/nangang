package com.taiji.emp.res.feign;

import com.taiji.emp.res.vo.PlanCalTreeVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-res-planCalTree")
public interface IPlanCalTreeRestService {


    /**
     * 根据参数获取PlanCalTreeVo多条记录
     *  @return ResponseEntity<List<PlanCalTreeVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findAll")
    @ResponseBody
    ResponseEntity<List<PlanCalTreeVo>> findList();

    /**
     * 新增预案目录PlanCalTreeVo，PlanCalTreeVo不能为空
     * @param vo
     * @return ResponseEntity<PlanCalTreeVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanCalTreeVo> create(@RequestBody PlanCalTreeVo vo);

    /**
     * 更新预案目录PlanCalTreeVo，PlanCalTreeVo不能为空
     * @param vo,
     * @param id 要更新PlanCalTreeVo id
     * @return ResponseEntity<PlanCalTreeVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PlanCalTreeVo> update(@RequestBody PlanCalTreeVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取预案目录PlanCalTreeVo
     * @param id id不能为空
     * @return ResponseEntity<PlanCalTreeVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanCalTreeVo> findOne(@PathVariable(value = "id") String id);

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
