package com.taiji.emp.res.feign;


import com.taiji.emp.res.searchVo.target.TargetSearchVo;
import com.taiji.emp.res.vo.TargetVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 防护目标 feign 接口服务类
 * @author qzp-pc
 * @date 2018年10月16日15:09:08
 */
@FeignClient(value = "micro-res-target")
public interface ITargetRestService {

    /**
     * 根据参数获取RcTargetVo多条记录
     * params参数key为name(可选),
     * @param vo
     * @return ResponseEntity<List<RcTargetVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<TargetVo>> findList(@RequestBody TargetSearchVo vo);

    /**
     * 根据参数获取RcTargetVo多条记录,分页信息
     * params参数key为name(可选),
     * page,size
     * @param searchVo
     * @return ResponseEntity<RestPageImpl<RcTargetVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<TargetVo>> findPage(@RequestBody TargetSearchVo searchVo);

    /**
     * 新增防护目标 RcTargetVo 不能为空
     * @param vo
     * @return ResponseEntity<RcTargetVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<TargetVo> create(@RequestBody TargetVo vo);

    /**
     * 更新防护目标 RcTargetVo不能为空
     * @param vo
     * @param id 要更新 RcTargetVo id
     * @return  ResponseEntity<RcTargetVo>
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<TargetVo> update(@RequestBody TargetVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id获取防护目标信息
     * @param id id不能为空
     * @return ResponseEntity<RcTargetVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TargetVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id删除一条记录
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);
}
