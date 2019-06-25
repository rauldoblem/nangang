package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.CmdSupportVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急处置-- 关联社会依托资源接口服务类
 * @author qizhijie-pc
 * @date 2018年11月8日10:42:18
 */
@FeignClient(value = "micro-event-cmdSupport")
public interface ICmdSupportRestService {

    /**
     * 新增单个社会依托资源关联信息
     * @param vo
     * @return ResponseEntity<CmdSupportVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<CmdSupportVo> createOne(@RequestBody CmdSupportVo vo);

    /**
     * 新增多个社会依托资源关联信息
     * @param vos
     * @return ResponseEntity<List<CmdSupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<CmdSupportVo>> createList(@RequestBody List<CmdSupportVo> vos);

    /**
     * 删除社会依托资源关联信息
     * @param id 社会依托资源关联表Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询处置方案已关联的社会依托资源
     * @param params
     * 参数key:schemeId,name,address
     * @return ResponseEntity<List<CmdSupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/list")
    @ResponseBody
    ResponseEntity<List<CmdSupportVo>> findList(@RequestParam MultiValueMap<String,Object> params);

}
