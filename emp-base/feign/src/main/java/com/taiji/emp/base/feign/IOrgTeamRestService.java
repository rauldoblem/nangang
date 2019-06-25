package com.taiji.emp.base.feign;

import com.taiji.emp.base.vo.OrgTeamMidVo;
import com.taiji.emp.base.vo.OrgTeamVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yhcookie
 * @date 2018/12/28 10:48
 */
@FeignClient(value = "micro-base-orgTeam")
public interface IOrgTeamRestService {

    @RequestMapping(method = RequestMethod.POST,path = "/create")
    @ResponseBody
    ResponseEntity<OrgTeamVo> create(@RequestBody OrgTeamVo orgTeamVo);

    @RequestMapping(method = RequestMethod.PUT,path = "/update")
    @ResponseBody
    ResponseEntity<OrgTeamVo> update(OrgTeamVo orgTeamVo);

    @RequestMapping(method = RequestMethod.GET,path = "/findOne/{id}")
    @ResponseBody
    ResponseEntity<OrgTeamVo> findOne(@PathVariable(name = "id") String id);

    @RequestMapping(method = RequestMethod.DELETE,path = "/deleteOne/{id}")
    @ResponseBody
    ResponseEntity deleteOne(@PathVariable(name = "id") String id);

    @RequestMapping(method = RequestMethod.GET,path = "/searchAll/{createUserId}")
    @ResponseBody
    ResponseEntity<List<OrgTeamVo>> findAll(@PathVariable(name = "createUserId") String createUserId);

    @RequestMapping(method = RequestMethod.DELETE,path = "/deleteMids/{teamId}")
    @ResponseBody
    ResponseEntity deleteOrgTeamMidsByTeamId(@PathVariable(name = "teamId")String teamId);

    @RequestMapping(method = RequestMethod.POST,path = "/saveMids")
    @ResponseBody
    ResponseEntity createOrgTeamMids(@RequestBody  List<OrgTeamMidVo> orgTeamMidVos);

    @RequestMapping(method = RequestMethod.GET,path = "/searchOrgsByTeamId/{id}")
    @ResponseBody
    ResponseEntity<List<OrgTeamMidVo>> searchOrgsByTeamId(@PathVariable(name = "id") String id);
}
