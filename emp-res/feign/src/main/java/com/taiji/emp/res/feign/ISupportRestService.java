package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.support.SupportListVo;
import com.taiji.emp.res.searchVo.support.SupportPageVo;
import com.taiji.emp.res.vo.SupportVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急社会依托资源 feign 接口服务类
 */
@FeignClient(value = "micro-res-support")
public interface ISupportRestService {

    /**
     * 新增社会依托资源SupportVo，SupportVo不能为空
     * @param vo
     * @return ResponseEntity<Support>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<SupportVo> create(@RequestBody SupportVo vo);

    /**
     * 根据id 获取应急社会依托资源SupportVo
     * @param id id不能为空
     * @return ResponseEntity<SupportVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<SupportVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新应急社会依托资源SupportVo，SupportVo不能为空
     * @param vo,
     * @param id 要更新SupportVo id
     * @return ResponseEntity<SupportVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<SupportVo> update(@RequestBody SupportVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 根据参数获取SupportVo多条记录,分页信息
     * 参数key为 name,suppTypeIds(数组),createOrgId
     *          page,size
     *  @param supportPageVo
     *  @return ResponseEntity<RestPageImpl<SupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<SupportVo>> findPage(@RequestBody SupportPageVo supportPageVo);

    /**
     * 根据参数获取SupportVo多条记录
     *参数key为 name,suppTypeIds(数组),createOrgId
     *  @param supportListVo
     *  @return ResponseEntity<List<SupportVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<SupportVo>> findList(@RequestBody SupportListVo supportListVo);

    /**
     * 通过schemeId应急社会依托资源信息
     * @param schemeId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/schemeId")
    @ResponseBody
    ResponseEntity<List<SupportVo>> findBySchemeId(@RequestBody String schemeId);
}
