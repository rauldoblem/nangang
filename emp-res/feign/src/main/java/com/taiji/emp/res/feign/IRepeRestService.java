package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.repertory.RepertoryPageVo;
import com.taiji.emp.res.vo.RepertoryVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急储备库 feign 接口服务类
 *
 */
@FeignClient(value = "micro-res-repertory")
public interface IRepeRestService {

    /**
     * 根据参数获取RepertoryVo多条记录,分页信息
     *  @param vo
     *  @return ResponseEntity<RestPageImpl<RepertoryVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<RepertoryVo>> findPage(@RequestBody RepertoryPageVo vo);

    /**
     * 新增应急储备库RepertoryVo，RepertoryVo不能为空
     * @param vo
     * @return ResponseEntity<RepertoryVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<RepertoryVo> create(@RequestBody RepertoryVo vo);

    /**
     * 更新应急储备库RepertoryVo，RepertoryVo不能为空
     * @param vo,
     * @param id 要更新RepertoryVo id
     * @return ResponseEntity<RepertoryVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<RepertoryVo> update(@RequestBody RepertoryVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取应急储备库RepertoryVo
     * @param id id不能为空
     * @return ResponseEntity<RepertoryVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<RepertoryVo> findOne(@PathVariable(value = "id") String id);

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
     * 根据条件查询物资库列表-不分页
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/repertory/list")
    @ResponseBody
    ResponseEntity<List<RepertoryVo>> findRepertoryList(@RequestBody RepertoryPageVo vo);
}
