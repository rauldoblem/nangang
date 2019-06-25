package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.material.MaterialListVo;
import com.taiji.emp.res.searchVo.material.MaterialPageVo;
import com.taiji.emp.res.vo.MaterialVo;
import com.taiji.emp.zn.vo.MaterialSearchVo;
import com.taiji.emp.zn.vo.MaterialStatVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急物质 feign 接口服务类
 */
@FeignClient(value = "micro-res-material")
public interface IMaterialRestService {

    /**
     * 新增应急物质MaterialVo，MaterialVo不能为空
     * @param vo
     * @return ResponseEntity<MaterialVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<MaterialVo> create(@RequestBody MaterialVo vo);

    /**
     * 根据id 获取应急物质MaterialVo
     * @param id id不能为空
     * @return ResponseEntity<MaterialVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<MaterialVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新应急物质MaterialVo，MaterialVo不能为空
     * @param vo,
     * @param id 要更新MaterialVo id
     * @return ResponseEntity<MaterialVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<MaterialVo> update(@RequestBody MaterialVo vo, @PathVariable(value = "id") String id);

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
     * 根据参数获取MaterialVo多条记录,分页信息
     * 参数key为 name,unit,repertoryIds、positionIds、resTypeIds(数组),createOrgId
     *          page,size
     *  @param materialPageVo
     *  @return ResponseEntity<RestPageImpl<MaterialVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<MaterialVo>> findPage(@RequestBody MaterialPageVo materialPageVo);

    /**
     * 根据参数获取MaterialVo多条记录
     * 参数key为 name,unit,repertoryIds、positionIds、resTypeIds(数组),createOrgId
     *  @param materialListVo
     *  @return ResponseEntity<List<MaterialVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<MaterialVo>> findList(@RequestBody MaterialListVo materialListVo);

    /**
     * 去重获取物资类型大类ID多条记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/group/list")
    @ResponseBody
    ResponseEntity<List<MaterialVo>> findGroupList(@RequestBody List<String> listCode);

    @RequestMapping(method = RequestMethod.POST, path = "/find/info")
    @ResponseBody
    ResponseEntity<List<MaterialStatVo>> findInfo(@RequestBody MaterialSearchVo vo);

    /**
     * 通过schemeId应急物质信息
     * @param schemeId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/schemeId")
    @ResponseBody
    ResponseEntity<List<MaterialVo>> findBySchemeId(@RequestBody String schemeId);
}
