package com.taiji.emp.event.infoConfig.feign;

import com.taiji.emp.event.infoConfig.vo.AcceptRuleVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  接报设置--接报要求 接口服务类
 * @author qizhijie-pc
 * @date 2018年10月22日10:11:45
 */
@FeignClient(value = "micro-event-accRule")
public interface IAcceptRuleService {

    /**
     * 新增接报要求AcceptRuleVo
     * @param vo
     * @return ResponseEntity<AcceptRuleVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<AcceptRuleVo> create(@RequestBody AcceptRuleVo vo);

    /**
     * 更新接报要求AcceptRuleVo
     * @param vo,
     * @param id 要更新AcceptRuleVo id
     * @return ResponseEntity<AcceptRuleVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<AcceptRuleVo> update(@RequestBody AcceptRuleVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据查询条件单个AcceptRuleVo
     * 参数keys：eventTypeId
     * @param params
     * @return ResponseEntity<AcceptRuleVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/getRuleSetting")
    @ResponseBody
    ResponseEntity<AcceptRuleVo> getRuleSetting(@RequestParam MultiValueMap<String,Object> params);

    /**
     * 获取一键事故的描述信息
     * @param eventTypeId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/eventDesc")
    @ResponseBody
    ResponseEntity<List<AcceptRuleVo>> eventDesc(@RequestParam(value = "eventTypeId") String eventTypeId);
}
