package com.taiji.emp.res.feign;

import com.taiji.emp.res.searchVo.team.TeamListVo;
import com.taiji.emp.res.searchVo.team.TeamPageVo;
import com.taiji.emp.res.vo.TeamVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 救援队伍 feign 接口服务类
 * @author qizhijie-pc
 * @date 2018年10月10日10:30:04
 */
@FeignClient(value = "micro-res-team")
public interface ITeamRestService {

    /**
     * 根据参数获取TeamVo多条记录
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *  @param teamListVo
     *  @return ResponseEntity<List<TeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<TeamVo>> findList(@RequestBody TeamListVo teamListVo);

    /**
     * 根据参数获取TeamVo多条记录,分页信息
     * 参数key为title(可选),eventTypeId(可选),keyWord(可选),knoTypeId(可选),createOrgId
     *          page,size
     *  @param teamPageVo
     *  @return ResponseEntity<RestPageImpl<TeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/page")
    @ResponseBody
    ResponseEntity<RestPageImpl<TeamVo>> findPage(@RequestBody TeamPageVo teamPageVo);

    /**
     * 新增救援队伍TeamVo，TeamVo不能为空
     * @param vo
     * @return ResponseEntity<TeamVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<TeamVo> create(@RequestBody TeamVo vo);

    /**
     * 更新救援队伍TeamVo，TeamVo不能为空
     * @param vo,
     * @param id 要更新TeamVo id
     * @return ResponseEntity<TeamVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<TeamVo> update(@RequestBody TeamVo vo,@PathVariable(value = "id") String id);

    /**
     * 根据id 获取救援队伍TeamVo
     * @param id id不能为空
     * @return ResponseEntity<TeamVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<TeamVo> findOne(@PathVariable(value = "id") String id);

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
     * 通过schemeId救援队伍信息
     * @param schemeId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/schemeId")
    @ResponseBody
    ResponseEntity<List<TeamVo>> findBySchemeId(@RequestBody String schemeId);
}
