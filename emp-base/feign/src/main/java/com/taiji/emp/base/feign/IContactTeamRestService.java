package com.taiji.emp.base.feign;

import com.taiji.emp.base.searchVo.contacts.ContactTeamListVo;
import com.taiji.emp.base.searchVo.contacts.ContactTeamsPageVo;
import com.taiji.emp.base.vo.ContactTeamVo;
import com.taiji.emp.base.vo.ContactVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 通讯录组 feign 接口服务类
 * @author sun yi
 * @date 2018年10月23日
 */
@FeignClient(value = "micro-base-contactTeam")
public interface IContactTeamRestService {

    /**
     * 根据参数获取 ContactTeamVo 多条记录
     * params参数key为 teamId(分组ID)，teamName（组名）
     *  @param
     *  @return ResponseEntity<List<ContactTeamVo>>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/find/list")
    @ResponseBody
    ResponseEntity<List<ContactTeamVo>> findList(@RequestBody ContactTeamListVo contactTeamListVo);

    @RequestMapping(method = RequestMethod.POST, path = "/find/searchContacts")
    @ResponseBody
    ResponseEntity<RestPageImpl<ContactVo>> findContactsList(@RequestBody ContactTeamsPageVo contactTeamsPageVo);
    /**
     * 新增通讯录 ContactTeamVo，ContactTeamVo 不能为空
     * @param vo
     * @return ResponseEntity<ContactTeamVo>
     */
    @RequestMapping(method = RequestMethod.POST, path = "/create")
    @ResponseBody
    ResponseEntity<ContactTeamVo> create(@RequestBody ContactTeamVo vo);

    /**
     * 更新通讯录 ContactVo，ContactVo 不能为空
     * @param vo,
     * @param id 要更新 ContactVo id
     * @return ResponseEntity<ContactVo>
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/update/{id}")
    @ResponseBody
    ResponseEntity<ContactTeamVo> update(@RequestBody ContactTeamVo vo, @PathVariable(value = "id") String id);

    /**
     * 根据id 获取通讯录 ContactTeamVo
     * @param id id不能为空
     * @return ResponseEntity<ContactTeamVo>
     */
    @RequestMapping(method = RequestMethod.GET, path = "/find/{id}")
    @ResponseBody
    ResponseEntity<ContactTeamVo> findOne(@PathVariable(value = "id") String id);

    /**
     * 根据id删除一条记录。
     * 存在子节点，一并删除
     * @param id
     * @return ResponseEntity<Void>
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
    @ResponseBody
    ResponseEntity<Void> deletes(@PathVariable(value = "id") String id);

}
