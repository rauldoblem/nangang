package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.CmdTeamVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急处置-- 关联应急队伍接口服务类
 * @author qizhijie-pc
 * @date 2018年11月7日11:26:20
 */
@FeignClient(value = "micro-event-cmdTeam")
public interface ICmdTeamRestService {

    /**
     * 新增单个应急队伍
     * @param vo
     * @return ResponseEntity<CmdTeamVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<CmdTeamVo> createOne(@RequestBody CmdTeamVo vo);

    /**
     * 新增多个应急队伍
     * @param vos
     * @return ResponseEntity<List<CmdTeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<CmdTeamVo>> createList(@RequestBody List<CmdTeamVo> vos);

    /**
     * 删除应急队伍关联信息
     * @param id 应急队伍关联表Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询处置方案已关联的应急队伍信息
     * @param params
     * 参数key:schemeId,name,teamTypeName
     * @return ResponseEntity<List<CmdTeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/list")
    @ResponseBody
    ResponseEntity<List<CmdTeamVo>> findList(@RequestParam MultiValueMap<String,Object> params);

}
