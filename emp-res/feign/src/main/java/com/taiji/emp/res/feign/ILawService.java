package com.taiji.emp.res.feign;


import com.taiji.emp.res.searchVo.law.LawListVo;
import com.taiji.emp.res.searchVo.law.LawPageVo;
import com.taiji.emp.res.vo.LawVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "micro-res-law")
public interface ILawService {
    /**
     * 根据LawVo id获取一条记录
     *
     * @param id  字典项id
     * @return  ResponseEntity<LawVo>
     */
    @RequestMapping(method = RequestMethod.GET ,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<LawVo> findOne(@PathVariable("id") String id);

    /**
     * 新增LawVo, LawVo不能为空
     *
     * @param vo  字典项vo
     * @return ResponseEntity<LawVo>
     */
    @RequestMapping(method = RequestMethod.POST ,path = "/create")
    @ResponseBody
    ResponseEntity<LawVo> create(@RequestBody LawVo vo);

    /**
     * 根据id逻辑删除一条记录
     *
     * @param id  字典项id
     * @return  ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE ,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable("id") String id);

    /**
     * 更新LawVo, LawVo不能为空
     *
     * @param vo 字典项vo
     * @return ResponseEntity<LawVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<LawVo> update(@RequestBody LawVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据参数获取LawVo多条记录
     *
     * params参数key为title(可选),keyword(可选),buildOrg(可选),eventTypeIds(可选),lawTypeId(可选)
     *
     * @param lawListVo
     * @return ResponseEntuty<List<LawVo></>></>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<LawVo>> findList(@RequestBody LawListVo lawListVo);

    /**
     * 根据参数获取分页LawVo多条记录
     *
     * param参数key为title(可选),keyword(可选),buildOrg(可选),eventTypeIds(可选),lawTypeId(可选)
     *
     * @param lawPageVo
     * @return ResponseEntity<RestPageImpl<LawVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<LawVo>> findPage(@RequestBody LawPageVo lawPageVo);
}
