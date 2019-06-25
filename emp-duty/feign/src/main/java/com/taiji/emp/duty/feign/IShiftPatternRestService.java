package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.dailylog.ShiftPatternVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班次设置表 feign 接口服务类
 */
@FeignClient(value = "micro-duty-shiftPattern")
public interface IShiftPatternRestService {

    /**
     * 根据patterId查询班次设置信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/searchShiftPatterns")
    @ResponseBody
    ResponseEntity<List<ShiftPatternVo>> findAll(@RequestParam(name = "patterId") String id);

    /**
     * 新增班次信息 ShiftPatternVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<ShiftPatternVo> create(@RequestBody ShiftPatternVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改班次信息
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ShiftPatternVo> update(@RequestBody ShiftPatternVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id查询某条班次信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ShiftPatternVo> findOne(@PathVariable(value = "id") String id);

}
