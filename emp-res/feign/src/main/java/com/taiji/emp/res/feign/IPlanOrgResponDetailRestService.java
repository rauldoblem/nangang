package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.planOrgResponDetail.PlanOrgResponDetailListVo;
import com.taiji.emp.res.vo.PlanOrgResponDetailVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  预案责任人、单位详情管理 feign 接口服务类
 */
@FeignClient(value = "micro-res-planOrgResponDetail")
public interface IPlanOrgResponDetailRestService {

    /**
     * 新增预案责任人、单位详情PlanOrgResponDetailVo，PlanOrgResponDetailVo不能为空
     * @param vo
     * @return ResponseEntity<PlanOrgResponDetailVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<PlanOrgResponDetailVo> create(@RequestBody PlanOrgResponDetailListVo vo);

    /**
     * 根据id 获取预案责任人、单位详情PlanOrgResponDetailVo
     * @param id id不能为空
     * @return ResponseEntity<PlanOrgResponDetailVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<PlanOrgResponDetailVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 更新预案责任人、单位详情PlanOrgResponDetailListVo，PlanOrgResponDetailListVo不能为空
     * @param vo,
     * @param id 要更新PlanOrgResponDetailVo id
     * @return ResponseEntity<PlanOrgResponDetailVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<List<PlanOrgResponDetailVo>> updateDetail(@RequestBody PlanOrgResponDetailListVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id逻辑删除一条记录。
     *
     * @param ids
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/delete")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@RequestBody List<String> ids);

    /**
     * 根据参数获取PlanOrgResponDetailVo多条记录
     * 参数key为 planOrgId
     *  @param planOrgResponDetailListVo
     *  @return ResponseEntity<List<PlanOrgResponDetailVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/findAll")
    @ResponseBody
    ResponseEntity<List<PlanOrgResponDetailVo>> findList(@RequestBody PlanOrgResponDetailListVo planOrgResponDetailListVo);

    /**
     * 根据参数获取PlanOrgResponDetailVo多条记录
     * 参数key为 planOrgId
     *  @param planOrgResponDetailVo
     *  @return ResponseEntity<List<PlanOrgResponDetailVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<PlanOrgResponDetailVo>> findList(@RequestBody PlanOrgResponDetailVo planOrgResponDetailVo);
}
