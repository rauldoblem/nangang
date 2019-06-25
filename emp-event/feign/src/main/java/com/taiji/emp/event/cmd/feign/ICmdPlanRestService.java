package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.CmdPlanVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  应急处置--关联预案 接口服务类
 * @author qizhijie-pc
 * @date 2018年11月2日11:28:43
 */
@FeignClient(value = "micro-event-cmdPlan")
public interface ICmdPlanRestService {

    /**
     * 新增关联预案
     * @param vos
     * @return ResponseEntity<List<CmdPlanVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<CmdPlanVo>> createList(@RequestBody List<CmdPlanVo> vos);

    /**
     * 根据条件查询处置方案已关联的应急预案信息
     * @param params
     * 参数：schemeId 方案id
     * @return ResponseEntity<List<CmdPlanVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<CmdPlanVo>> searchAll(@RequestParam MultiValueMap<String,Object> params);

}
