package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.CmdOrgVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  应急处置--关联应急组织机构 接口服务类
 * @author qizhijie-pc
 * @date 2018年11月5日16:49:18
 */
@FeignClient(value = "micro-event-cmdOrg")
public interface ICmdOrgRestService {

    /**
     * 新增单个应急组织机构
     * @param vo
     * @return ResponseEntity<CmdOrgVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<CmdOrgVo> createOne(@RequestBody CmdOrgVo vo);

    /**
     * 获取单个应急组织机构信息
     * @param id 应急组织机构id
     * @return ResponseEntity<CmdOrgVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/one/{id}")
    @ResponseBody
    ResponseEntity<CmdOrgVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新单个应急组织机构
     * @param vo
     * @param id
     * @return ResponseEntity<CmdOrgVo>
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/one/{id}")
    @ResponseBody
    ResponseEntity<CmdOrgVo> update(@RequestBody CmdOrgVo vo,@PathVariable(value = "id") String id);

    /**
     * 删除应急组织机构（含该机构下关联的责任单位/人员）
     * @param id 应急组织机构id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 查询应急组织机构树
     * @param params
     * 参数key:schemeId
     * @return ResponseEntity<List<CmdOrgVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/tree")
    @ResponseBody
    ResponseEntity<List<CmdOrgVo>> findList(@RequestParam MultiValueMap<String,Object> params);


    /**
     * 新增关联应急组织机构--- 启动预案，根据数字化批量生成
     * @param vos
     * @return ResponseEntity<List<CmdOrgVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<CmdOrgVo>> createList(@RequestBody List<CmdOrgVo> vos);


}
