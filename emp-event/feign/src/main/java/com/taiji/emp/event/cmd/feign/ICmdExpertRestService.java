package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.CmdExpertVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急处置-- 关联应急专家接口服务类
 * @author qizhijie-pc
 * @date 2018年11月7日11:26:20
 */
@FeignClient(value = "micro-event-cmdExpert")
public interface ICmdExpertRestService {

    /**
     * 新增单个应急专家关联信息
     * @param vo
     * @return ResponseEntity<CmdExpertVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<CmdExpertVo> createOne(@RequestBody CmdExpertVo vo);

    /**
     * 新增多个应急专家关联信息
     * @param vos
     * @return ResponseEntity<List<CmdExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<CmdExpertVo>> createList(@RequestBody List<CmdExpertVo> vos);

    /**
     * 删除应急专家关联信息
     * @param id 应急专家关联表Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询处置方案已关联的应急专家信息
     * @param params
     * 参数key:schemeId,name,specialty
     * @return ResponseEntity<List<CmdExpertVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/list")
    @ResponseBody
    ResponseEntity<List<CmdExpertVo>> findList(@RequestParam MultiValueMap<String,Object> params);

}
