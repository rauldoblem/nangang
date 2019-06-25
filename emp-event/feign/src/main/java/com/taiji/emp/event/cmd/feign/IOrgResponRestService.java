package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.OrgResponVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  应急处置--关联应急责任单位/人 接口服务类
 * @author qizhijie-pc
 * @date 2018年11月5日16:49:18
 */
@FeignClient(value = "micro-event-orgRespon")
public interface IOrgResponRestService {

    /**
     * 新增单个应急责任单位/人
     * @param vo
     * @return ResponseEntity<OrgResponVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<OrgResponVo> createOne(@RequestBody OrgResponVo vo);

    /**
     * 获取单个应急责任单位/人
     * @param id 应急责任单位/人id
     * @return ResponseEntity<OrgResponVo>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/one/{id}")
    @ResponseBody
    ResponseEntity<OrgResponVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新单个应急责任单位/人
     * @param vo
     * @param id
     * @return ResponseEntity<OrgResponVo>
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/one/{id}")
    @ResponseBody
    ResponseEntity<OrgResponVo> update(@RequestBody OrgResponVo vo,@PathVariable(value = "id") String id);

    /**
     * 删除应急责任单位/人
     * @param id 应急责任单位/人id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 查询应急责任单位/人
     * @param params
     * 参数key:emgOrgId -- 应急机构id
     * @return ResponseEntity<List<OrgResponVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/tree")
    @ResponseBody
    ResponseEntity<List<OrgResponVo>> findList(@RequestParam MultiValueMap<String,Object> params);


    /**
     * 新增关联应急责任单位/人--- 启动预案，根据数字化批量生成
     * @param vos
     * @return ResponseEntity<List<OrgResponVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<OrgResponVo>> createList(@RequestBody List<OrgResponVo> vos);

}
