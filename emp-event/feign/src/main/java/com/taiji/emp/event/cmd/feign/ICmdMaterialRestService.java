package com.taiji.emp.event.cmd.feign;

import com.taiji.emp.event.cmd.vo.CmdMaterialVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急处置-- 关联应急物资接口服务类
 * @author qizhijie-pc
 * @date 2018年11月8日10:42:18
 */
@FeignClient(value = "micro-event-cmdMaterial")
public interface ICmdMaterialRestService {

    /**
     * 新增单个应急物资关联信息
     * @param vo
     * @return ResponseEntity<CmdMaterialVo>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/one")
    @ResponseBody
    ResponseEntity<CmdMaterialVo> createOne(@RequestBody CmdMaterialVo vo);

    /**
     * 新增多个应急物资关联信息
     * @param vos
     * @return ResponseEntity<List<CmdMaterialVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create/list")
    @ResponseBody
    ResponseEntity<List<CmdMaterialVo>> createList(@RequestBody List<CmdMaterialVo> vos);

    /**
     * 删除应急物资关联信息
     * @param id 应急物资关联表Id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/one/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询处置方案已关联的应急物资信息
     * @param params
     * 参数key:schemeId,name,resTypeName
     * @return ResponseEntity<List<CmdMaterialVo>>
     */
    @RequestMapping(method = RequestMethod.POST,path = "/search/list")
    @ResponseBody
    ResponseEntity<List<CmdMaterialVo>> findList(@RequestParam MultiValueMap<String,Object> params);

}
