package com.taiji.emp.event.infoConfig.feign;

import com.taiji.emp.event.infoConfig.vo.AcceptInformSearchVo;
import com.taiji.emp.event.infoConfig.vo.AcceptInformVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  接报设置--通知单位 接口服务类
 * @author qizhijie-pc
 * @date 2018年10月22日10:11:45
 */
@FeignClient(value = "micro-event-accInfo")
public interface IAcceptInformService {

    /**
     * 新增通知单位AcceptInformVo
     * @param vo
     * @return ResponseEntity<AcceptInformVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<AcceptInformVo> create(@RequestBody AcceptInformVo vo);

    /**
     * 根据id 通知单位AcceptInformVo
     * @param id id不能为空
     * @return ResponseEntity<AcceptInformVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<AcceptInformVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新通知单位AcceptInformVo
     * @param vo,
     * @param id 要更新AcceptInformVo id
     * @return ResponseEntity<AcceptInformVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<AcceptInformVo> update(@RequestBody AcceptInformVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> delete(@PathVariable(value = "id") String id);

    /**
     * 根据查询条件查询AcceptInformVo list
     * 参数keys：eventTypeId
     * @param params
     * @return ResponseEntity<List<AcceptInformVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<AcceptInformVo>> searchAll(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 根据条件查询通知单位列表
     * @param acceptInformVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/acceptInforms")
    @ResponseBody
    ResponseEntity<List<AcceptInformVo>> searchAcceptInforms(@RequestBody AcceptInformSearchVo acceptInformVo);
}
