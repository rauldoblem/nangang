package com.taiji.emp.duty.feign;

import com.taiji.emp.duty.vo.DutyTeamVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 值班人员分组 feign 接口服务类
 */
@FeignClient(value = "micro-duty-dutyTeam")
public interface IDutyTeamRestService {
    /**
     * 新增值班人员分组信息 DutyTeamVo不能为空
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<DutyTeamVo> create(@RequestBody DutyTeamVo vo);

    /**
     * 根据id删除一条记录
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deleteLogic(@PathVariable(value = "id") String id);

    /**
     * 修改值班人员分组信息
     * @param vo
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,path = "/update/{id}")
    @ResponseBody
    ResponseEntity<DutyTeamVo> update(@RequestBody DutyTeamVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id查询某条值班人员分组信息
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/find/{id}")
    @ResponseBody
    ResponseEntity<DutyTeamVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据条件查询值班人员分组列表
     * @param orgId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/list")
    @ResponseBody
    ResponseEntity<List<DutyTeamVo>> findList(@RequestParam(value = "orgId") String orgId);

    /**
     * 根据条件查询值班人员分组列表
     * @param vo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,path = "/find/dutyList")
    @ResponseBody
    ResponseEntity<List<DutyTeamVo>> findDutyList(@RequestBody DutyTeamVo vo);

}
