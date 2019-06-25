package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.dailylog.PersonTypePatternVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 值班人员设置表 feign 接口服务类
 */
@FeignClient(value = "micro-duty-personTypePattern")
public interface IPersonTypePatternRestService {

    /**
     * 根据patterId查询值班人员设置信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/searchPtypePatterns")
    @ResponseBody
    ResponseEntity<List<PersonTypePatternVo>> findAll(@RequestParam(value = "id") String id);

    /**
     * 新增值班人员设置信息 PersonTypePatternVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<PersonTypePatternVo> create(@RequestBody PersonTypePatternVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改值班人员设置信息
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<PersonTypePatternVo> update(@RequestBody PersonTypePatternVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id查询某条值班人员设置信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PersonTypePatternVo> findOne(@PathVariable(value = "id") String id);

}
